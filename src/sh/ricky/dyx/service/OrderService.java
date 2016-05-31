package sh.ricky.dyx.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import sh.ricky.core.bean.Page;
import sh.ricky.core.constant.ConfigConstants;
import sh.ricky.core.constant.DicConstants;
import sh.ricky.core.constant.GlobalConstants;
import sh.ricky.core.util.CascadeUtil;
import sh.ricky.core.util.FormUtil;
import sh.ricky.core.util.ext.WebContext;
import sh.ricky.dyx.bean.CfgFlow;
import sh.ricky.dyx.bean.OrderQueryCondition;
import sh.ricky.dyx.bean.UserInfo;
import sh.ricky.dyx.bo.DyxOrd;
import sh.ricky.dyx.bo.DyxOrdMetr;
import sh.ricky.dyx.bo.DyxOrdMetrAttach;
import sh.ricky.dyx.bo.DyxOrdProc;
import sh.ricky.dyx.constant.UserConstants;
import sh.ricky.dyx.dao.OrderDAO;
import sh.ricky.dyx.dao.OrderJdbcDAO;
import sh.ricky.dyx.util.FlowUtil;

@Service
public class OrderService {
    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderJdbcDAO orderJdbcDAO;

    /**
     * 根据主键获取订单对象
     * 
     * @param id
     * @param mode 延迟加载模式
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public DyxOrd getOrd(String id, int... mode) {
        DyxOrd ord = orderDAO.getOrd(id);

        if (mode.length > 0) {
            switch (mode[0]) {
            case 1:
                CascadeUtil.lazyInit(ord);
                break;
            }
        }

        return ord;
    }

    /**
     * 根据订单编号获取订单对象
     * 
     * @param ordNo
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public DyxOrd getOrdByNo(String ordNo) {
        DyxOrd ord = orderDAO.getOrdByNo(ordNo);
        CascadeUtil.lazyInit(ord);
        return ord;
    }

    /**
     * 根据订单号查询客户端订单详情
     * 
     * @param orderNo
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Map<String, Object> getClientOrderDetail(String orderNo) {
        Map<String, Object> result = new HashMap<String, Object>();

        Map<String, Object> result1 = orderJdbcDAO.getOrderInstInfo(orderNo);

        if (result1 != null && !result1.isEmpty()) {
            result.putAll(result1);
        }

        Map<String, Object> result2 = orderJdbcDAO.getOrderAuthInfo(orderNo);
        if (result2 != null && !result2.isEmpty()) {
            result.put("stat", result2.get("audt_stat"));
        }

        List<Map<String, Object>> result3 = orderJdbcDAO.getOrderGoodsInfo(orderNo);
        if (result3 != null && !result3.isEmpty()) {
            result.putAll(result3.get(0));
        }

        Map<String, Object> result4 = orderJdbcDAO.getOrderRecvInfo(orderNo);
        if (result4 != null && !result4.isEmpty()) {
            result.putAll(result4);
        }

        return result;
    }

    /**
     * 根据订单号查询订单详情
     * 
     * @param orderNo
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Map<String, Object> getOrderDetail(String orderNo) {
        Map<String, Object> result = new HashMap<String, Object>();

        Map<String, Object> result1 = orderJdbcDAO.getOrderInstInfo(orderNo);

        if (result1 != null && !result1.isEmpty()) {
            result.putAll(result1);
        }

        List<Map<String, Object>> result2 = orderJdbcDAO.getOrderGoodsDetail(orderNo);
        if (result2 != null && !result2.isEmpty()) {
            result.putAll(result2.get(0));
        }

        Map<String, Object> result3 = orderJdbcDAO.getOrderAplInfo(orderNo);
        if (result3 != null && !result3.isEmpty()) {
            result.putAll(result3);
        }

        return result;
    }

    /**
     * 根据订单号查询订单申请信息
     * 
     * @param orderNo
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Map<String, Object> getOrderAppInfo(String orderNo) {
        Map<String, Object> result = new HashMap<String, Object>();

        Map<String, Object> result1 = orderJdbcDAO.getOrderRecvInfo(orderNo);
        if (result1 != null && !result1.isEmpty()) {
            result.put("ordDate", result1.get("ord_date"));
            result.put("rec", result1.get("recv_name"));
            result.put("recTel", result1.get("mobile"));
        }

        Map<String, Object> result2 = orderJdbcDAO.getOrderShopInfo(orderNo);
        if (result2 != null && !result2.isEmpty()) {
            result.put("shopName", result2.get("name"));
            result.put("shopNo", result2.get("code"));
            result.put("shopAddr", result2.get("address"));
            result.put("shopRegion", result2.get("region_code"));
        }

        return result;
    }

    /**
     * 根据主键获取附件对象
     * 
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public DyxOrdMetrAttach getAttach(String id) {
        return orderDAO.getAttach(id);
    }

    /**
     * 根据条件查询订单页对象
     * 
     * @param condition
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Page findOrderWithPage(OrderQueryCondition condition) {
        UserInfo user = (UserInfo) WebContext.getSession().getAttribute(GlobalConstants.SESSION_USER);
        if (user == null) {
            return Page.EMPTY_PAGE;
        }

        // 根据功能权和环节分类过滤查询列表
        List<String> authList = user.getAuthList();
        if (authList != null) {
            Map<String, String> dicSeg = DicConstants.getInstance().getDicSeg();
            Map<String, String> dicSegSort = DicConstants.getInstance().getDicSegSort();

            for (String segId : dicSeg.keySet()) {
                String segName = dicSeg.get(segId);
                String segSort = dicSegSort.get(segId);

                if (!StringUtils.equals(segSort, condition.getSegSort())) {
                    continue;
                }

                if (authList.contains(UserConstants.USER_AUTH_FQDDYS_CS) || authList.contains(UserConstants.USER_AUTH_FQDDGL_CS)) {
                    if (segName.matches("(初审)")) {
                        condition.addToAudtStatList(segId);
                        continue;
                    }
                }

                if (authList.contains(UserConstants.USER_AUTH_FQDDYS_FS) || authList.contains(UserConstants.USER_AUTH_FQDDGL_FS)) {
                    if (segName.matches("(复审)")) {
                        condition.addToAudtStatList(segId);
                        continue;
                    }
                }

                if (authList.contains(UserConstants.USER_AUTH_FQDDYS_SHJS) || authList.contains(UserConstants.USER_AUTH_FQDDGL_SHJS)) {
                    if (segName.matches("(审核结束)")) {
                        condition.addToAudtStatList(segId);
                        continue;
                    }
                }
            }
        }

        // 获取订单页对象并加载办理过程信息
        Page page = orderDAO.findOrderWithPage(condition);
        if (page != null) {
            for (DyxOrd ord : (List<DyxOrd>) page.getResult()) {
                ord.getDyxOrdProcSet().iterator();
            }
        }

        return page;
    }

    /**
     * 根据条件查询客户端订单页对象
     * 
     * @param condition
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Page findClientOrderWithPage(OrderQueryCondition condition) {
        return orderJdbcDAO.findOrderWithPage(condition, false);
    }

    /**
     * 根据条件查询订单办理过程页对象
     * 
     * @param condition
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Page findOrderProcWithPage(OrderQueryCondition condition) {
        UserInfo user = (UserInfo) WebContext.getSession().getAttribute(GlobalConstants.SESSION_USER);
        if (user == null) {
            return Page.EMPTY_PAGE;
        }

        // 根据功能权过滤查询列表
        List<String> authList = user.getAuthList();
        if (authList != null) {
            if (!authList.contains(UserConstants.USER_AUTH_GLSYYHCZRZ)) {
                condition.setOpUserId(user.getUserId());
            }
        }

        // 获取分期订单办理过程页对象
        return orderDAO.findOrderProcWithPage(condition);
    }

    /**
     * 保存或更新订单对象
     * 
     * @param ord
     * @param memo
     * @param flow
     * @return
     */
    @Transactional
    public DyxOrd updateOrd(DyxOrd ord, String memo, CfgFlow flow) {
        List<CfgFlow> flowList = FlowUtil.getFlowList(flow);

        if (flowList != null && flowList.size() == 1) {
            flow = flowList.get(0);
            ord.setAudtStat(flow.getPostSeg());
            ord.setLastOpDate(new Date(System.currentTimeMillis()));

            DyxOrdProc proc = new DyxOrdProc();
            proc.setOrdId(ord.getOrdId());
            proc.setOpSeg(flow.getFlowSeg());
            proc.setOpActn(flow.getActnId());
            proc.setOpDate(ord.getLastOpDate());
            proc.setOpMemo(memo);

            UserInfo user = (UserInfo) WebContext.getSession().getAttribute(GlobalConstants.SESSION_USER);
            if (user != null) {
                ord.setLastOpUser(user.getNickName());
                proc.setOpUser(user.getNickName());
                proc.setOpUserId(user.getUserId());
                proc.setOpUserRole(DicConstants.getInstance().getDicRole().get(user.getUserRole()));
            }

            ord.addToDyxOrdProcSet(proc);
        }

        return orderDAO.updateOrd(ord);
    }

