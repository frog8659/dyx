package sh.ricky.dyx.webservice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import sh.ricky.core.bean.Page;
import sh.ricky.core.util.CascadeUtil;
import sh.ricky.dyx.bean.OrderQueryCondition;
import sh.ricky.dyx.bo.DyxOrd;
import sh.ricky.dyx.bo.DyxOrdMetr;
import sh.ricky.dyx.constant.FlowConstants;
import sh.ricky.dyx.form.OrderForm;
import sh.ricky.dyx.service.OrderService;
import sh.ricky.dyx.util.SysUtil;

@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class OrderWebService {
    @Autowired
    private OrderService orderService;

    /**
     * 获取JSON格式数据
     * 
     * @param data
     * @param clz
     * @return
     */
    private <T> T getData(T data, Class<T> clz) {
        // 清除多对一关联属性及未加载的集合对象
        data = SysUtil.clearChain(data);
        // 清除空属性
        CascadeUtil.clearObject(data);

        // 以JSON格式返回
        try {
            return data == null ? clz.newInstance() : data;
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * 获取分期订单申请资料
     * 
     * @param orderNo
     * @return
     */
    @GET
    @Path("/order/metr/{orderNo}")
    public DyxOrd getConsumerOrderMetr(@PathParam("orderNo")
    String orderNo) {
        // 根据订单编号获取订单对象
        DyxOrd ord = orderService.getOrdByNo(orderNo);

        if (ord == null) {
            ord = new DyxOrd();
            ord.setDyxOrdMetr(new DyxOrdMetr());
            ord.getDyxOrdMetr().setMetrId(SysUtil.randomUUID());
        } else {
            ord.setDyxOrdEvalSet(null);
            ord.setDyxOrdProcSet(null);
        }

        return this.getData(ord, DyxOrd.class);
    }

    /**
     * 提交分期订单
     * 
     * @param form
     * @return
     */
    @POST
    @Path("/order/submit")
    public Map<String, Object> submitConsumerOrder(OrderForm form) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        if (form == null) {
            map.put("error", "提交分期订单出错！");
            return map;
        }

        // 根据订单编号获取订单对象
        DyxOrd ord = orderService.getOrdByNo(form.getOrd().getOrdNo());
        if (ord == null) {
            ord = new DyxOrd();
            ord.setAudtStat(FlowConstants.FLOW_SEG_DCS);
        } else {
            ord.setAudtStat(FlowConstants.FLOW_SEG_ZLYXG);
        }

        // 校验订单令牌，校验不通过则返回空
        String token = ord.getToken();
        if (StringUtils.isBlank(token) && StringUtils.isNotBlank(form.getToken())) {
            map.put("error", "订单不存在或已被处理！");
            return map;
        } else if (StringUtils.isNotBlank(token) && !StringUtils.equals(form.getToken(), token)) {
            map.put("error", "订单不存在或已被处理！");
            return map;
        }

        // 根据表单内容更新订单对象
        form.update(ord);

        // 保存或更新订单
        try {
            ord = orderService.updateOrd(ord, null, null);
            // 更新成功，则返回订单审核状态
            map.put("stat", ord.getAudtStat());
        } catch (Throwable t) {
            map.put("error", "订单更新出错！");
        }

        return map;
    }

    /**
     * 获取订单详情信息
     * 
     * @param orderNo
     * @return
     */
    @GET
    @SuppressWarnings("unchecked")
    @Path("/order/detail/{orderNo}")
    public Map<String, Object> getConsumerOrderDetail(@PathParam("orderNo")
    String orderNo) {
        // 订单详情信息
        Map<String, Object> result = orderService.getConsumerOrderDetail(orderNo);

        // 以JSON格式返回
        return this.getData(result, Map.class);
    }

    /**
     * 获取订单分页列表
     * 
     * @param logicId
     * @param userId
     * @param pageNo
     * @return
     */
    @GET
    @SuppressWarnings("unchecked")
    @Path("/order/list/{logicId}/{userId}/{pageNo}")
    public List<Map<String, Object>> findConsumerOrderWithPage(@PathParam("logicId")
    String logicId, @PathParam("userId")
    String userId, @PathParam("pageNo")
    int pageNo) {
        OrderQueryCondition condition = new OrderQueryCondition();
        condition.setConsumerId(userId);
        condition.setConsumerLogicId(logicId);
        condition.setPageNo(pageNo);

        // 订单信息页对象
        Page page = orderService.findConsumerOrderWithPage(condition);

        // 以JSON格式返回
        return this.getData((List<Map<String, Object>>) page.getResult(), List.class);
    }
}
