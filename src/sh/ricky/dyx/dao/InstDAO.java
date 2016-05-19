package sh.ricky.dyx.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import sh.ricky.core.dao.BaseDAO;
import sh.ricky.dyx.bean.InstQueryCondition;
import sh.ricky.dyx.bo.DyxInst;
import sh.ricky.dyx.bo.DyxInstRule;

@Repository
public class InstDAO extends BaseDAO {
    /**
     * 根据主键获取分期业务对象
     * 
     * @param id
     * @return
     */
    public DyxInst getInst(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        return super.get(DyxInst.class, id);
    }

    /**
     * 根据主键获取分期业务规则对象
     * 
     * @param id
     * @return
     */
    public DyxInstRule getInstRule(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        return super.get(DyxInstRule.class, id);
    }

    /**
     * 保存或更新分期业务对象
     * 
     * @param inst
     * @return
     */
    public DyxInst updateInst(DyxInst inst) {
        return super.update(inst);
    }

    /**
     * 保存或更新分期业务规则对象
     * 
     * @param rule
     * @return
     */
    public DyxInstRule updateInstRule(DyxInstRule rule) {
        return super.update(rule);
    }

    /**
     * 删除分期业务对象
     * 
     * @param inst
     * @return
     */
    public void deleteInst(DyxInst inst) {
        super.delete(inst);
    }

    /**
     * 根据分期业务规则主键删除对应的分期业务对象
     * 
     * @param ruleId
     * @return
     */
    public int deleteInstByRule(String ruleId) {
        Query query = em.createQuery("delete from DyxInst a where exists (select 1 from DyxInstRule where instId = a.instId and ruleId = ?1)");
        query.setParameter(1, ruleId);
        return query.executeUpdate();
    }

    /**
     * 根据主键删除分期业务规则对象
     * 
     * @param ruleId
     * @return
     */
    public int deleteInstRule(String ruleId) {
        Query query = em.createQuery("delete from DyxInstRule where ruleId = ?1");
        query.setParameter(1, ruleId);
        return query.executeUpdate();
    }

    /**
     * 根据条件查询分期业务列表
     * 
     * @param condition
     * @return
     */
    public List<DyxInst> findInstWithList(InstQueryCondition condition) {
        if (condition == null) {
            return null;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder jpql = new StringBuilder("from DyxInst where 1 = 1");

        if (StringUtils.isNotBlank(condition.getInstId())) {
            jpql.append(" and instId = :instId");
            params.put("instId", condition.getInstId());
        }

        return super.findByJPQL(jpql.toString(), params);
    }

    /**
     * 根据分期业务名称查询符合条件的分期业务数量
     * 
     * @param instId
     * @param instName
     * @return
     */
    public int countInst(String instId, String instName) {
        Map<String, Object> params = new HashMap<String, Object>();

        if (StringUtils.isBlank(instName)) {
            return 0;
        } else {
            params.put("instName", instName);
        }

        StringBuilder jpql = new StringBuilder("select count(*) from DyxInst where instName = :instName");
        if (StringUtils.isNotBlank(instId)) {
            jpql.append(" and instId != :instId");
            params.put("instId", instId);
        }

        return super.countByJPQL(jpql.toString(), params);
    }

    /**
     * 根据分期业务规则主键查询对应的分期业务包含规则数量
     * 
     * @param ruleId
     * @return
     */
    public int countInstRule(String ruleId) {
        return super.countByJPQL("select count(*) from DyxInstRule a where exists (select 1 from DyxInstRule where instId = a.instId and ruleId = ?1)", ruleId);
    }
}