    /**
     * 保存或更新附件对象
     * 
     * @param attach
     * @param file
     * @return
     * @throws IOException
     */
    @Transactional
    public DyxOrdMetrAttach updateAttach(DyxOrdMetrAttach attach, MultipartFile file) throws IOException {
        if (StringUtils.isBlank(attach.getAtchType()) || attach.getDispOrd() == null) {
            return null;
        }

        attach.setAtchName(file.getOriginalFilename());
        attach = orderDAO.updateAttach(attach);

        // 以文件格式存储
        if (attach != null) {
            // 读取配置路径
            String path = ConfigConstants.getInstance().get("file.attach.dir") + attach.getAtchId() + "/";
            File dir = new File(path);
            // 检查文件夹是否存在 如果不存在则创建一个
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 转存为文件存储
            file.transferTo(new File(path + file.getOriginalFilename()));
        }

        // 返回更新后的附件对象
        return attach;
    }

    /**
     * 以当前订单数据为条件分析门店分期订单数
     * 
     * @param ord 当前订单对象
     * @param mode 统计模式
     * @return
     */
    public int countOrder(DyxOrd ord, int mode) {
        OrderQueryCondition condition = new OrderQueryCondition();

        switch (mode) {
        case 1:
            condition.setOrdId(ord.getOrdId());
            condition.setOrdNo(ord.getOrdNo());
            break;
        case 2:
            condition.setShopNo(ord.getShopNo());
            break;
        case 3:
        case 4:
        case 5:
            DyxOrdMetr metr = ord.getDyxOrdMetr();
            if (metr == null) {
                metr = new DyxOrdMetr();
            }
            condition.setAplDist(metr.getAplDist());

            switch (mode) {
            case 4:
                condition.setShopDist(ord.getShopDist());
                break;
            case 5:
                condition.setShopCity(ord.getShopCity());
                break;
            }
        }

        return FormUtil.isObjectEmpty(condition) ? 0 : orderDAO.countOrder(condition);
    }
}
