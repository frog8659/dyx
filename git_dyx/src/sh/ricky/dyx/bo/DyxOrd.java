package sh.ricky.dyx.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
 * 订单表
 * 
 * @author SHI
 */
@Entity
@Table(name = "DYX_ORD")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true, selectBeforeUpdate = true)
public class DyxOrd implements java.io.Serializable {

    // Fields

    private static final long serialVersionUID = -751092736951484215L;

    private String ordId;

    private String ordNo;

    private String ordType;

    private Date ordDate;

    private BigDecimal ordAmt;

    private String rec;

    private String recTel;

    private String agt;

    private String agtNo;

    private String agtTel;

    private String shopNo;

    private String shopName;

    private String shopProv;

    private String shopCity;

    private String shopDist;

    private String shopAddr;

    private String prdName;

    private String prdBrand;

    private String prdType;

    private String prdSpec;

    private String prdColor;

    private Integer prdCount;

    private BigDecimal prdOriPric;

    private BigDecimal prdCurPric;

    private String audtStat;

    private String bankStat;

    private Date lastOpDate;

    private String lastOpUser;

    private String token;

    private Set<DyxOrdMetr> dyxOrdMetrSet;

    private Set<DyxOrdEval> dyxOrdEvalSet;

    private Set<DyxOrdProc> dyxOrdProcSet;

    // Property accessors

    @Id
    @Column(name = "ORD_ID", unique = true, nullable = false, insertable = true, updatable = true, length = 32)
    public String getOrdId() {
        return ordId;
    }

    public void setOrdId(String ordId) {
        this.ordId = ordId;
    }

