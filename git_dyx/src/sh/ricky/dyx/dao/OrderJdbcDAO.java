package sh.ricky.dyx.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import sh.ricky.core.bean.Page;
import sh.ricky.core.constant.ConfigConstants;
import sh.ricky.core.constant.DicConstants;
import sh.ricky.core.dao.BaseJdbcDAO;
import sh.ricky.core.util.DBUtil;
import sh.ricky.dyx.bean.OrderQueryCondition;
import sh.ricky.dyx.constant.OrderConstants;

@Repository
public class OrderJdbcDAO extends BaseJdbcDAO {
    private static final String JNDI_EASYBIKE = ConfigConstants.getInstance().get("jdbc.easybike");

    private static final String JNDI_BUSINESS = ConfigConstants.getInstance().get("jdbc.business");

    /**
     * 根据订单号查询消费者订单详情
     * 
     * @param orderNo
     * @return
     */
    public Map<String, Object> getConsumerOrderDetail(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return null;
        }

        StringBuilder sql = new StringBuilder();
        sql.append(" select a.order_no, concat(b.first_name, b.last_name) as consumer_name, b.mobile, a.submited_time, c.name as merchant_name, c.address");
        sql.append(" from order_consumer_installment a, consumer b, merchant c where a.consumer_id = b.account_id and a.re_merchant_id = c.id");
        sql.append(" and a.order_no = ?");

        Map<String, Object> result = super.findUnique(JNDI_EASYBIKE, sql.toString(), orderNo);

        if (result == null || result.isEmpty()) {
            return null;
        }

        Map<String, Object> result2 = super.findUnique(JNDI_BUSINESS, " select audt_stat from dyx_ord where ord_no = ?", orderNo);
        if (result2 != null && !result2.isEmpty()) {
            result.put("stat", result2.get("audt_stat"));
        }

        List<Map<String, Object>> result3 = super.find(JNDI_EASYBIKE,
                "select goods_name, color_name, transport_price, price, num from order_consumer_installment_item where order_no = ?", orderNo);
        if (result3 != null && !result3.isEmpty()) {
            result.put("goods", result3);
        }

        return result;
    }

    /**
     * 根据条件查询消费者订单页对象
     * 
     * @param condition
     * @param ifCount
     * @return
     */
    @SuppressWarnings("unchecked")
    public Page findConsumerOrderWithPage(OrderQueryCondition condition, boolean ifCount) {
        if (condition == null) {
            return Page.EMPTY_PAGE;
        }

        Map<String, Object> params = new HashMap<String, Object>();

        StringBuilder sql1 = new StringBuilder(" select a.order_no, concat(b.first_name, b.last_name) as name, b.telephone");
        StringBuilder sql2 = new StringBuilder(" select count(*)");
        StringBuilder sql3 = new StringBuilder(" from order_consumer_installment a, consumer b where a.consumer_id = b.account_id");
        StringBuilder sql4 = new StringBuilder("");

        if (DBUtil.isMySql()) {
            sql4.append(" limit ").append((condition.getPageNo() - 1) * condition.getPageSize()).append(", ").append(condition.getPageSize());
        }

        if (StringUtils.isNotBlank(condition.getConsumerId())) {
            sql3.append(" and a.consumer_id = :consumerId");
            params.put("consumerId", condition.getConsumerId());
        }

        if (StringUtils.isNotBlank(condition.getConsumerLogicId())) {
            sql3.append(" and b.id = :logicId");
            params.put("logicId", condition.getConsumerLogicId());
        }

        List<Map<String, Object>> result = super.find(JNDI_EASYBIKE, sql1.append(sql3).append(sql4).toString(), params);
        if (result == null || result.isEmpty()) {
            return Page.EMPTY_PAGE;
        }

        int count = ifCount ? super.count(JNDI_EASYBIKE, sql2.append(sql3).toString(), params) : 0;

        for (Map<String, Object> r : result) {
            if (!params.containsKey("orders")) {
                params.put("orders", new ArrayList<Object>());
            }
            ((List<Object>) params.get("orders")).add(r.get("order_no"));
        }

        List<Map<String, Object>> result2 = super.find(JNDI_BUSINESS, " select ord_no, audt_stat, ord_type from dyx_ord where ord_no in (:orders)", params);
        Map<Object, String> map = new HashMap<Object, String>();
        if (result2 != null && !result2.isEmpty()) {
            for (Map<String, Object> r : result2) {
                String stat = (String) r.get("audt_stat");
                String type = (String) r.get("ord_type");

                if (OrderConstants.ORD_TYPE_ZZBL.equals(type)) {
                    map.put(r.get("ord_no"), "门店已代办");
                } else if (OrderConstants.ORD_TYPE_KFDB.equals(type)) {
                    map.put(r.get("ord_no"), "自主上传订单");
                } else {
                    map.put(r.get("ord_no"), DicConstants.getInstance().getDicSegStat().get(stat));
                }
            }
        }

        for (Map<String, Object> r : result) {
            String stat = map.get(r.get("order_no"));
            r.put("stat", StringUtils.isBlank(stat) ? "订单已生成" : stat);
        }

        Page page = new Page();
        page.setResult(result);
        page.setPageNo(condition.getPageNo());
        page.setPageSize(condition.getPageSize());
        page.setCount(count);
        page.setPageCount((count / condition.getPageSize()) + 1);

        return page;
    }
}
