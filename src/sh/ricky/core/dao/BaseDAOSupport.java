package sh.ricky.core.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.query.ParamLocationRecognizer;
import org.hibernate.util.StringHelper;

import sh.ricky.core.bean.Page;
import sh.ricky.core.exception.DAOException;

public class BaseDAOSupport {

    /** QL选项：JPA语法 */
    private final int QL_JPA = 1;

    /** QL选项：原生SQL */
    private final int QL_NATIVE = 2;

    /**
     * 根据QL选项适配JPA语法或原生SQL，返回Query对象
     * 
     * @param em
     * @param ql
     * @param opt
     * @return
     */
    private Query createAdaptor(EntityManager em, String ql, int opt) {
        switch (opt) {
        case QL_JPA:
            return em.createQuery(ql);
        case QL_NATIVE:
            return em.createNativeQuery(ql);
        default:
            return null;
        }
    }

    /**
     * 将QL参数统一转为位置参数，返回Query对象
     * 
     * @param em
     * @param ql
     * @param opt
     * @param params 参数（可选）
     * @return
     */
    private Query create(EntityManager em, String ql, int opt, Object... params) {
        Query query = null;
        ParamLocationRecognizer plr = ParamLocationRecognizer.parseLocations(ql);

        if (plr.getNamedParameterDescriptionMap().isEmpty()) {
            // 命名参数集合为空，认定当前QL无参数，或以?为参数占位符
            query = this.createAdaptor(em, ql, opt);
            // 根据参数位置进行参数设值
            if (params != null && !plr.getOrdinalParameterLocationList().isEmpty()) {
                for (int i = 1; i <= params.length; i++) {
                    query.setParameter(i, params[i - 1]);
                }
            }
        } else if (params != null) {
            // 命名参数集合不为空，认定当前QL使用命名参数
            String[] tokens = StringHelper.split(" \n\r\f\t(),", ql, true);
            // 将QL中的命名参数统一替换为jpa风格占位符
            int p = 1;
            if (params.length == 1 && params[0] instanceof Map) {
                // 传入参数为集合类型
                Map<?, ?> parametersMap = (Map<?, ?>) params[0];
                // 重新整理传入参数
                List<Object> list = new ArrayList<Object>();
                for (int i = 0; i < tokens.length; i++) {
                    Object param = null;
                    if (tokens[i].startsWith(":")) {
                        param = parametersMap.get(tokens[i].substring(1));
                    } else if (tokens[i].startsWith("?")) {
                        param = parametersMap.get(new Integer(tokens[i].substring(1)));
                    }
                    if (param != null) {
                        list.add(param);
                    }
                    if (tokens[i].startsWith(":") || tokens[i].startsWith("?")) {
                        tokens[i] = "?" + p++;
                    }
                }
                // 通过新QL生成Query对象
                query = this.createAdaptor(em, StringHelper.join("", tokens), opt);
                // 根据新参数进行参数设值
                for (int i = 1; i <= list.size(); i++) {
                    query.setParameter(i, list.get(i - 1));
                }
            } else {
                // 传入参数为非集合类型
                for (int i = 0; i < tokens.length; i++) {
                    if (tokens[i].startsWith(":") || tokens[i].startsWith("?")) {
                        tokens[i] = "?" + p++;
                    }
                }
                // 通过新QL生成Query对象
                query = this.createAdaptor(em, StringHelper.join("", tokens), opt);
                // 根据参数位置进行参数设值
                for (int i = 1; i <= params.length; i++) {
                    query.setParameter(i, params[i - 1]);
                }
            }
        }

        // 返回Query对象
        return query;
    }

