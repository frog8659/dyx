package sh.ricky.core.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import sh.ricky.core.constant.ConfigConstants;
import sh.ricky.core.constant.DicConstants;
import sh.ricky.core.constant.GlobalConstants;
import sh.ricky.dyx.bean.CfgFlow;
import sh.ricky.dyx.bean.CfgMenu;
import sh.ricky.dyx.bean.UserInfo;
import sh.ricky.dyx.constant.UserConstants;

@SuppressWarnings("unused")
public class DicDAO extends BaseJdbcDAO {
    private static final String JNDI_BUSINESS = ConfigConstants.getInstance().get("jdbc.business");

    private static final String JNDI_EASYBIKE = ConfigConstants.getInstance().get("jdbc.easybike");

    /**
     * 根据查询结果集组装字典
     * 
     * @param result
     * @return
     */
    private Map<String, String> getDic(List<Map<String, Object>> result) {
        Map<String, String> map = new LinkedHashMap<String, String>();

        if (result != null) {
            for (Map<String, Object> r : result) {
                String key = StringUtils.trimToEmpty(String.valueOf(r.get("k")));
                String value = StringUtils.trimToEmpty(String.valueOf(r.get("v")));
                map.put(key, value);
            }
        }

        return map;
    }

    /**
     * 根据查询结果集组装字典（两级）
     * 
     * @param result
     * @return
     */
    private Map<String, Map<String, String>> getAdvancedDic(List<Map<String, Object>> result) {
        Map<String, Map<String, String>> map = new LinkedHashMap<String, Map<String, String>>();

        if (result != null) {
            for (Map<String, Object> r : result) {
                String key1 = StringUtils.trimToEmpty(String.valueOf(r.get("k1")));
                String key2 = StringUtils.trimToEmpty(String.valueOf(r.get("k2")));
                String value = StringUtils.trimToEmpty(String.valueOf(r.get("v")));

                if (!map.containsKey(key1)) {
                    map.put(key1, new LinkedHashMap<String, String>());
                }

                Map<String, String> subMap = map.get(key1);
                subMap.put(key2, value);
            }
        }

        return map;
    }

    /**
     * 获取常规字典
     * 
     * @param dataSourceName 数据源
     * @param tableName 表名
     * @param keyColumn 索引字段名
     * @param valueColumn 字典值字段名
     * @param validityColumn 有效性字段名
     * @param orderbyColumns 排序
     * @return
     */
    private Map<String, String> getDic(String dataSourceName, String tableName, String keyColumn, String valueColumn, String validityColumn,
            String orderbyColumns) {
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(keyColumn) || StringUtils.isBlank(valueColumn)) {
            return new LinkedHashMap<String, String>();
        }

        StringBuffer sql = new StringBuffer("select ");
        sql.append(keyColumn).append(" as k, ").append(valueColumn).append(" as v from ").append(tableName);

        if (StringUtils.isNotBlank(validityColumn)) {
            sql.append(" where ").append(validityColumn).append(" = '").append(GlobalConstants.YES_VALUE).append("'");
        }

        if (StringUtils.isNotBlank(orderbyColumns)) {
            sql.append(" order by ").append(orderbyColumns);
        }

