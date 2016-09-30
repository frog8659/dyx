package sh.ricky.dyx.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import sh.ricky.core.bean.Page;
import sh.ricky.core.constant.DicConstants;
import sh.ricky.core.dao.BaseDAO;
import sh.ricky.core.util.DateUtil;
import sh.ricky.dyx.bean.OrderQueryCondition;
import sh.ricky.dyx.bo.DyxOrd;
import sh.ricky.dyx.bo.DyxOrdMetrAttach;

@Repository
public class OrderDAO extends BaseDAO {

    /**
     * 根据主键获取订单对象
     * 
     * @param id
     * @return
     */
    public DyxOrd getOrd(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        return super.get(DyxOrd.class, id);
    }

    /**
     * 根据订单编号获取订单对象
     * 
     * @param ordNo
     * @return
     */
    public DyxOrd getOrdByNo(String ordNo) {
        return super.findUniqueByJPQL("from DyxOrd where ordNo = ?1", ordNo);
    }

    /**
     * 根据主键获取附件对象
     * 
     * @param id
     * @return
     */
    public DyxOrdMetrAttach getAttach(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        return super.get(DyxOrdMetrAttach.class, id);
    }

    /**
     * 保存或更新订单对象
     * 
     * @param ord
     * @return
     */
    public DyxOrd updateOrd(DyxOrd ord) {
        return super.update(ord);
    }

    /**
     * 保存或更新附件对象
     * 
     * @param attach
     * @return
     */
    public DyxOrdMetrAttach updateAttach(DyxOrdMetrAttach attach) {
        return super.update(attach);
    }

    /**
     * 根据条件查询订单页对象
     * 
     * @param condition
     * @return
     */
    public Page findOrderWithPage(OrderQueryCondition condition) {
        if (condition == null) {
            return Page.EMPTY_PAGE;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder jpql = new StringBuilder("from DyxOrd where 1 = 1");

        if (condition.getAudtStatList() != null && !condition.getAudtStatList().isEmpty()) {
            jpql.append(" and audtStat in (:audtStatList)");
            params.put("audtStatList", condition.getAudtStatList());
        } else {
            return Page.EMPTY_PAGE;
        }

        if (StringUtils.isNotBlank(condition.getOrdType())) {
            jpql.append(" and ordType = :ordType");
            params.put("ordType", condition.getOrdType());
        }

        if (StringUtils.isNotBlank(condition.getOrdDate())) {
            jpql.append(" and ordDate between :ordDateBef and :ordDateAft");

            Date ordDate = DateUtil.string2Date(condition.getOrdDate(), null);
            ordDate = DateUtil.string2Date(DateUtil.date2String(ordDate, DateUtil.FORMAT_DATE) + " 00:00:00", null);

            params.put("ordDateBef", ordDate);
            params.put("ordDateAft", DateUtil.addDays(ordDate, 1));
        }

        if (StringUtils.isNotBlank(condition.getShopProv())) {
            jpql.append(" and shopProv = :shopProv");
            params.put("shopProv", condition.getShopProv());
        }

        if (StringUtils.isNotBlank(condition.getShopCity())) {
            jpql.append(" and shopCity = :shopCity");
            params.put("shopCity", condition.getShopCity());
        }

        if (StringUtils.isNotBlank(condition.getShopDist())) {
            jpql.append(" and shopDist = :shopDist");
            params.put("shopDist", condition.getShopDist());
        }

        if (StringUtils.isNotBlank(condition.getAudtStat())) {
            jpql.append(" and audtStat in (:audtStat)");
            params.put("audtStat", DicConstants.getInstance().getDicAudtStat().get(condition.getSegSort()).get(condition.getAudtStat()));
        }

        if (StringUtils.isNotBlank(condition.getKeyword())) {
            jpql.append(" and (rec like :keyword or recTel like :keyword or shopName like :keyword or agt like :keyword or agtTel like :keyword)");
            params.put("keyword", "%" + condition.getKeyword() + "%");
        }

        // 根据订单日期倒序
        jpql.append(" order by ordDate desc");
        
        return super.findByJPQLWithPage(jpql.toString(), condition.getPageNo(), condition.getPageSize(), params);
    }

    /**
     * 根据条件查询订单办理过程页对象
     * 
     * @param condition
     * @return
     */
    public Page findOrderProcWithPage(OrderQueryCondition condition) {
        if (condition == null) {
            return Page.EMPTY_PAGE;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder jpql = new StringBuilder("from DyxOrdProc p, DyxOrd o where p.ordId = o.ordId");

        if (StringUtils.isNotBlank(condition.getOrdDate())) {
            jpql.append(" and o.ordDate between :ordDateBef and :ordDateAft");

            Date ordDate = DateUtil.string2Date(condition.getOrdDate(), null);
            ordDate = DateUtil.string2Date(DateUtil.date2String(ordDate, DateUtil.FORMAT_DATE) + " 00:00:00", null);

            params.put("ordDateBef", ordDate);
            params.put("ordDateAft", DateUtil.addDays(ordDate, 1));
        }

        if (StringUtils.isNotBlank(condition.getOrdNo())) {
            jpql.append(" and o.ordNo = :ordNo");
            params.put("ordNo", condition.getOrdNo());
        }

        if (StringUtils.isNotBlank(condition.getOpUserId())) {
            jpql.append(" and p.opUserId = :opUserId");
            params.put("opUserId", condition.getOpUserId());
        }

        if (StringUtils.isNotBlank(condition.getKeyword())) {
            jpql.append(" and (o.rec like :keyword or o.agt like :keyword)");
            params.put("keyword", "%" + condition.getKeyword() + "%");
        }

        jpql.append(" order by p.opDate");

        return super.findByJPQLWithPage(jpql.toString(), condition.getPageNo(), condition.getPageSize(), params);
    }

    /**
     * 根据条件统计分期订单数
     * 
     * @param condition 统计条件
     * @return
     */
    public int countOrder(OrderQueryCondition condition) {
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder jpql = new StringBuilder("select count(*) from DyxOrd o, DyxOrdMetr m where o.ordId = m.ordId");

        if (StringUtils.isNotBlank(condition.getOrdId())) {
            jpql.append(" and o.ordId != :ordId");
            params.put("ordId", condition.getOrdId());
        }

        if (StringUtils.isNotBlank(condition.getOrdNo())) {
            jpql.append(" and o.ordNo = :ordNo");
            params.put("ordNo", condition.getOrdNo());
        }

        if (StringUtils.isNotBlank(condition.getShopNo())) {
            jpql.append(" and o.shopNo = :shopNo");
            params.put("shopNo", condition.getShopNo());
        }

        if (StringUtils.isNotBlank(condition.getShopCity())) {
            jpql.append(" and o.shopCity = :shopCity");
            params.put("shopCity", condition.getShopCity());
        }

        if (StringUtils.isNotBlank(condition.getShopDist())) {
            jpql.append(" and o.shopDist = :shopDist");
            params.put("shopDist", condition.getShopDist());
        }

        if (StringUtils.isNotBlank(condition.getAplDist())) {
            jpql.append(" and m.aplDist = :aplDist");
            params.put("aplDist", condition.getAplDist());
        }

        return super.countByJPQL(jpql.toString(), params);
    }
}
