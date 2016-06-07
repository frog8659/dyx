package sh.ricky.dyx.controller;

import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import sh.ricky.core.annotation.Outer;
import sh.ricky.core.constant.ConfigConstants;
import sh.ricky.core.constant.GlobalConstants;
import sh.ricky.core.controller.BaseBinder;
import sh.ricky.core.util.ResponseUtil;
import sh.ricky.dyx.annotation.Permission;
import sh.ricky.dyx.bean.CfgFlow;
import sh.ricky.dyx.bean.OrderQueryCondition;
import sh.ricky.dyx.bo.DyxOrd;
import sh.ricky.dyx.bo.DyxOrdMetr;
import sh.ricky.dyx.bo.DyxOrdMetrAttach;
import sh.ricky.dyx.constant.FlowConstants;
import sh.ricky.dyx.constant.OrderConstants;
import sh.ricky.dyx.constant.UserConstants;
import sh.ricky.dyx.form.OrderForm;
import sh.ricky.dyx.service.OrderService;
import sh.ricky.dyx.util.FlowUtil;
import sh.ricky.dyx.util.SysUtil;

/**
 * 分期订单
 * 
 * @author SHI
 */
@Controller
@RequestMapping("/ord")
public class OrderController extends BaseBinder {
    @Autowired
    private CommonsMultipartResolver multipartResolver;

    @Autowired
    private OrderService orderService;

    /**
     * 订单管理列表：自主办理订单
     * 
     * @param condition
     * @param model
     * @return
     */
    @Permission(authId = UserConstants.USER_AUTH_ZZBLDD)
    @RequestMapping(value = "/audit/list/" + FlowConstants.SEG_SORT_FQDDYS, params = "ordType=" + OrderConstants.ORD_TYPE_ZZBL)
    public String zzblddAuditList(OrderQueryCondition condition, Model model) {
        condition.setSegSort(FlowConstants.SEG_SORT_FQDDYS);
        condition.setOrdType(OrderConstants.ORD_TYPE_ZZBL);
        return this.auditList(condition, model);
    }

    /**
     * 订单管理列表：客服代办订单
     * 
     * @param condition
     * @param model
     * @return
     */
    @Permission(authId = UserConstants.USER_AUTH_KFDBDD)
    @RequestMapping(value = "/audit/list/" + FlowConstants.SEG_SORT_FQDDYS, params = "ordType=" + OrderConstants.ORD_TYPE_KFDB)
    public String kfdbddAuditList(OrderQueryCondition condition, Model model) {
        condition.setSegSort(FlowConstants.SEG_SORT_FQDDYS);
        condition.setOrdType(OrderConstants.ORD_TYPE_KFDB);
        return this.auditList(condition, model);
    }

    /**
     * 订单管理列表：贷后资料管理
     * 
     * @param condition
     * @param model
     * @return
     */
    @Permission(authId = UserConstants.USER_AUTH_DHZLGL)
    @RequestMapping("/audit/list/" + FlowConstants.SEG_SORT_FQDDGL)
    public String dhzlglAuditList(OrderQueryCondition condition, Model model) {
        condition.setSegSort(FlowConstants.SEG_SORT_FQDDGL);
        return this.auditList(condition, model);
    }

    /**
     * 订单管理列表
     * 
     * @param condition
     * @param model
     * @return
     */
    private String auditList(OrderQueryCondition condition, Model model) {
        // 查询分期订单页对象
        model.addAttribute("ordPage", orderService.findOrderWithPage(condition));
        // 返回查询条件
        model.addAttribute("condition", condition);
        // 跳转至列表页面
        return "list/audit";
    }

