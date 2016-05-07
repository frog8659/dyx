package sh.ricky.dyx.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sh.ricky.core.util.CascadeUtil;
import sh.ricky.dyx.bean.InstQueryCondition;
import sh.ricky.dyx.bo.DyxInst;
import sh.ricky.dyx.bo.DyxInstRule;
import sh.ricky.dyx.dao.InstDAO;

@Service
@Transactional
public class InstService {
    @Autowired
    private InstDAO instDAO;

    /**
     * 根据主键获取分期业务对象
     * 
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public DyxInst getInst(String id) {
        DyxInst inst = instDAO.getInst(id);
        CascadeUtil.lazyInit(inst);
        return inst;
    }

    /**
     * 根据主键获取分期业务规则对象
     * 
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public DyxInstRule getInstRule(String id) {
        return instDAO.getInstRule(id);
    }

    /**
     * 保存或更新分期业务对象
     * 
     * @param inst
     * @return
     */
    public DyxInst updateInst(DyxInst inst) {
        return instDAO.updateInst(inst);
    }

    /**
     * 保存或更新分期业务规则对象
     * 
     * @param rule
     * @return
     */
    public DyxInstRule updateInstRule(DyxInstRule rule) {
        return instDAO.updateInstRule(rule);
    }

    /**
     * 删除分期业务对象
     * 
     * @param inst
     * @return
     */
    public void deleteInst(DyxInst inst) {
        if (inst != null && StringUtils.isNotBlank(inst.getInstId())) {
            instDAO.deleteInst(inst);
        }
    }

    /**
     * 删除分期业务规则
     * 
     * @param rule
     * @return
     */
    public boolean deleteInstRule(String ruleId) {
        boolean dicRefresh = false;

        if (StringUtils.isNotBlank(ruleId)) {
            int count = instDAO.countInstRule(ruleId);
            if (count == 1) {
                if (instDAO.deleteInstByRule(ruleId) == 1) {
                    dicRefresh = true;
                }
            }

            instDAO.deleteInstRule(ruleId);
        }

        return dicRefresh;
    }

    /**
     * 校验分期业务是否符合规范
     * 
     * @param inst
     * @return
     */
    public String verifyInst(DyxInst inst) {
        int count = instDAO.countInst(inst.getInstName());

        if (count > 0) {
            return "已存在相同名称的分期业务！";
        }

        List<String> list = new ArrayList<String>();
        for (DyxInstRule rule : inst.getDyxInstRuleSet()) {
            String key = "@" + rule.getInstPeriod() + "@" + rule.getRepMethod();
            if (list.contains(key)) {
                return "存在分期周期及还款方式完全相同的多条规则，不符合业务逻辑！";
            }
            list.add(key);
        }

        return "";
    }

    /**
     * 根据条件查询分期业务列表
     * 
     * @param condition
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<DyxInst> findInstWithList(InstQueryCondition condition) {
        List<DyxInst> list = instDAO.findInstWithList(condition);
        CascadeUtil.lazyInit(list);
        return list;
    }
}
