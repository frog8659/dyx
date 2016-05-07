package sh.ricky.dyx.bean;

import java.util.ArrayList;
import java.util.List;

public class OrderQueryCondition extends QueryCondition {
    private String ordId;

    private String ordNo;

    private String ordType;

    private String ordDate;

    private String shopNo;

    private String shopProv;

    private String shopCity;

    private String shopDist;

    private String audtStat;

    private String keyword;

    private String aplDist;

    private String opUserId;

    private String consumerId;

    private String consumerLogicId;

    private List<String> audtStatList;

    public String getOrdId() {
        return ordId;
    }

    public void setOrdId(String ordId) {
        this.ordId = ordId;
    }

    public String getOrdNo() {
        return ordNo;
    }

    public void setOrdNo(String ordNo) {
        this.ordNo = ordNo;
    }

    public String getOrdType() {
        return ordType;
    }

    public void setOrdType(String ordType) {
        this.ordType = ordType;
    }

    public String getOrdDate() {
        return ordDate;
    }

    public void setOrdDate(String ordDate) {
        this.ordDate = ordDate;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getShopProv() {
        return shopProv;
    }

    public void setShopProv(String shopProv) {
        this.shopProv = shopProv;
    }

    public String getShopCity() {
        return shopCity;
    }

    public void setShopCity(String shopCity) {
        this.shopCity = shopCity;
    }

    public String getShopDist() {
        return shopDist;
    }

    public void setShopDist(String shopDist) {
        this.shopDist = shopDist;
    }

    public String getAudtStat() {
        return audtStat;
    }

    public void setAudtStat(String audtStat) {
        this.audtStat = audtStat;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAplDist() {
        return aplDist;
    }

    public void setAplDist(String aplDist) {
        this.aplDist = aplDist;
    }

    public String getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getConsumerLogicId() {
        return consumerLogicId;
    }

    public void setConsumerLogicId(String consumerLogicId) {
        this.consumerLogicId = consumerLogicId;
    }

    public List<String> getAudtStatList() {
        return audtStatList;
    }

    public void setAudtStatList(List<String> audtStatList) {
        this.audtStatList = audtStatList;
    }

    public void addToAudtStatList(String stat) {
        if (this.audtStatList == null) {
            this.audtStatList = new ArrayList<String>();
        }
        this.audtStatList.add(stat);
    }

}
