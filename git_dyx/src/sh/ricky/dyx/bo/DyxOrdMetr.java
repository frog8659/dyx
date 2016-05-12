package sh.ricky.dyx.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;

/**
 * 订单申请材料表
 * 
 * @author SHI
 */
@Entity
@Table(name = "DYX_ORD_METR")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true, selectBeforeUpdate = true)
public class DyxOrdMetr implements java.io.Serializable {

    // Fields

    private static final long serialVersionUID = 2746751974060407864L;

    private String metrId;

    private String ordId;

    private String metrNo;

    private Date metrDate;

    private String instOrg;

    private String instName;

    private String instPeriod;

    private BigDecimal instAmt;

    private BigDecimal instOopAmt;

    private BigDecimal instMonIntrRate;

    private BigDecimal instMonServRate;

    private BigDecimal instPermRate;

    private BigDecimal instRepAmt;

    private String instRepDate;

    private String apl;

    private String aplName;

    private String aplTel;

    private String aplMob;

    private String aplMail;

    private String aplIdc;

    private Date aplIdcExp;

    private String aplBank;

    private String aplBankCard;

    private String aplBankMob;

    private Integer aplAge;

    private String aplSex;

    private String aplEdu;

    private String aplMari;

    private String aplProv;

    private String aplCity;

    private String aplDist;

    private String aplAddr;

    private String aplHomeStat;

    private String aplResdPeriod;

    private String aplEmpType;

    private String aplCompany;

    private String aplCompAddr;

    private String aplCompTel;

    private String aplCompNatu;

    private String aplCompSize;

    private String aplJobTitle;

    private String aplJobYear;

    private BigDecimal aplJobIncome;

    private String aplJobSpec;

    private String tabAudt;

    private DyxOrd dyxOrd;

    private Set<DyxOrdMetrAttach> dyxOrdMetrAttachSet;

    private Set<DyxOrdMetrContact> dyxOrdMetrContactSet;

    // Property accessors

    @Id
    @Column(name = "METR_ID", unique = true, nullable = false, insertable = true, updatable = true, length = 32)
    public String getMetrId() {
        return metrId;
    }

    public void setMetrId(String metrId) {
        this.metrId = metrId;
    }

    @Column(name = "ORD_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getOrdId() {
        return ordId;
    }

    public void setOrdId(String ordId) {
        this.ordId = ordId;
    }

    @Column(name = "METR_NO", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getMetrNo() {
        return metrNo;
    }

    public void setMetrNo(String metrNo) {
        this.metrNo = metrNo;
    }

    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "METR_DATE", unique = false, nullable = true, insertable = true, updatable = true)
    public Date getMetrDate() {
        return metrDate;
    }

    public void setMetrDate(Date metrDate) {
        this.metrDate = metrDate;
    }