    /**
     * 订单审核详情
     * 
     * @param list
     * @param id
     * @param model
     * @return
     */
    @Permission(authId = { UserConstants.USER_AUTH_FQDDYS_CS, UserConstants.USER_AUTH_FQDDYS_FS, UserConstants.USER_AUTH_FQDDGL_CS,
            UserConstants.USER_AUTH_FQDDGL_FS })
    @RequestMapping("/audit/detail/{sort}")
    public String auditDetail(@PathVariable("sort") String sort, String id, Model model) {
        // 查询分期订单对象
        DyxOrd ord = orderService.getOrd(id, 1);

        if (ord != null) {
            Map<String, Object> detail = orderService.getOrderDetail(ord.getOrdNo());
            if (detail != null && !detail.isEmpty()) {
                ord.setPrdName((String) detail.get("fullname"));
                ord.setPrdBrand((String) detail.get("brand"));
                ord.setPrdType((String) detail.get("category"));
                ord.setPrdSpec((String) detail.get("configuration"));
                ord.setPrdColor((String) detail.get("color_name"));
                ord.setPrdCount((Integer) detail.get("number"));
                ord.setPrdOriPric((BigDecimal) detail.get("original_price"));
                ord.setPrdCurPric((BigDecimal) detail.get("current_price"));
                ord.setOrdAmt((BigDecimal) detail.get("in_price"));

                DyxOrdMetr metr = ord.getDyxOrdMetr();
                if (metr == null) {
                    metr = new DyxOrdMetr();
                    ord.setDyxOrdMetr(metr);
                }
                metr.setInstName((String) detail.get("inst_name"));
                metr.setInstPeriod((String) detail.get("inst_period"));
                metr.setInstOopAmt(((BigDecimal) detail.get("dw_pay_amt")).setScale(2, BigDecimal.ROUND_CEILING));
                metr.setInstMonIntrRate((BigDecimal) detail.get("inst_mon_rate"));
                metr.setInstMonServRate((BigDecimal) detail.get("inst_mon_rate"));
                metr.setMetrDate((Date) detail.get("sys_time"));
                metr.setApl((String) detail.get("consumer"));
                metr.setAplTel((String) detail.get("telephone"));

                if (ord.getOrdAmt() != null && metr.getInstOopAmt() != null) {
                    metr.setInstAmt(ord.getOrdAmt().subtract(metr.getInstOopAmt()).setScale(2, BigDecimal.ROUND_CEILING));
                }

                if (metr.getInstAmt() != null && metr.getInstMonIntrRate() != null && StringUtils.isNotBlank(metr.getInstPeriod())
                        && !BigDecimal.ZERO.equals(new BigDecimal(metr.getInstPeriod()))) {
                    metr.setInstRepAmt(metr.getInstAmt().divide(new BigDecimal(metr.getInstPeriod()), BigDecimal.ROUND_CEILING)
                            .multiply(BigDecimal.ONE.add(metr.getInstMonIntrRate())).setScale(2, BigDecimal.ROUND_CEILING));
                }
            }

            // 设置流程状态
            CfgFlow condition = new CfgFlow();
            condition.setFlowSeg(ord.getAudtStat());
            if (StringUtils.equals(ord.getOrdType(), OrderConstants.ORD_TYPE_ZZBL)) {
                condition.setFlowId(FlowConstants.FLOW_ID_ZZBLDDYS);
            } else if (StringUtils.equals(ord.getOrdType(), OrderConstants.ORD_TYPE_KFDB)) {
                condition.setFlowId(FlowConstants.FLOW_ID_KFDBDDYS);
            }

            // 返回是否存在分期订单
            model.addAttribute("isOrdExisted", orderService.countOrder(ord, 1) > 0);
            // 返回门店分期订单业务数
            model.addAttribute("stat1", orderService.countOrder(ord, 2));
            // 返回门店受理申请人所在区/县人员分期订单数
            model.addAttribute("stat2", orderService.countOrder(ord, 3));
            // 返回门店所在区/县受理申请人所在区/县人员分期订单数
            model.addAttribute("stat3", orderService.countOrder(ord, 4));
            // 返回门店所在省级市受理申请人所在区/县人员分期订单数
            model.addAttribute("stat4", orderService.countOrder(ord, 5));
            // 返回流程操作列表
            model.addAttribute("flowList", FlowUtil.getFlowList(condition));
            // 返回流程ID
            model.addAttribute("flowId", condition.getFlowId());
            // 返回分期订单对象
            model.addAttribute("ord", ord);
        }

        // 返回订单环节分类
        model.addAttribute("sort", sort);

        // 跳转至订单详情页面
        return "info/audit";
    }

    /**
     * 根据表单信息更新订单对象
     * 
     * @param form
     * @return
     */
    private DyxOrd formUpdate(OrderForm form) {
        // 根据表单信息从数据库获取订单对象
        DyxOrd ord = orderService.getOrd(form.getOrd().getOrdId(), 1);

        // 匹配不到订单，则返回空
        if (ord == null) {
            return null;
        }

        // 校验订单令牌，校验不通过则返回空
        String token = ord.getToken();
        if (StringUtils.isBlank(token) && StringUtils.isNotBlank(form.getToken())) {
            return null;
        } else if (StringUtils.isNotBlank(token) && !StringUtils.equals(form.getToken(), token)) {
            return null;
        }

        // 根据表单内容更新订单对象
        form.update(ord);

        // 返回更新后的订单对象
        return ord;
    }

    /**
     * 订单审核编辑更新
     * 
     * @param form
     * @return
     */
    @Permission(authId = { UserConstants.USER_AUTH_FQDDYS_CS, UserConstants.USER_AUTH_FQDDYS_FS, UserConstants.USER_AUTH_FQDDGL_CS,
            UserConstants.USER_AUTH_FQDDGL_FS })
    @ResponseBody
    @RequestMapping("/audit/upd")
    public Map<String, Object> auditUpdate(OrderForm form) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        // 根据表单信息更新订单对象
        DyxOrd ord = this.formUpdate(form);

        if (ord == null) {
            // 订单不存在或已被处理
            map.put("error", "订单不存在或已被处理！");
        } else {
            // 保存或更新订单
            try {
                ord = orderService.updateOrd(ord, null, null);
            } catch (Throwable t) {
                map.put("error", "订单更新出错！");
            }
        }

        // 返回更新后的订单数据
        map.put("ord", SysUtil.clearChain(ord));

