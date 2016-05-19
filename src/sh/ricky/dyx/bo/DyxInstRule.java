package sh.ricky.dyx.bo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 分期业务规则
 * 
 * @author SHI
 */
@Entity
@Table(name = "DYX_INST_RULE")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true, selectBeforeUpdate = true)
public class DyxInstRule implements java.io.Serializable {

    // Fields

    private static final long serialVersionUID = -2340931284344087559L;

    private String ruleId;

    private String instId;

    private String instPeriod;

    private BigDecimal instMonRate;

    private BigDecimal instFee;

    private String repMethod;

    private BigDecimal dwPayAmt;

    private String instStat;

    private Integer dispOrd;

    private DyxInst dyxInst;

    // Property accessors

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "RULE_ID", unique = true, nullable = false, insertable = true, updatable = true, length = 32)
    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    @Column(name = "INST_ID", unique = true, nullable = false, insertable = true, updatable = true, length = 32)
    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    @Column(name = "INST_PERIOD", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getInstPeriod() {
        return instPeriod;
    }

    public void setInstPeriod(String instPeriod) {
        this.instPeriod = instPeriod;
    }

    @Column(name = "INST_MON_RATE", unique = false, nullable = true, insertable = true, updatable = true)
    public BigDecimal getInstMonRate() {
        return instMonRate;
    }

    public void setInstMonRate(BigDecimal instMonRate) {
        this.instMonRate = instMonRate;
    }

    @Column(name = "INST_FEE", unique = false, nullable = true, insertable = true, updatable = true)
    public BigDecimal getInstFee() {
        return instFee;
    }

    public void setInstFee(BigDecimal instFee) {
        this.instFee = instFee;
    }

    @Column(name = "REP_METHOD", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
    public String getRepMethod() {
        return repMethod;
    }

    public void setRepMethod(String repMethod) {
        this.repMethod = repMethod;
    }

    @Column(name = "DW_PAY_AMT", unique = false, nullable = true, insertable = true, updatable = true)
    public BigDecimal getDwPayAmt() {
        return dwPayAmt;
    }

    public void setDwPayAmt(BigDecimal dwPayAmt) {
        this.dwPayAmt = dwPayAmt;
    }

    @Column(name = "INST_STAT", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
    public String getInstStat() {
        return instStat;
    }

    public void setInstStat(String instStat) {
        this.instStat = instStat;
    }

    @Column(name = "DISP_ORD", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getDispOrd() {
        return dispOrd;
    }

    public void setDispOrd(Integer dispOrd) {
        this.dispOrd = dispOrd;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INST_ID", insertable = false, updatable = false)
    public DyxInst getDyxInst() {
        return dyxInst;
    }

    public void setDyxInst(DyxInst dyxInst) {
        this.dyxInst = dyxInst;
    }

}
