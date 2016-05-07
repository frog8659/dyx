package sh.ricky.dyx.bean;

import org.apache.commons.lang.StringUtils;

import sh.ricky.core.constant.ConfigConstants;

public class QueryCondition {
    private Integer pageSize;

    private Integer pageNo;

    public Integer getPageSize() {
        if (pageSize == null) {
            String conf = ConfigConstants.getInstance().get("page.size");
            if (StringUtils.isNotBlank(conf)) {
                pageSize = new Integer(conf);
            }
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo == null ? 1 : pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}
