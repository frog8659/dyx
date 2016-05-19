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
 * 分期订单评价表
 * 
 * @author SHI
 */
@Entity
@Table(name = "DYX_ORD_EVAL")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true, selectBeforeUpdate = true)
public class DyxOrdEval implements java.io.Serializable {

    // Fields

    private static final long serialVersionUID = -6344395332982232019L;

    private String evalId;

    private String ordId;

    private String evalName;

    private String evalCont;

    private DyxOrd dyxOrd;

    // Property accessors

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "EVAL_ID", unique = true, nullable = false, insertable = true, updatable = true, length = 32)
    public String getEvalId() {
        return evalId;
    }

    public void setEvalId(String evalId) {
        this.evalId = evalId;
    }

    @Column(name = "ORD_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getOrdId() {
        return ordId;
    }

    public void setOrdId(String ordId) {
        this.ordId = ordId;
    }

    @Column(name = "EVAL_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getEvalName() {
        return evalName;
    }

    public void setEvalName(String evalName) {
        this.evalName = evalName;
    }

    @Column(name = "EVAL_CONT", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getEvalCont() {
        return evalCont;
    }

    public void setEvalCont(String evalCont) {
        this.evalCont = evalCont;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORD_ID", insertable = false, updatable = false)
    public DyxOrd getDyxOrd() {
        return dyxOrd;
    }

    public void setDyxOrd(DyxOrd dyxOrd) {
        this.dyxOrd = dyxOrd;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((evalId == null) ? 0 : evalId.hashCode());
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
        DyxOrdEval other = (DyxOrdEval) obj;
        if (StringUtils.isBlank(evalId)) {
            return false;
        } else if (!evalId.equals(other.evalId)) {
            return false;
        }
        return true;
    }
}