        return this.getDic(super.find(dataSourceName, sql.toString()));
    }

    /**
     * 获取操作菜单配置
     * 
     * @return
     */
    public Map<String, CfgMenu> getDicMenu() {
        Map<String, CfgMenu> map = new LinkedHashMap<String, CfgMenu>();

        String sql = "select * from CFG_MENU where VALID = '" + GlobalConstants.YES_VALUE + "' order by DISP_ORD, MENU_ID";
        List<CfgMenu> list = super.find(JNDI_BUSINESS, sql, new BeanPropertyRowMapper<CfgMenu>(CfgMenu.class));

        if (list != null) {
            for (CfgMenu menu : list) {
                map.put(menu.getMenuId(), menu);
            }
        }

        return map;
    }

    /**
     * 角色字典
     * 
     * @return
     */
    public Map<String, String> getDicRole() {
        return this.getDic(JNDI_BUSINESS, "DIC_ROLE", "ROLE_ID", "ROLE_NAME", null, null);
    }

    /**
     * 获取角色权限映射配置
     * 
     * @return
     */
    public Map<String, List<String>> getDicRoleAuth() {
        Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();

        List<Map<String, Object>> result = super.find(JNDI_BUSINESS, "select CFG_ID, ROLE_ID as k, AUTH_ID as v from CFG_ROLE_AUTH");

        if (result != null) {
            for (Map<String, Object> r : result) {
                String key = StringUtils.trimToEmpty(String.valueOf(r.get("k")));
                String value = StringUtils.trimToEmpty(String.valueOf(r.get("v")));

                if (!map.containsKey(key)) {
                    map.put(key, new ArrayList<String>());
                }
                map.get(key).add(value);
            }
        }

        return map;
    }

    /**
     * 获取流程配置
     * 
     * @return
     */
    public Map<String, List<CfgFlow>> getDicFlowCfg() {
        Map<String, List<CfgFlow>> map = new LinkedHashMap<String, List<CfgFlow>>();

        String sql = "select * from CFG_FLOW where VALID = '" + GlobalConstants.YES_VALUE + "' order by FLOW_ID, FLOW_SEG, DISP_ORD";
        List<CfgFlow> list = super.find(JNDI_BUSINESS, sql, new BeanPropertyRowMapper<CfgFlow>(CfgFlow.class));

        if (list != null) {
            for (CfgFlow flow : list) {
                if (!map.containsKey(flow.getFlowId())) {
                    map.put(flow.getFlowId(), new ArrayList<CfgFlow>());
                }
                map.get(flow.getFlowId()).add(flow);
            }
        }

        return map;
    }

    /**
     * 流程操作字典
     * 
     * @return
     */
    public Map<String, String> getDicActn() {
        return this.getDic(JNDI_BUSINESS, "DIC_ACTN", "ACTN_ID", "ACTN_NAME", null, "DISP_ORD");
    }

    /**
     * 流程环节字典
     * 
     * @return
     */
    public Map<String, String> getDicSeg() {
        return this.getDic(JNDI_BUSINESS, "DIC_SEG", "SEG_ID", "SEG_NAME", null, null);
    }

    /**
     * 流程环节状态字典
     * 
     * @return
     */
    public Map<String, String> getDicSegStat() {
        return this.getDic(JNDI_BUSINESS, "DIC_SEG", "SEG_ID", "SEG_STAT", null, null);
    }

    /**
     * 流程环节分类字典
     * 
     * @return
     */
    public Map<String, String> getDicSegSort() {
        return this.getDic(JNDI_BUSINESS, "DIC_SEG", "SEG_ID", "SEG_SORT", null, null);
    }

    /**
     * 分期名称字典
     * 
     * @return
     */
    public Map<String, String> getDicInstName() {
        return this.getDic(JNDI_BUSINESS, "DYX_INST", "INST_ID", "INST_NAME", null, "INST_NAME");
    }

    /**
     * 操作员字典
     * 
     * @return
     */
    public Map<String, String> getDicOpUser() {
        StringBuilder sql = new StringBuilder("select * from account_company_detail a, account b");
        sql.append(" where a.account_id = b.id and account_category in (1, 3, 4, 6) and valid = 1");
        sql.append(" and exists (select 1 from role_account where account_id = a.account_id and role_id = 1)");

        List<Map<String, Object>> result = super.find(JNDI_EASYBIKE, sql.toString());
        List<UserInfo> list = new ArrayList<UserInfo>();
        if (result != null) {
            for (Map<String, Object> user : result) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(((Long) user.get("id")).toString());
                userInfo.setUserName((String) user.get("username"));
                userInfo.setNickName((String) user.get("name"));
                userInfo.setUserRole(((Integer) user.get("account_category")).toString());
                userInfo.setAuthList(DicConstants.getInstance().getDicRoleAuth().get(userInfo.getUserRole()));
                list.add(userInfo);
            }
        }

        Map<String, String> map = new LinkedHashMap<String, String>();
        if (list != null) {
            for (UserInfo user : list) {
                map.put(user.getUserId(), user.getNickName());
            }
        }

        return map;
    }
}
