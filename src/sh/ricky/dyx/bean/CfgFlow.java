package sh.ricky.dyx.bean;

/**
 * 流程
 * 
 * @author SHI
 */
public class CfgFlow implements java.io.Serializable {

    private static final long serialVersionUID = -482596496684378339L;

    private String cfgId;

    private String flowId;

    private String flowSeg;

    private String actnId;

    private String postSeg;

    private Integer dispOrd;

    private String valid;

    public String getCfgId() {
        return cfgId;
    }

    public void setCfgId(String cfgId) {
        this.cfgId = cfgId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowSeg() {
        return flowSeg;
    }

    public void setFlowSeg(String flowSeg) {
        this.flowSeg = flowSeg;
    }

    public String getActnId() {
        return actnId;
    }

    public void setActnId(String actnId) {
        this.actnId = actnId;
    }

    public String getPostSeg() {
        return postSeg;
    }

    public void setPostSeg(String postSeg) {
        this.postSeg = postSeg;
    }

    public Integer getDispOrd() {
        return dispOrd;
    }

    public void setDispOrd(Integer dispOrd) {
        this.dispOrd = dispOrd;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

}
