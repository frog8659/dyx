package sh.ricky.dyx.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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
     * 根据订单号查询消费者订单详情
     * 
     * @param orderNo
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Map<String, Object> getConsumerOrderDetail(String orderNo) {
        return orderJdbcDAO.getConsumerOrderDetail(orderNo);
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

        // 根据功能权过滤查询列表
        List<String> authList = user.getAuthList();
        if (authList != null) {
            Map<String, String> dicSeg = DicConstants.getInstance().getDicSeg();

            for (String segId : dicSeg.keySet()) {
                String segName = dicSeg.get(segId);

                if (authList.contains(UserConstants.USER_AUTH_CS)) {
                    if (segName.matches("(初审)")) {
                        condition.addToAudtStatList(segId);
                        continue;
                    }
                }

                if (authList.contains(UserConstants.USER_AUTH_FS)) {
                    if (segName.matches("(复审)")) {
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
     * 根据条件查询消费者订单页对象
     * 
     * @param condition
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Page findConsumerOrderWithPage(OrderQueryCondition condition) {
        return orderJdbcDAO.findConsumerOrderWithPage(condition, false);
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
        if (StringUtils.isBlank(attach.getAtchType()) || attach.getDispOrd() == null || StringUtils.isBlank(attach.getMetrId())) {
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
