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
     * 根据订单号查询订单门店信息
     *
     * @param orderNo
     * @return
     */
    public Map<String, Object> getOrderShopInfo(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return null;
        }

        StringBuilder sql = new StringBuilder();
        sql.append(" select b.name, b.region_code, b.address, b.code");
        sql.append(" from orders_consumer a, merchant b");
        sql.append(" where a.merchant_id = b.id and a.orderno = ?");

        return super.findUnique(JNDI_EASYBIKE, sql.toString(), orderNo);
    }

    /**
     * 根据订单号查询订单分期信息
     * 
     * @param orderNo
     * @return
     */
    public Map<String, Object> getOrderInstInfo(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return null;
        }

        StringBuilder sql = new StringBuilder("select a.orderno, a.in_price, b.inst_name, b.inst_period, b.sys_time, b.inst_mon_rate");
        sql.append(" from orders_consumer a, orders_consumer_installment b where a.orderno = b.orderno and a.orderno = ?");

        return super.findUnique(JNDI_EASYBIKE, sql.toString(), orderNo);
    }

    /**
     * 根据订单号查询订单申请人信息
     * 
     * @param orderNo
     * @return
     */
    public Map<String, Object> getOrderAplInfo(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return null;
        }

        StringBuilder sql = new StringBuilder("select concat(b.first_name, b.last_name) as consumer, b.telephone, b.mobile");
        sql.append(" from orders_consumer a, consumer b where a.consumer_id = b.account_id and orderno = ?");

        Map<String, Object> result = super.findUnique(JNDI_EASYBIKE, sql.toString(), orderNo);
        if (result != null && !result.isEmpty()) {
            if (StringUtils.isBlank((String) result.get("telephone"))) {
                result.put("telephone", result.get("mobile"));
            }
            result.remove("mobile");
        }

        return result;
    }

    /**
     * 根据订单号查询订单收货信息
     * 
     * @param orderNo
     * @return
     */
    public Map<String, Object> getOrderRecvInfo(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return null;
        }

        StringBuilder sql = new StringBuilder();
        sql.append(" select c.mobile, b.to_consumer_fullname as recv_name, a.createtimestamp as ord_date,");
        sql.append(" b.address_type, b.address, d.name as merchant_name");
        sql.append(" from orders_consumer a, consumer c, consumer_address b left join merchant d");
        sql.append(" on b.merchant_address = d.id");
        sql.append(" where a.re_address = b.id and b.to_consumer_id = c.id");
        sql.append(" and a.orderno = ?");

        return super.findUnique(JNDI_EASYBIKE, sql.toString(), orderNo);
    }

    /**
     * 根据订单号查询订单审核信息
     * 
     * @param orderNo
     * @return
     */
    public Map<String, Object> getOrderAuthInfo(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return null;
        }

        return super.findUnique(JNDI_BUSINESS, "select audt_stat from dyx_ord where ord_no = ?", orderNo);
    }

    /**
     * 根据订单号查询订单商品信息
     * 
     * @param orderNo
     * @return
     */
    public List<Map<String, Object>> getOrderGoodsInfo(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return null;
        }

        String sql = "select product_name, color_name, transport_price, price, number from orders_consumer_item where orderno = ?";

        return super.find(JNDI_EASYBIKE, sql, orderNo);
    }

    /**
     * 根据订单号查询订单商品详情信息
     * 
     * @param orderNo
     * @return
     */
    public List<Map<String, Object>> getOrderGoodsDetail(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return null;
        }

        StringBuilder sql = new StringBuilder("select a.color_name, a.number, b.current_price, b.original_price, d.fullname,");
        sql.append(" e.name as brand, f.name as category, g.name as configuration");
        sql.append(" from orders_consumer_item a, goods b, product_detail c, product_template d,");
        sql.append(" product_brand e, product_category f, product_configuration g");
        sql.append(" where a.goods_id = b.id and b.product_detail_id = c.id and c.product_id = d.id");
        sql.append(" and d.brand_id = e.id and d.category_id = f.id and d.configuration_id = g.id");
        sql.append(" and a.orderno = ?");

        return super.find(JNDI_EASYBIKE, sql.toString(), orderNo);
    }

    /**
     * 根据条件查询订单页对象
     * 
     * @param condition
     * @param ifCount
     * @return
     */
    @SuppressWarnings("unchecked")
    public Page findOrderWithPage(OrderQueryCondition condition, boolean ifCount) {
        if (condition == null) {
            return Page.EMPTY_PAGE;
        }

        Map<String, Object> params = new HashMap<String, Object>();

        StringBuilder sql1 = new StringBuilder(" select a.orderno, concat(b.first_name, b.last_name) as consumer, b.telephone, b.mobile");
        StringBuilder sql2 = new StringBuilder(" select count(*)");
        StringBuilder sql3 = new StringBuilder(" from orders_consumer a, consumer b where a.consumer_id = b.account_id and is_installment = 1");
        StringBuilder sql4 = new StringBuilder(" order by a.id");

        if (DBUtil.isMySql()) {
            sql4.append(" limit ").append((condition.getPageNo() - 1) * condition.getPageSize()).append(", ").append(condition.getPageSize());
        }

        if (StringUtils.isNotBlank(condition.getOpUserId())) {
            sql3.append(" and exists (select 1 from account_dealer_merchant");
            sql3.append(" where a.merchant_id = merchant_id and merchant_id != 0 and account_id = :accountId)");
            params.put("accountId", condition.getOpUserId());
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
            ((List<Object>) params.get("orders")).add(r.get("orderno"));
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
            String stat = map.get(r.get("orderno"));
            r.put("stat", StringUtils.isBlank(stat) ? "订单已生成" : stat);

            if (StringUtils.isBlank((String) r.get("telephone"))) {
                r.put("telephone", r.get("mobile"));
            }
            r.remove("mobile");
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
