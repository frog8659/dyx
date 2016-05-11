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

@Repository
public class OrderJdbcDAO extends BaseJdbcDAO {
    private static final String JNDI_EASYBIKE = ConfigConstants.getInstance().get("jdbc.easybike");

    private static final String JNDI_BUSINESS = ConfigConstants.getInstance().get("jdbc.business");

    /**
     * 根据订单号查询消费者订单申请信息
     * 
     * @param orderNo
     * @return
     */
    public Map<String, Object> getConsumerOrderAppInfo(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return null;
        }

        StringBuilder sql = new StringBuilder();
        sql.append(" select a.order_no, a.submited_time, a.in_price, a.recv_method, ");
        sql.append(" concat(b.first_name, b.last_name) as consumer_name, b.mobile, ");
        sql.append(" c.name as merchant_name, c.address, c.region_code, c.code");
        sql.append(" from order_consumer_installment a, consumer b, merchant c where a.consumer_id = b.account_id and a.re_merchant_id = c.id");
        sql.append(" and a.order_no = ?");

        return super.findUnique(JNDI_EASYBIKE, sql.toString(), orderNo);
    }

    /**
     * 根据订单号查询消费者订单审核信息
     * 
     * @param orderNo
     * @return
     */
    public Map<String, Object> getConsumerOrderAuthInfo(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return null;
        }

        return super.findUnique(JNDI_BUSINESS, "select audt_stat from dyx_ord where ord_no = ?", orderNo);
    }

    /**
     * 根据订单号查询消费者订单商品信息
     * 
     * @param orderNo
     * @return
     */
    public List<Map<String, Object>> getConsumerOrderGoodsInfo(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return null;
        }

        String sql = "select goods_name, color_name, transport_price, price, num from order_consumer_installment_item where order_no = ?";

        return super.find(JNDI_EASYBIKE, sql, orderNo);
    }

    // public Map<String, Map<String, Object>> findSubmittedOrderWithMap(List<String> orders) {
    // if (orders == null || !orders.isEmpty()) {
    // return null;
    // }
    //
    // Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
    // Map<String, Object> params = new HashMap<String, Object>();
    //
    // return map;
    // }

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

        if (StringUtils.isNotBlank(condition.getOpUserId())) {
            sql3.append(" and a.consumer_id = :consumerId");
            params.put("consumerId", condition.getOpUserId());
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

        List<Map<String, Object>> result2 = super.find(JNDI_BUSINESS, " select ord_no, audt_stat from dyx_ord where ord_no in (:orders)", params);
        Map<Object, String> map = new HashMap<Object, String>();
        if (result2 != null && !result2.isEmpty()) {
            for (Map<String, Object> r : result2) {
                String stat = (String) r.get("audt_stat");
                map.put(r.get("ord_no"), DicConstants.getInstance().getDicClientStat().get(stat));
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
