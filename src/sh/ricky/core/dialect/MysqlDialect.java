package sh.ricky.core.dialect;

import java.sql.Types;

import org.hibernate.Hibernate;

/**
 * 自定义mysql方言，添加hibernate3.0未支持的特性
 */
public class MysqlDialect extends org.hibernate.dialect.MySQLDialect {
    public MysqlDialect() {
        super();
        // 添加lvarchar支持
        registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
        // 解决jpa利用NativeQuery查询时将char($l>1)认为character的bug
        registerHibernateType(Types.CHAR, Hibernate.STRING.getName());
    }
}