    @Column(name = "ORD_NO", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(String ordNo) {
        this.ordNo = ordNo;
    }

    @Column(name = "ORD_TYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
    public String getOrdType() {
        return ordType;
    }

    public void setOrdType(String ordType) {
        this.ordType = ordType;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ORD_DATE", unique = false, nullable = true, insertable = true, updatable = true)
    public Date getOrdDate() {
        return ordDate;
    }

    public void setOrdDate(Date ordDate) {
        this.ordDate = ordDate;
    }

    @Column(name = "ORD_AMT", unique = false, nullable = true, insertable = true, updatable = true)
    public BigDecimal getOrdAmt() {
        return ordAmt;
    }

    public void setOrdAmt(BigDecimal ordAmt) {
        this.ordAmt = ordAmt;
    }

    @Column(name = "REC", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    @Column(name = "REC_TEL", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
    public String getRecTel() {
        return recTel;
    }

    public void setRecTel(String recTel) {
        this.recTel = recTel;
    }

    @Column(name = "AGT", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getAgt() {
        return agt;
    }

    public void setAgt(String agt) {
        this.agt = agt;
    }

    @Column(name = "AGT_NO", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getAgtNo() {
        return agtNo;
    }

    public void setAgtNo(String agtNo) {
        this.agtNo = agtNo;
    }

    @Column(name = "AGT_TEL", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
    public String getAgtTel() {
        return agtTel;
    }

    public void setAgtTel(String agtTel) {
        this.agtTel = agtTel;
    }

    @Column(name = "SHOP_NO", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    @Column(name = "SHOP_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Column(name = "SHOP_PROV", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getShopProv() {
        return shopProv;
    }

    public void setShopProv(String shopProv) {
        this.shopProv = shopProv;
    }

    @Column(name = "SHOP_CITY", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getShopCity() {
        return shopCity;
    }

    public void setShopCity(String shopCity) {
        this.shopCity = shopCity;
    }

    @Column(name = "SHOP_DIST", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getShopDist() {
        return shopDist;
    }

    public void setShopDist(String shopDist) {
        this.shopDist = shopDist;
    }

    @Column(name = "SHOP_ADDR", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    @Column(name = "PRD_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    @Column(name = "PRD_BRAND", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getPrdBrand() {
        return prdBrand;
    }

    public void setPrdBrand(String prdBrand) {
        this.prdBrand = prdBrand;
    }

    @Column(name = "PRD_TYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public String getPrdType() {
        return prdType;
    }

    public void setPrdType(String prdType) {
        this.prdType = prdType;
    }

    @Column(name = "PRD_SPEC", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getPrdSpec() {
        return prdSpec;
    }

    public void setPrdSpec(String prdSpec) {
        this.prdSpec = prdSpec;
    }

    @Column(name = "PRD_COLOR", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getPrdColor() {
        return prdColor;
    }

    public void setPrdColor(String prdColor) {
        this.prdColor = prdColor;
    }

    @Column(name = "PRD_COUNT", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getPrdCount() {
        return prdCount;
    }

    public void setPrdCount(Integer prdCount) {
        this.prdCount = prdCount;
    }

    @Column(name = "PRD_ORI_PRIC", unique = false, nullable = true, insertable = true, updatable = true)
    public BigDecimal getPrdOriPric() {
        return prdOriPric;
    }

    public void setPrdOriPric(BigDecimal prdOriPric) {
        this.prdOriPric = prdOriPric;
    }

    @Column(name = "PRD_CUR_PRIC", unique = false, nullable = true, insertable = true, updatable = true)
    public BigDecimal getPrdCurPric() {
        return prdCurPric;
    }

    public void setPrdCurPric(BigDecimal prdCurPric) {
        this.prdCurPric = prdCurPric;
    }

    @Column(name = "AUDT_STAT", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
    public String getAudtStat() {
        return audtStat;
    }

    public void setAudtStat(String audtStat) {
        this.audtStat = audtStat;
    }

    @Column(name = "BANK_STAT", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
    public String getBankStat() {
        return bankStat;
    }

    public void setBankStat(String bankStat) {
        this.bankStat = bankStat;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_OP_DATE", unique = false, nullable = true, insertable = true, updatable = true)
    public Date getLastOpDate() {
        return lastOpDate;
    }

    public void setLastOpDate(Date lastOpDate) {
        this.lastOpDate = lastOpDate;
    }

    @Column(name = "LAST_OP_USER", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getLastOpUser() {
        return lastOpUser;
    }

    public void setLastOpUser(String lastOpUser) {
        this.lastOpUser = lastOpUser;
    }

    @Column(name = "TOKEN", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dyxOrd")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinColumn(name = "ORD_ID")
    public Set<DyxOrdMetr> getDyxOrdMetrSet() {
        return dyxOrdMetrSet;
    }

    @Transient
    public DyxOrdMetr getDyxOrdMetr() {
        if (dyxOrdMetrSet != null) {
            for (DyxOrdMetr metr : dyxOrdMetrSet) {
                return metr;
            }
        }
        return null;
    }

    public void setDyxOrdMetrSet(Set<DyxOrdMetr> dyxOrdMetrSet) {
        this.dyxOrdMetrSet = dyxOrdMetrSet;
    }

    public void setDyxOrdMetr(DyxOrdMetr metr) {
        (this.dyxOrdMetrSet = new HashSet<DyxOrdMetr>()).add(metr);
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dyxOrd")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinColumn(name = "ORD_ID")
    public Set<DyxOrdEval> getDyxOrdEvalSet() {
        return dyxOrdEvalSet;
    }

    @Transient
    public Map<String, DyxOrdEval> getDyxOrdEvalMap() {
        if (dyxOrdEvalSet == null) {
            return null;
        }

        Map<String, DyxOrdEval> map = new LinkedHashMap<String, DyxOrdEval>();
        for (DyxOrdEval eval : dyxOrdEvalSet) {
            String evalName = eval.getEvalName();
            if (StringUtils.isNotBlank(evalName)) {
                map.put(evalName, eval);
            }
        }
        return map;
    }

    public void setDyxOrdEvalSet(Set<DyxOrdEval> dyxOrdEvalSet) {
        this.dyxOrdEvalSet = dyxOrdEvalSet;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dyxOrd")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinColumn(name = "ORD_ID")
    @OrderBy("opDate")
    public Set<DyxOrdProc> getDyxOrdProcSet() {
        return dyxOrdProcSet;
    }

    @Transient
    public DyxOrdProc getLastDyxOrdProc() {
        if (dyxOrdProcSet != null && !dyxOrdProcSet.isEmpty()) {
            Object[] arr = dyxOrdProcSet.toArray();
            return (DyxOrdProc) arr[arr.length - 1];
        }
        return null;
    }

    public void setDyxOrdProcSet(Set<DyxOrdProc> dyxOrdProcSet) {
        this.dyxOrdProcSet = dyxOrdProcSet;
    }

    public void addToDyxOrdProcSet(DyxOrdProc proc) {
        if (this.dyxOrdProcSet == null) {
            this.dyxOrdProcSet = new HashSet<DyxOrdProc>();
        }
        this.dyxOrdProcSet.add(proc);
    }

}
