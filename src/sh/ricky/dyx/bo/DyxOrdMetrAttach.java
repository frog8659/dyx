package sh.ricky.dyx.bo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import sun.misc.BASE64Encoder;

import sh.ricky.core.constant.ConfigConstants;

/**
 * 分期订单材料附件表
 * 
 * @author SHI
 */
@Entity
@Table(name = "DYX_ORD_METR_ATTACH")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true, selectBeforeUpdate = true)
public class DyxOrdMetrAttach implements java.io.Serializable {

    // Fields

    private static final long serialVersionUID = -641789402443257973L;

    private String atchId;

    private String metrId;

    private String atchType;

    private String atchName;

    private Integer dispOrd;

    private DyxOrdMetr dyxOrdMetr;

    public DyxOrdMetrAttach() {
    }

    public DyxOrdMetrAttach(String atchName) {
        this.atchName = atchName;
    }

    // Property accessors

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "ATCH_ID", unique = true, nullable = false, insertable = true, updatable = true, length = 32)
    public String getAtchId() {
        return atchId;
    }

    public void setAtchId(String atchId) {
        this.atchId = atchId;
    }

    @Column(name = "METR_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getMetrId() {
        return metrId;
    }

    public void setMetrId(String metrId) {
        this.metrId = metrId;
    }

    @Column(name = "ATCH_TYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
    public String getAtchType() {
        return atchType;
    }

    public void setAtchType(String atchType) {
        this.atchType = atchType;
    }

    @Column(name = "ATCH_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getAtchName() {
        return atchName;
    }

    public void setAtchName(String atchName) {
        this.atchName = atchName;
    }

    @Transient
    public String getAtchTitle() {
        return StringUtils.isBlank(atchName) || atchName.indexOf(".") == -1 ? "" : atchName.substring(0, atchName.lastIndexOf("."));
    }

    @Transient
    public String getAtchExt() {
        return StringUtils.isBlank(atchName) || atchName.indexOf(".") == -1 ? "" : atchName.substring(atchName.lastIndexOf(".") + 1).toLowerCase();
    }

    @Transient
    public String getAtchPreview() {
        if (StringUtils.isBlank(atchName)) {
            return null;
        } else {
            try {
                BufferedImage im = ImageIO.read(new File(ConfigConstants.getInstance().get("file.attach.dir") + atchId + "/" + atchName));
                im = Thumbnails.of(im).size(30, 30).keepAspectRatio(false).asBufferedImage();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(im, this.getAtchExt(), bos);
                return (new BASE64Encoder()).encode(bos.toByteArray());
            } catch (IOException e) {
                return null;
            }
        }
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
        result = prime * result + ((atchId == null) ? 0 : atchId.hashCode());
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
        DyxOrdMetrAttach other = (DyxOrdMetrAttach) obj;
        if (StringUtils.isBlank(atchId)) {
            return false;
        } else if (!atchId.equals(other.atchId)) {
            return false;
        }
        return true;
    }
}
