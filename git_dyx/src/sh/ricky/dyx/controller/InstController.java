package sh.ricky.dyx.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sh.ricky.core.constant.DicConstants;
import sh.ricky.dyx.annotation.Permission;
import sh.ricky.dyx.bean.InstQueryCondition;
import sh.ricky.dyx.bo.DyxInst;
import sh.ricky.dyx.bo.DyxInstRule;
import sh.ricky.dyx.constant.UserConstants;
import sh.ricky.dyx.form.InstForm;
import sh.ricky.dyx.service.InstService;

/**
 * 分期业务
 * 
 * @author SHI
 */
@Controller
@Permission(authId = UserConstants.USER_AUTH_FQYWGL)
@RequestMapping("/inst")
public class InstController {
    @Autowired
    private InstService instService;

    /**
     * 分期业务列表
     * 
     * @param condition
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(InstQueryCondition condition, Model model) {
        model.addAttribute("instList", instService.findInstWithList(condition));
        model.addAttribute("condition", condition);
        return "list/inst";
    }

    /**
     * 分期业务编辑窗口
     * 
     * @param instId
     * @param model
     * @return
     */
    @RequestMapping("/edit")
    public String edit(String instId, Model model) {
        DyxInst inst = instService.getInst(instId);
        if (inst == null) {
            inst = new DyxInst();
        }

        model.addAttribute("inst", inst);
        return "popup/inst";
    }

    /**
     * 分期业务编辑更新
     * 
     * @param form
     * @return
     */
    @ResponseBody
    @RequestMapping("/upd")
    public String update(InstForm form) {
        try {
            // 是否需要刷新分期业务字典
            boolean dicRefresh = false;

            // 根据表单信息从数据库获取分期业务对象
            DyxInst inst = instService.getInst(form.getInst().getInstId());

            // 分期业务对象为空，则为新增操作
            if (inst == null) {
                inst = new DyxInst();
                // 需要刷新分期业务字典
                dicRefresh = true;
            }

            // 根据表单内容更新分期业务对象
            form.update(inst);

            // 检查是否符合业务规则
            String msg = instService.verifyInst(inst);
            if (StringUtils.isNotBlank(msg)) {
                return msg;
            }

            if (inst.getDyxInstRuleSet() == null || inst.getDyxInstRuleSet().isEmpty()) {
                // 分期业务规则为空，则删除分期业务对象（若为新增操作，则不作删除）
                if (!dicRefresh) {
                    instService.deleteInst(inst);
                }

                // 需要刷新分期业务字典
                dicRefresh = true;
            } else {
                // 保存或更新分期业务对象
                instService.updateInst(inst);
            }

            // 刷新分期业务字典
            if (dicRefresh) {
                DicConstants.getInstance().refreshDicInstName();
            }
        } catch (Throwable t) {
            return "分期业务信息未能成功保存！";
        }

        // 返回处理结果
        return "";
    }

    /**
     * 分期业务规则状态更新
     * 
     * @param ruleId
     * @param stat
     * @return
     */
    @ResponseBody
    @RequestMapping("/rule/upd_stat")
    public String updateRuleStat(String ruleId, String stat) {
        try {
            // 根据规则主键从数据库获取分期业务对象
            DyxInstRule rule = instService.getInstRule(ruleId);

            // 分期业务规则对象为空
            if (rule == null) {
                return "找不到符合条件的分期业务规则！";
            }

            // 更新状态
            rule.setInstStat(stat);

            // 保存或更新分期业务规则对象
            instService.updateInstRule(rule);
        } catch (Throwable t) {
            return "分期业务规则状态更新失败！";
        }

        // 返回处理结果
        return "";
    }

    /**
     * 分期业务规则删除
     * 
     * @param ruleId
     * @return
     */
    @ResponseBody
    @RequestMapping("/rule/del")
    public String deleteRule(String ruleId) {
        try {
            // 分期业务规则ID为空
            if (StringUtils.isBlank(ruleId)) {
                return "找不到符合条件的分期业务规则！";
            }

            // 删除分期业务规则
            if (instService.deleteInstRule(ruleId)) {
                // 刷新分期业务字典
                DicConstants.getInstance().refreshDicInstName();
            }
        } catch (Throwable t) {
            return "分期业务规则删除失败！";
        }

        // 返回处理结果
        return "";
    }
}