    @Transient
    @Column(name = "INST_ORG", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getInstOrg() {
        return instOrg;
    }

    public void setInstOrg(String instOrg) {
        this.instOrg = instOrg;
    }

    @Transient
    @Column(name = "INST_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    @Transient
    @Column(name = "INST_PERIOD", unique = false, nullable = true, insertable = true, updatable = true, length = 5)
    public String getInstPeriod() {
        return instPeriod;
    }

    public void setInstPeriod(String instPeriod) {
        this.instPeriod = instPeriod;
    }

    @Transient
    @Column(name = "INST_AMT", unique = false, nullable = true, insertable = true, updatable = true)
    public BigDecimal getInstAmt() {
        return instAmt;
    }

    public void setInstAmt(BigDecimal instAmt) {
        this.instAmt = instAmt;
    }

    @Transient
    @Column(name = "INST_OOP_AMT", unique = false, nullable = true, insertable = true, updatable = true)
    public BigDecimal getInstOopAmt() {
        return instOopAmt;
    }

    public void setInstOopAmt(BigDecimal instOopAmt) {
        this.instOopAmt = instOopAmt;
    }

    @Transient
    @Column(name = "INST_MON_INTR_RATE", unique = false, nullable = true, insertable = true, updatable = true)
    public BigDecimal getInstMonIntrRate() {
        return instMonIntrRate;
    }

    public void setInstMonIntrRate(BigDecimal instMonIntrRate) {
        this.instMonIntrRate = instMonIntrRate;
    }

    @Transient
    @Column(name = "INST_MON_SERV_RATE", unique = false, nullable = true, insertable = true, updatable = true)
    public BigDecimal getInstMonServRate() {
        return instMonServRate;
    }

    public void setInstMonServRate(BigDecimal instMonServRate) {
        this.instMonServRate = instMonServRate;
    }

    @Transient
    @Column(name = "INST_PREM_RATE", unique = false, nullable = true, insertable = true, updatable = true)
    public BigDecimal getInstPermRate() {
        return instPermRate;
    }

    public void setInstPermRate(BigDecimal instPermRate) {
        this.instPermRate = instPermRate;
    }

    @Transient
    @Column(name = "INST_REP_AMT", unique = false, nullable = true, insertable = true, updatable = true)
    public BigDecimal getInstRepAmt() {
        return instRepAmt;
    }

    public void setInstRepAmt(BigDecimal instRepAmt) {
        this.instRepAmt = instRepAmt;
    }

    @Transient
    @Column(name = "INST_REP_DATE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getInstRepDate() {
        return instRepDate;
    }

    public void setInstRepDate(String instRepDate) {
        this.instRepDate = instRepDate;
    }

    @Transient
    @Column(name = "APL", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getApl() {
        return apl;
    }

    public void setApl(String apl) {
        this.apl = apl;
    }

    @Column(name = "APL_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getAplName() {
        return aplName;
    }

    public void setAplName(String aplName) {
        this.aplName = aplName;
    }

    @Transient
    @Column(name = "APL_TEL", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
    public String getAplTel() {
        return aplTel;
    }

    public void setAplTel(String aplTel) {
        this.aplTel = aplTel;
    }

    @Column(name = "APL_MOB", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
    public String getAplMob() {
        return aplMob;
    }

    public void setAplMob(String aplMob) {
        this.aplMob = aplMob;
    }

    @Column(name = "APL_MAIL", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getAplMail() {
        return aplMail;
    }

    public void setAplMail(String aplMail) {
        this.aplMail = aplMail;
    }

    @Column(name = "APL_IDC", unique = false, nullable = true, insertable = true, updatable = true, length = 18)
    public String getAplIdc() {
        return aplIdc;
    }

    public void setAplIdc(String aplIdc) {
        this.aplIdc = aplIdc;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "APL_IDC_EXP", unique = false, nullable = true, insertable = true, updatable = true)
    public Date getAplIdcExp() {
        return aplIdcExp;
    }

    public void setAplIdcExp(Date aplIdcExp) {
        this.aplIdcExp = aplIdcExp;
    }

    @Column(name = "APL_BANK", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getAplBank() {
        return aplBank;
    }

    public void setAplBank(String aplBank) {
        this.aplBank = aplBank;
    }

    @Column(name = "APL_BANK_CARD", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getAplBankCard() {
        return aplBankCard;
    }

    public void setAplBankCard(String aplBankCard) {
        this.aplBankCard = aplBankCard;
    }

    @Column(name = "APL_BANK_MOB", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
    public String getAplBankMob() {
        return aplBankMob;
    }

    public void setAplBankMob(String aplBankMob) {
        this.aplBankMob = aplBankMob;
    }

    @Column(name = "APL_AGE", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getAplAge() {
        return aplAge;
    }

    public void setAplAge(Integer aplAge) {
        this.aplAge = aplAge;
    }

    @Column(name = "APL_SEX", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
    public String getAplSex() {
        return aplSex;
    }

    public void setAplSex(String aplSex) {
        this.aplSex = aplSex;
    }

    @Column(name = "APL_EDU", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getAplEdu() {
        return aplEdu;
    }

    public void setAplEdu(String aplEdu) {
        this.aplEdu = aplEdu;
    }

    @Column(name = "APL_MARI", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getAplMari() {
        return aplMari;
    }

    public void setAplMari(String aplMari) {
        this.aplMari = aplMari;
    }

    @Column(name = "APL_PROV", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getAplProv() {
        return aplProv;
    }

    public void setAplProv(String aplProv) {
        this.aplProv = aplProv;
    }

    @Column(name = "APL_CITY", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getAplCity() {
        return aplCity;
    }

    public void setAplCity(String aplCity) {
        this.aplCity = aplCity;
    }

    @Column(name = "APL_DIST", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getAplDist() {
        return aplDist;
    }

    public void setAplDist(String aplDist) {
        this.aplDist = aplDist;
    }

    @Column(name = "APL_ADDR", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getAplAddr() {
        return aplAddr;
    }

    public void setAplAddr(String aplAddr) {
        this.aplAddr = aplAddr;
    }

    @Column(name = "APL_HOME_STAT", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getAplHomeStat() {
        return aplHomeStat;
    }

    public void setAplHomeStat(String aplHomeStat) {
        this.aplHomeStat = aplHomeStat;
    }

    @Column(name = "APL_RESD_PERIOD", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public String getAplResdPeriod() {
        return aplResdPeriod;
    }

    public void setAplResdPeriod(String aplResdPeriod) {
        this.aplResdPeriod = aplResdPeriod;
    }

    @Column(name = "APL_EMP_TYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public String getAplEmpType() {
        return aplEmpType;
    }

    public void setAplEmpType(String aplEmpType) {
        this.aplEmpType = aplEmpType;
    }

    @Column(name = "APL_COMPANY", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getAplCompany() {
        return aplCompany;
    }

    public void setAplCompany(String aplCompany) {
        this.aplCompany = aplCompany;
    }

    @Column(name = "APL_COMP_ADDR", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getAplCompAddr() {
        return aplCompAddr;
    }

    public void setAplCompAddr(String aplCompAddr) {
        this.aplCompAddr = aplCompAddr;
    }

    @Column(name = "APL_COMP_TEL", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
    public String getAplCompTel() {
        return aplCompTel;
    }

    public void setAplCompTel(String aplCompTel) {
        this.aplCompTel = aplCompTel;
    }

    @Column(name = "APL_COMP_NATU", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getAplCompNatu() {
        return aplCompNatu;
    }

    public void setAplCompNatu(String aplCompNatu) {
        this.aplCompNatu = aplCompNatu;
    }

    @Column(name = "APL_COMP_SIZE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getAplCompSize() {
        return aplCompSize;
    }

    public void setAplCompSize(String aplCompSize) {
        this.aplCompSize = aplCompSize;
    }

    @Column(name = "APL_JOB_TITLE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getAplJobTitle() {
        return aplJobTitle;
    }

    public void setAplJobTitle(String aplJobTitle) {
        this.aplJobTitle = aplJobTitle;
    }

    @Column(name = "APL_JOB_YEAR", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getAplJobYear() {
        return aplJobYear;
    }

    public void setAplJobYear(String aplJobYear) {
        this.aplJobYear = aplJobYear;
    }

    @Column(name = "APL_JOB_INCOME", unique = false, nullable = true, insertable = true, updatable = true)
    public BigDecimal getAplJobIncome() {
        return aplJobIncome;
    }

    public void setAplJobIncome(BigDecimal aplJobIncome) {
        this.aplJobIncome = aplJobIncome;
    }

    @Column(name = "APL_JOB_SPEC", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getAplJobSpec() {
        return aplJobSpec;
    }

    public void setAplJobSpec(String aplJobSpec) {
        this.aplJobSpec = aplJobSpec;
    }

    @Column(name = "TAB_AUDT", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public String getTabAudt() {
        return tabAudt;
    }

    public void setTabAudt(String tabAudt) {
        this.tabAudt = tabAudt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORD_ID", insertable = false, updatable = false)
    public DyxOrd getDyxOrd() {
        return dyxOrd;
    }

    public void setDyxOrd(DyxOrd dyxOrd) {
        this.dyxOrd = dyxOrd;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dyxOrdMetr")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinColumn(name = "METR_ID")
    @OrderBy("atchType, dispOrd")
    public Set<DyxOrdMetrAttach> getDyxOrdMetrAttachSet() {
        return dyxOrdMetrAttachSet;
    }

    @Transient
    public Map<String, Map<String, DyxOrdMetrAttach>> getDyxOrdMetrAttachMap() {
        if (dyxOrdMetrAttachSet == null || dyxOrdMetrAttachSet.isEmpty()) {
            return null;
        }

        Map<String, Map<String, DyxOrdMetrAttach>> map = new LinkedHashMap<String, Map<String, DyxOrdMetrAttach>>();
        for (DyxOrdMetrAttach attach : dyxOrdMetrAttachSet) {
            String type = attach.getAtchType();
            Integer dispOrd = attach.getDispOrd();
            if (StringUtils.isBlank(type) || dispOrd == null) {
                continue;
            }
            if (!map.containsKey(type)) {
                map.put(type, new LinkedHashMap<String, DyxOrdMetrAttach>());
            }
            map.get(type).put(dispOrd.toString(), attach);
        }
        return map;
    }

    public void setDyxOrdMetrAttachSet(Set<DyxOrdMetrAttach> dyxOrdMetrAttachSet) {
        this.dyxOrdMetrAttachSet = dyxOrdMetrAttachSet;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dyxOrdMetr")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinColumn(name = "METR_ID")
    @OrderBy("dispOrd")
    public Set<DyxOrdMetrContact> getDyxOrdMetrContactSet() {
        return dyxOrdMetrContactSet;
    }

    @Transient
    public Map<String, DyxOrdMetrContact> getDyxOrdMetrContactMap() {
        if (dyxOrdMetrContactSet == null || dyxOrdMetrContactSet.isEmpty()) {
            return null;
        }

        Map<String, DyxOrdMetrContact> map = new LinkedHashMap<String, DyxOrdMetrContact>();
        for (DyxOrdMetrContact contact : dyxOrdMetrContactSet) {
            Integer dispOrd = contact.getDispOrd();
            if (dispOrd == null) {
                continue;
            }
            map.put(dispOrd.toString(), contact);
        }
        return map;
    }

    public void setDyxOrdMetrContactSet(Set<DyxOrdMetrContact> dyxOrdMetrContactSet) {
        this.dyxOrdMetrContactSet = dyxOrdMetrContactSet;
    }
}
