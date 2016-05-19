package sh.ricky.dyx.webservice;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloopen.rest.sdk.CCPRestSDK;

import sh.ricky.core.bean.Page;
import sh.ricky.core.constant.ConfigConstants;
import sh.ricky.core.util.CascadeUtil;
import sh.ricky.dyx.bean.OrderQueryCondition;
import sh.ricky.dyx.bo.DyxOrd;
import sh.ricky.dyx.constant.FlowConstants;
import sh.ricky.dyx.constant.OrderConstants;
import sh.ricky.dyx.form.OrderForm;
import sh.ricky.dyx.service.OrderService;
import sh.ricky.dyx.util.SysUtil;

@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class OrderWebService {
    @Autowired
    private OrderService orderService;

    private static CCPRestSDK ccp = new CCPRestSDK();

    static {
        ccp.init(ConfigConstants.getInstance().get("ccp.url"), ConfigConstants.getInstance().get("ccp.port"));
        ccp.setAccount(ConfigConstants.getInstance().get("ccp.account_sid"), ConfigConstants.getInstance().get("ccp.auth_token"));
        ccp.setAppId(ConfigConstants.getInstance().get("ccp.app_id"));
    }

    /**
     * 统一设置返回数据格式
     * 
     * @param data
     * @return
     */
    @SuppressWarnings("rawtypes")
    private Map<String, Object> getReturnData(Object data) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("data", data);

        if (data == null) {
            return map;
        }

        try {
            // 清除多对一关联属性及未加载的集合对象
            data = SysUtil.clearChain(data);
            // 清除空属性
            CascadeUtil.clearObject(data);

            if (data instanceof Map) {
                if (((Map) data).containsKey("error")) {
                    map.put("error", ((Map) data).get("error"));
                    ((Map) data).remove("error");
                }
            }
        } catch (Throwable t) {
            map.put("error", "系统出现异常！");
        }

        return map;
    }

    /**
     * 获取分期订单申请资料
     * 
     * @param request
     * @param orderNo
     * @return
     */
    @GET
    @Path("/order/metr/{orderNo}")
    public Object getClientOrderMetr(@Context HttpServletRequest request, @PathParam("orderNo") String orderNo) {
        // 根据订单编号获取订单对象
        DyxOrd ord = orderService.getOrdByNo(orderNo);

        if (ord != null) {
            DyxOrd result = new DyxOrd();
            result.setOrdType(ord.getOrdType());
            result.setToken(ord.getToken());
            result.setDyxOrdMetr(ord.getDyxOrdMetr());
            ord = result;
        }

        // 以统一数据格式返回
        return this.getReturnData(ord);
    }

    /**
     * 提交分期订单
     * 
     * @param request
     * @param form
     * @return
     */
    @SuppressWarnings("unchecked")
    @POST
    @Path("/order/submit")
    public Object submitClientOrder(@Context HttpServletRequest request, OrderForm form) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        if (form == null) {
            map.put("error", "提交分期订单出错！");
            return this.getReturnData(map);
        }

        // 根据订单编号获取订单对象
        DyxOrd ord = orderService.getOrdByNo(form.getOrd().getOrdNo());
        if (ord == null) {
            ord = new DyxOrd();
            String ordType = form.getOrd().getOrdType();
            if (OrderConstants.ORD_TYPE_ZZBL.equals(ordType)) {
                ord.setAudtStat(FlowConstants.FLOW_SEG_DCS);
            } else if (OrderConstants.ORD_TYPE_KFDB.equals(ordType)) {
                ord.setAudtStat(FlowConstants.FLOW_SEG_KFDTX);
            }
        } else {
            ord.setAudtStat(FlowConstants.FLOW_SEG_ZLYXG);
        }

        // 校验订单令牌，校验不通过则返回空
        String token = ord.getToken();
        if (StringUtils.isBlank(token) && StringUtils.isNotBlank(form.getToken())) {
            map.put("error", "订单不存在或已被处理！");
            return this.getReturnData(map);
        } else if (StringUtils.isNotBlank(token) && !StringUtils.equals(form.getToken(), token)) {
            map.put("error", "订单不存在或已被处理！");
            return this.getReturnData(map);
        }

        // 根据表单内容更新订单对象
        form.update(ord);

        // 保存或更新订单
        try {
            Map<String, Object> detail = orderService.getOrderAppInfo(ord.getOrdNo());
            if (detail != null && !detail.isEmpty()) {
                ord.setRec((String) detail.get("rec"));
                ord.setRecTel((String) detail.get("recTel"));
                ord.setOrdDate((Date) detail.get("ordDate"));
                ord.setShopAddr((String) detail.get("shopAddr"));
                ord.setShopNo((String) detail.get("shopNo"));

                String region = (String) detail.get("shopRegion");
                if (StringUtils.isNotBlank(region)) {
                    if (region.endsWith("0000")) {
                        ord.setShopProv(region);
                    } else if (region.endsWith("00")) {
                        ord.setShopProv(region.substring(0, 2) + "0000");
                        ord.setShopCity(region);
                    } else {
                        ord.setShopProv(region.substring(0, 2) + "0000");
                        ord.setShopCity(region.substring(0, 4) + "00");
                        ord.setShopDist(region);
                    }
                }
            }

            Map<String, Object> user = (Map<String, Object>) request.getAttribute("shopUser");
            if (user != null) {
                ord.setAgt((String) user.get("name"));
                ord.setAgtNo((String) user.get("code"));
                ord.setAgtTel((String) user.get("mobile"));
            }

            ord = orderService.updateOrd(ord, null, null);
            // 更新成功，则返回订单审核状态
            map.put("stat", ord.getAudtStat());
        } catch (Throwable t) {
            map.put("error", "订单更新出错！");
        }

        // 以统一数据格式返回
        return this.getReturnData(map);
    }

    /**
     * 获取订单详情信息
     * 
     * @param request
     * @param orderNo
     * @return
     */
    @GET
    @Path("/order/detail/{orderNo}")
    public Object getClientOrderDetail(@Context HttpServletRequest request, @PathParam("orderNo") String orderNo) {
        // 订单详情信息
        Map<String, Object> result = orderService.getClientOrderDetail(orderNo);

        // 以统一数据格式返回
        return this.getReturnData(result);
    }

    /**
     * 获取订单分页列表
     * 
     * @param request
     * @param pageNo
     * @return
     */
    @SuppressWarnings("unchecked")
    @GET
    @Path("/order/list/{pageNo}")
    public Object findClientOrderWithPage(@Context HttpServletRequest request, @PathParam("pageNo") int pageNo) {
        Map<String, Object> user = (Map<String, Object>) request.getAttribute("shopUser");
        Page page = Page.EMPTY_PAGE;

        if (user != null && user.containsKey("id")) {
            OrderQueryCondition condition = new OrderQueryCondition();
            condition.setOpUserId(String.valueOf(user.get("id")));
            condition.setPageNo(pageNo);

            // 订单信息页对象
            page = orderService.findClientOrderWithPage(condition);
        }

        // 以统一数据格式返回
        return this.getReturnData(page.getResult());
    }

    /**
     * 发送短信
     * 
     * @param request
     * @param mobile
     * @param templateId
     * @return
     */
    @GET
    @Path("/sms/{mobile}/{templateId}")
    public Object sendShortMessage(@Context HttpServletRequest request, @PathParam("mobile") String mobile, @PathParam("templateId") String templateId) {
        String[] data = null;
        Map<String, Object> result = ccp.sendTemplateSMS(mobile, templateId, data);

        // 以统一数据格式返回
        return this.getReturnData(result);
    }
}
