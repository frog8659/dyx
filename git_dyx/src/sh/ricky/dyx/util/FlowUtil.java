package sh.ricky.dyx.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import sh.ricky.core.constant.DicConstants;
import sh.ricky.dyx.bean.CfgFlow;

/**
 * 流程相关工具类
 */
public class FlowUtil {
    /**
     * 根据条件获取流程配置（流程编号为必要条件）
     * 
     * @param condition
     * @return
     */
    public static List<CfgFlow> getFlowList(CfgFlow condition) {
        List<CfgFlow> flowList = new ArrayList<CfgFlow>();

        if (condition == null || StringUtils.isBlank(condition.getFlowId())) {
            return flowList;
        }

        List<CfgFlow> list = DicConstants.getInstance().getDicFlowCfg().get(condition.getFlowId());

        if (list != null) {
            for (CfgFlow flow : list) {
                if (StringUtils.isNotBlank(condition.getActnId()) && !StringUtils.equals(flow.getActnId(), condition.getActnId())) {
                    continue;
                }

                if (StringUtils.isNotBlank(condition.getFlowSeg()) && !StringUtils.equals(flow.getFlowSeg(), condition.getFlowSeg())) {
                    continue;
                }

                if (StringUtils.isNotBlank(condition.getPostSeg()) && !StringUtils.equals(flow.getPostSeg(), condition.getPostSeg())) {
                    continue;
                }

                flowList.add(flow);
            }
        }

        return flowList;
    }
}
