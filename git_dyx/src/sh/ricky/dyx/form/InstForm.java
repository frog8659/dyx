package sh.ricky.dyx.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import sh.ricky.core.util.CascadeUtil;
import sh.ricky.core.util.FormUtil;
import sh.ricky.dyx.bo.DyxInst;
import sh.ricky.dyx.bo.DyxInstRule;
import sh.ricky.dyx.util.SysUtil;

public class InstForm {

    private DyxInst inst = new DyxInst();

    private List<DyxInstRule> rule = new ArrayList<DyxInstRule>();

    /**
     * 从页面获取信息，并保存至分期业务对象
     * 
     * @param inst 当前已保存的分期业务对象
     */
    public void update(DyxInst inst) {
        FormUtil.copyProperties(inst, this.inst);

        if (inst.getDyxInstRuleSet() == null) {
            Set<DyxInstRule> dyxInstRuleSet = new LinkedHashSet<DyxInstRule>();
            dyxInstRuleSet.addAll(this.rule);
            inst.setDyxInstRuleSet(dyxInstRuleSet);
        } else {
            FormUtil.updateSet(inst.getDyxInstRuleSet(), this.rule);
        }

        // 批量设置分期业务主键
        CascadeUtil.setRefProperty(inst, StringUtils.isBlank(inst.getInstId()) ? SysUtil.randomUUID() : inst.getInstId(), "instId");

        // 级联清除分期业务对象中的空集合，防止数据库中保存空记录（即所有属性均为null或空字符串的记录）
        CascadeUtil.clearObject(inst, Arrays.asList("instId", "ruleId", "dispOrd"));
    }

    public DyxInst getInst() {
        return inst;
    }

    public void setInst(DyxInst inst) {
        this.inst = inst;
    }

    public List<DyxInstRule> getRule() {
        return rule;
    }

    public void setRule(List<DyxInstRule> rule) {
        this.rule = rule;
    }

}