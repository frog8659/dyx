package sh.ricky.dyx.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import sh.ricky.core.constant.ConfigConstants;
import sh.ricky.core.dao.BaseJdbcDAO;
import sh.ricky.dyx.bean.UserQueryCondition;

@Repository
public class UserDAO extends BaseJdbcDAO {
    private static final String JNDI_EASYBIKE = ConfigConstants.getInstance().get("jdbc.easybike");

    /**
     * 根据条件获取有效公司用户对象
     * 
     * @param condition
     * @return
     */
    public Map<String, Object> getCompUser(UserQueryCondition condition) {
        Map<String, Object> params = new HashMap<String, Object>();

        StringBuilder sql = new StringBuilder("select * from account_company_detail a, account b");
        sql.append(" where a.account_id = b.id and account_category in (1, 3, 4, 6) and valid = 1");
        sql.append(" and exists (select 1 from role_account where account_id = a.account_id and role_id = 1)");

        if (StringUtils.isNotBlank(condition.getUserId())) {
            sql.append(" and a.account_id = :userId");
            params.put("userId", Integer.parseInt(condition.getUserId()));
        }

        if (StringUtils.isNotBlank(condition.getUserName())) {
            sql.append(" and b.username = :userName");
            params.put("userName", condition.getUserName());
        }

        if (StringUtils.isNotBlank(condition.getUserPwd())) {
            sql.append(" and b.password = :userPwd");
            params.put("userPwd", condition.getUserPwd());
        }

        List<Map<String, Object>> result = super.find(JNDI_EASYBIKE, sql.toString(), params);
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }

        return null;
    }

    /**
     * 更新用户密码
     * 
     * @param id
     * @param pwd
     * @return
     */
    public int updatePwd(Integer id, String pwd) {
        String sql = "update account set password = ? where id = ?";
        return super.update(JNDI_EASYBIKE, sql, pwd, id);
    }
}
