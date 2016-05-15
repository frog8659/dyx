package sh.ricky.dyx.form;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import sh.ricky.core.util.CascadeUtil;
import sh.ricky.core.util.FormUtil;
import sh.ricky.dyx.bean.CfgFlow;
import sh.ricky.dyx.bo.DyxOrd;
import sh.ricky.dyx.bo.DyxOrdEval;
import sh.ricky.dyx.bo.DyxOrdMetr;
import sh.ricky.dyx.bo.DyxOrdMetrAttach;
import sh.ricky.dyx.bo.DyxOrdMetrContact;
import sh.ricky.dyx.util.SysUtil;

public class OrderForm {

    private DyxOrd ord = new DyxOrd();

    private DyxOrdMetr metr = new DyxOrdMetr();

    private List<DyxOrdMetrContact> contact;

    private List<DyxOrdMetrAttach> attach;

    private List<DyxOrdEval> eval;

    private CfgFlow flow = new CfgFlow();

    private String token;

    private String memo;

    /**
     * 从页面获取信息，并保存至订单对象
     * 
     * @param dyxOrd 当前已保存的订单对象
     */
    public void update(DyxOrd dyxOrd) {
        FormUtil.copyProperties(dyxOrd, this.ord);

        // 更新订单令牌
        dyxOrd.setToken(String.valueOf((new Date(System.currentTimeMillis())).getTime()));

        if (dyxOrd.getDyxOrdMetr() == null) {
            dyxOrd.setDyxOrdMetr(this.metr);
        } else {
            FormUtil.copyProperties(dyxOrd.getDyxOrdMetr(), this.metr);
        }

        if (this.contact != null) {
            if (dyxOrd.getDyxOrdMetr().getDyxOrdMetrContactSet() == null) {
                Set<DyxOrdMetrContact> dyxOrdMetrContactSet = new LinkedHashSet<DyxOrdMetrContact>();
                dyxOrdMetrContactSet.addAll(this.contact);
                dyxOrd.getDyxOrdMetr().setDyxOrdMetrContactSet(dyxOrdMetrContactSet);
            } else {
                FormUtil.updateSet(dyxOrd.getDyxOrdMetr().getDyxOrdMetrContactSet(), this.contact);
            }
        }

        if (this.attach != null) {
            if (dyxOrd.getDyxOrdMetr().getDyxOrdMetrAttachSet() == null) {
                Set<DyxOrdMetrAttach> dyxOrdMetrAttachSet = new LinkedHashSet<DyxOrdMetrAttach>();
                dyxOrdMetrAttachSet.addAll(this.attach);
                dyxOrd.getDyxOrdMetr().setDyxOrdMetrAttachSet(dyxOrdMetrAttachSet);
            } else {
                FormUtil.updateSet(dyxOrd.getDyxOrdMetr().getDyxOrdMetrAttachSet(), this.attach);
            }
        }

        if (this.eval != null) {
            if (dyxOrd.getDyxOrdEvalSet() == null) {
                Set<DyxOrdEval> dyxOrdEvalSet = new LinkedHashSet<DyxOrdEval>();
                dyxOrdEvalSet.addAll(this.eval);
                dyxOrd.setDyxOrdEvalSet(dyxOrdEvalSet);
            } else {
                FormUtil.updateSet(dyxOrd.getDyxOrdEvalSet(), this.eval);
            }
        }

        // 批量设置分期订单主键
        CascadeUtil.setRefProperty(dyxOrd, StringUtils.isBlank(dyxOrd.getOrdId()) ? SysUtil.randomUUID() : dyxOrd.getOrdId(), "ordId");

        // 批量设置分期订单申请材料主键
        DyxOrdMetr dyxOrdMetr = dyxOrd.getDyxOrdMetr();
        CascadeUtil.setRefProperty(dyxOrdMetr, StringUtils.isBlank(dyxOrdMetr.getMetrId()) ? SysUtil.randomUUID() : dyxOrdMetr.getMetrId(), "metrId");

        // 级联清除订单对象中的空集合，防止数据库中保存空记录（即所有属性均为null或空字符串的记录）
        CascadeUtil.clearObject(dyxOrd, Arrays.asList("ordId", "metrId", "contId", "atchId", "evalId", "evalName", "dispOrd"));
    }

    public DyxOrd getOrd() {
        return ord;
    }

    public void setOrd(DyxOrd ord) {
        this.ord = ord;
    }

    public DyxOrdMetr getMetr() {
        return metr;
    }

    public void setMetr(DyxOrdMetr metr) {
        this.metr = metr;
    }

    public List<DyxOrdMetrContact> getContact() {
        return contact;
    }

    public void setContact(List<DyxOrdMetrContact> contact) {
        this.contact = contact;
    }

    public List<DyxOrdMetrAttach> getAttach() {
        return attach;
    }

    public void setAttach(List<DyxOrdMetrAttach> attach) {
        this.attach = attach;
    }

    public List<DyxOrdEval> getEval() {
        return eval;
    }

    public void setEval(List<DyxOrdEval> eval) {
        this.eval = eval;
    }

    public CfgFlow getFlow() {
        return flow;
    }

    public void setFlow(CfgFlow flow) {
        this.flow = flow;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}