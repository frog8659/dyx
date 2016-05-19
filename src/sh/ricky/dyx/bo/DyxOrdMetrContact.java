package sh.ricky.dyx.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

/**
 * 分期订单材料联系人表
 * 
 * @author SHI
 */
@Entity
@Table(name = "DYX_ORD_METR_CONTACT")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true, selectBeforeUpdate = true)
public class DyxOrdMetrContact implements java.io.Serializable {

    // Fields

    private static final long serialVersionUID = 6327390055291324793L;

    private String contId;

    private String metrId;

    private String name;

    private String tel;

    private String rel;

    private Integer dispOrd;

    private DyxOrdMetr dyxOrdMetr;

    // Property accessors

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "CONT_ID", unique = true, nullable = false, insertable = true, updatable = true, length = 32)
    public String getContId() {
        return contId;
    }

    public void setContId(String contId) {
        this.contId = contId;
    }

    @Column(name = "METR_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getMetrId() {
        return metrId;
    }

    public void setMetrId(String metrId) {
        this.metrId = metrId;
    }

    @Column(name = "NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "TEL", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Column(name = "REL", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    @Column(name = "DISP_ORD", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getDispOrd() {
        return dispOrd;
    }

    public void setDispOrd(Integer dispOrd) {
        this.dispOrd = dispOrd;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "METR_ID", insertable = false, updatable = false)
    public DyxOrdMetr getDyxOrdMetr() {
        return dyxOrdMetr;
    }

    public void setDyxOrdMetr(DyxOrdMetr dyxOrdMetr) {
        this.dyxOrdMetr = dyxOrdMetr;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contId == null) ? 0 : contId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DyxOrdMetrContact other = (DyxOrdMetrContact) obj;
        if (StringUtils.isBlank(contId)) {
            return false;
        } else if (!contId.equals(other.contId)) {
            return false;
        }
        return true;
    }
}
