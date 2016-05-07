package sh.ricky.dyx.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import sh.ricky.dyx.constant.OrderConstants;

/**
 * 订单办理过程表
 * 
 * @author SHI
 */
@Entity
@Table(name = "DYX_ORD_PROC")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true, selectBeforeUpdate = true)
public class DyxOrdProc implements java.io.Serializable {

    // Fields

    private static final long serialVersionUID = -5661451741507844934L;

    private String procId;

    private String ordId;

    private Date opDate;

    private String opUser;

    private String opUserId;

    private String opUserRole;

    private String opSeg;

    private String opActn;

    private String opMemo;

    private DyxOrd dyxOrd;

    // Property accessors

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "PROC_ID", unique = true, nullable = false, insertable = true, updatable = true, length = 32)
    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    @Column(name = "ORD_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getOrdId() {
        return ordId;
    }

    public void setOrdId(String ordId) {
        this.ordId = ordId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OP_DATE", unique = false, nullable = true, insertable = true, updatable = true)
    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    @Column(name = "OP_USER", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    @Column(name = "OP_USER_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }

    @Column(name = "OP_USER_ROLE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getOpUserRole() {
        return opUserRole;
    }

    public void setOpUserRole(String opUserRole) {
        this.opUserRole = opUserRole;
    }

    @Column(name = "OP_SEG", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
    public String getOpSeg() {
        return opSeg;
    }

    public void setOpSeg(String opSeg) {
        this.opSeg = opSeg;
    }

    @Column(name = "OP_ACTN", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
    public String getOpActn() {
        return opActn;
    }

    public void setOpActn(String opActn) {
        this.opActn = opActn;
    }

    @Column(name = "OP_MEMO", unique = false, nullable = true, insertable = true, updatable = true, length = 1000)
    public String getOpMemo() {
        return opMemo;
    }

    public void setOpMemo(String opMemo) {
        this.opMemo = opMemo;
    }

    @Transient
    public String getOpMenu() {
        if (dyxOrd != null) {
            if (StringUtils.equals(dyxOrd.getOrdType(), OrderConstants.ORD_TYPE_ZZBL)) {
                return "分期订单预审-自主办理订单";
            }

            if (StringUtils.equals(dyxOrd.getOrdType(), OrderConstants.ORD_TYPE_KFDB)) {
                return "分期订单预审-客服代办订单";
            }
        }

        return null;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORD_ID", insertable = false, updatable = false)
    public DyxOrd getDyxOrd() {
        return dyxOrd;
    }

    public void setDyxOrd(DyxOrd dyxOrd) {
        this.dyxOrd = dyxOrd;
    }
}
