package sh.ricky.dyx.bo;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

/**
 * 分期业务管理
 * 
 * @author SHI
 */
@Entity
@Table(name = "DYX_INST")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true, selectBeforeUpdate = true)
public class DyxInst implements java.io.Serializable {

    // Fields

    private static final long serialVersionUID = -709258023843779849L;

    private String instId;

    private String instName;

    private Set<DyxInstRule> dyxInstRuleSet;

    // Property accessors

    @Id
    @Column(name = "INST_ID", unique = true, nullable = false, insertable = true, updatable = true, length = 32)
    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    @Column(name = "INST_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dyxInst")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinColumn(name = "INST_ID")
    @OrderBy("dispOrd")
    public Set<DyxInstRule> getDyxInstRuleSet() {
        return dyxInstRuleSet;
    }

    @Transient
    public Integer getDyxInstRuleCount() {
        return dyxInstRuleSet == null ? 0 : dyxInstRuleSet.size();
    }

    public void setDyxInstRuleSet(Set<DyxInstRule> dyxInstRuleSet) {
        this.dyxInstRuleSet = dyxInstRuleSet;
    }
}