        // 返回处理结果
        return map;
    }

    /**
     * 订单审核
     * 
     * @param form
     * @return
     */
    @Permission(authId = { UserConstants.USER_AUTH_FQDDYS_CS, UserConstants.USER_AUTH_FQDDYS_FS, UserConstants.USER_AUTH_FQDDGL_CS,
            UserConstants.USER_AUTH_FQDDGL_FS })
    @RequestMapping("/audit")
    public String audit(OrderForm form) {
        // 根据表单信息更新订单对象
        DyxOrd ord = this.formUpdate(form);

        // 订单不存在或已被处理
        if (ord == null) {
            return GlobalConstants.FORWARD_ERROR;
        }

        try {
            // 保存或更新订单，订单流程流转
            orderService.updateOrd(ord, form.getMemo(), form.getFlow());
        } catch (Throwable t) {
            return GlobalConstants.FORWARD_ERROR;
        }

        // 返回预审列表
        return "redirect:/ord/audit/list/" + form.getSort() + "?ordType=" + ord.getOrdType();
    }

    /**
     * 订单申请资料文件上传（手机端）
     * 
     * @param request
     * @param atchType
     * @param dispOrd
     * @return
     */
    @Outer
    @ResponseBody
    @RequestMapping("/upload_mob/{atchType}/{dispOrd}")
    public Object uploadMob(HttpServletRequest request, @PathVariable("atchType") String atchType, @PathVariable("dispOrd") Integer dispOrd) {
        DyxOrdMetrAttach attach = new DyxOrdMetrAttach();
        attach.setAtchType(atchType);
        attach.setDispOrd(dispOrd);

        return this.upload(request, attach);
    }

    /**
     * 订单申请资料文件上传
     * 
     * @param request
     * @param attach
     * @return
     */
    @Permission
    @RequestMapping("/upload")
    public ResponseEntity<Map<String, Object>> upload(HttpServletRequest request, DyxOrdMetrAttach attach) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            try {
                // 仅支持单文件上传
                if (iter.hasNext()) {
                    MultipartFile file = multiRequest.getFile(iter.next());

                    // 最大支持上传附件大小限制配置
                    int fileMaxSize = Integer.parseInt(ConfigConstants.getInstance().get("file.attach.maxsize"));

                    // 限制附件大小
                    if (fileMaxSize < file.getSize()) {
                        map.put("error",
                                "附件大小不得超过" + new BigDecimal(fileMaxSize).divide(new BigDecimal(1024 * 1024)).setScale(1, BigDecimal.ROUND_FLOOR) + "！");
                    }

                    // 文件扩展名限制
                    if (!map.containsKey("error")) {
                        String fileExtAllowed = ConfigConstants.getInstance().get("file.attach.ext");
                        String atchExt = (new DyxOrdMetrAttach(file.getOriginalFilename())).getAtchExt();
                        if (StringUtils.isNotBlank(fileExtAllowed) && !Arrays.asList(fileExtAllowed.toLowerCase().split(",")).contains(atchExt)) {
                            map.put("error", "不支持的附件类型！");
                        }
                    }

                    // 保存附件信息
                    if (!map.containsKey("error")) {
                        attach = orderService.updateAttach(attach, file);
                        if (attach == null) {
                            map.put("error", "附件保存失败！");
                        } else {
                            map.put("attach", attach);
                        }
                    }
                } else {
                    map.put("error", "附件上传失败！");
                }
            } catch (Throwable t) {
                map.put("error", "附件保存失败！");
            }
        } else {
            map.put("error", "附件上传失败！");
        }

        // 回写，json格式
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(new MediaType("text", "PLAIN", Charset.forName("UTF-8")));
        return new ResponseEntity<Map<String, Object>>(map, responseHeaders, HttpStatus.OK);
    }

    /**
     * 返回附件图片数据
     * 
     * @param response
     * @param uuid
     * @param name
     */
    @Permission(authId = { UserConstants.USER_AUTH_FQDDYS_CS, UserConstants.USER_AUTH_FQDDYS_FS, UserConstants.USER_AUTH_FQDDGL_CS,
            UserConstants.USER_AUTH_FQDDGL_FS })
    @ResponseBody
    @RequestMapping("/attach/{uuid}")
    public void attach(HttpServletResponse response, @PathVariable String uuid) {
        DyxOrdMetrAttach attach = orderService.getAttach(uuid);
        if (attach != null) {
            File file = new File(ConfigConstants.getInstance().get("file.attach.dir") + uuid + "/" + attach.getAtchName());
            if (file != null && file.exists() && !file.isDirectory()) {
                try {
                    ResponseUtil.writeAsImage(response, file);
                } catch (Throwable e) {
                    System.out.println("图片加载出错！");
                }
            }
        }
    }

    /**
     * 日志列表
     * 
     * @param condition
     * @param model
     * @return
     */
    @Permission(authId = { UserConstants.USER_AUTH_GLDQYHCZRZ, UserConstants.USER_AUTH_GLSYYHCZRZ })
    @RequestMapping("/proc/list")
    public String procList(OrderQueryCondition condition, Model model) {
        // 查询分期订单操作日志页对象
        model.addAttribute("procPage", orderService.findOrderProcWithPage(condition));
        // 返回查询条件
        model.addAttribute("condition", condition);
        // 跳转至列表页面
        return "list/proc";
    }
}