    /**
     * 根据QL语句（使用聚合函数count）获取记录数
     * 
     * @param em
     * @param ql
     * @param opt
     * @param params 参数（可选）
     * @return
     */
    private int countByQL(EntityManager em, String ql, int opt, Object... params) {
        try {
            Query query = this.create(em, ql, opt, params);
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (HibernateException ex) {
            throw new DAOException(ex);
        }
    }

    /**
     * 根据SQL语句（使用聚合函数count）获取记录数
     * 
     * @param em
     * @param ql
     * @param params 参数（可选）
     * @return
     */
    protected int countBySQL(EntityManager em, String ql, Object... params) {
        return this.countByQL(em, ql, this.QL_NATIVE, params);
    }

    /**
     * 根据JPQL语句（使用聚合函数count）获取记录数
     * 
     * @param em
     * @param ql
     * @param params 参数（可选）
     * @return
     */
    protected int countByJPQL(EntityManager em, String ql, Object... params) {
        return this.countByQL(em, ql, this.QL_JPA, params);
    }

    /**
     * 根据QL语句查询获取单个对象
     * 
     * @param em
     * @param ql
     * @param opt
     * @param params 参数（可选）
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> T findUniqueByQL(EntityManager em, String ql, int opt, Object... params) {
        try {
            Query query = this.create(em, ql, opt, params);

            // 设置查询结果的开始记录数（从0开始计数）
            query.setFirstResult(0);

            // 设置查询结果的结束记录数（获取单个对象）
            query.setMaxResults(1);

            List<T> list = (List<T>) query.getResultList();
            return list == null ? null : list.isEmpty() ? null : list.get(0);
        } catch (HibernateException ex) {
            throw new DAOException(ex);
        }
    }

    /**
     * 根据SQL语句查询获取单个对象
     * 
     * @param em
     * @param ql
     * @param params 参数（可选）
     * @return
     */
    protected <T> T findUniqueBySQL(EntityManager em, String ql, Object... params) {
        return this.findUniqueByQL(em, ql, this.QL_NATIVE, params);
    }

    /**
     * 根据JPQL语句查询获取单个对象
     * 
     * @param em
     * @param ql
     * @param params 参数（可选）
     * @return
     */
    protected <T> T findUniqueByJPQL(EntityManager em, String ql, Object... params) {
        return this.findUniqueByQL(em, ql, this.QL_JPA, params);
    }

    /**
     * 根据QL语句查询
     * 
     * @param em
     * @param ql
     * @param opt
     * @param params 参数（可选）
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> findByQL(EntityManager em, String ql, int opt, Object... params) {
        try {
            Query query = this.create(em, ql, opt, params);
            return (List<T>) query.getResultList();
        } catch (HibernateException ex) {
            throw new DAOException(ex);
        }
    }

    /**
     * 根据SQL语句查询
     * 
     * @param em
     * @param ql
     * @param params 参数（可选）
     * @return
     */
    protected <T> List<T> findBySQL(EntityManager em, String ql, Object... params) {
        return this.findByQL(em, ql, this.QL_NATIVE, params);
    }

    /**
     * 根据JPQL语句查询
     * 
     * @param em
     * @param ql
     * @param params 参数（可选）
     * @return
     */
    protected <T> List<T> findByJPQL(EntityManager em, String ql, Object... params) {
        return this.findByQL(em, ql, this.QL_JPA, params);
    }

    /**
     * 根据QL语句分页查询，返回页对象
     * 
     * @param em
     * @param ql
     * @param opt
     * @param pn 当前页码
     * @param ps 每页记录数
     * @param params 参数（可选）
     * @return
     */
    private Page findByQLWithPage(EntityManager em, String ql, int opt, int pn, int ps, Object... params) {
        if (StringUtils.isBlank(ql)) {
            return Page.EMPTY_PAGE;
        }

        try {
            // 根据ql获取count语句
            String countQL = "select count(*) ";
            if (ql.toLowerCase().indexOf("order by") == -1) {
                countQL += ql.substring(ql.toLowerCase().indexOf("from"));
            } else {
                countQL += ql.substring(ql.toLowerCase().indexOf("from"), ql.toLowerCase().indexOf("order by"));
            }

            int count = this.countByQL(em, countQL, opt, params);

            if (pn < 1) {
                pn = 1;
            }

            if (ps < 1) {
                ps = Page.DEFAULT_PAGE_SIZE;
            }

            Query query = this.create(em, ql, opt, params);

            // 设置查询结果的结束记录数
            int maxResults = ps;
            query.setMaxResults(maxResults);

            // 设置查询结果的开始记录数（从0开始计数）
            int firstResult = (pn - 1) * ps;
            query.setFirstResult(firstResult);

            return new Page(query.getResultList(), count, pn, ps);
        } catch (HibernateException ex) {
            throw new DAOException(ex);
        }
    }

    /**
     * 根据SQL语句分页查询，返回页对象
     * 
     * @param em
     * @param ql
     * @param pn 当前页码
     * @param ps 每页记录数
     * @param params 参数（可选）
     * @return
     */
    protected Page findBySQLWithPage(EntityManager em, String ql, int pn, int ps, Object... params) {
        return this.findByQLWithPage(em, ql, this.QL_NATIVE, pn, ps, params);
    }

    /**
     * 根据JPQL语句分页查询，返回页对象
     * 
     * @param em
     * @param ql
     * @param pn 当前页码
     * @param ps 每页记录数
     * @param params 参数（可选）
     * @return
     */
    protected Page findByJPQLWithPage(EntityManager em, String ql, int pn, int ps, Object... params) {
        return this.findByQLWithPage(em, ql, this.QL_JPA, pn, ps, params);
    }
}
