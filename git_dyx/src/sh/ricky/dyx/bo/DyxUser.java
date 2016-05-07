package sh.ricky.dyx.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户表
 * 
 * @author SHI
 */
@Entity
@Table(name = "DYX_USER")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true, selectBeforeUpdate = true)
public class DyxUser implements java.io.Serializable {

    // Fields

    private static final long serialVersionUID = 3394908575322159702L;

    // 用户ID
    private String userId;

    // 用户姓名
    private String userName;

    // 用户角色
    private String userRole;

    // 用户密码
    private String userPwd;

    // 用户初始密码
    private String initPwd;

    // 昵称
    private String nickName;

    // 电子邮箱
    private String email;

    // 联系电话
    private String tel;

    // 是否有效
    private String valid;

    // Property accessors

    @Id
    @Column(name = "USER_ID", unique = true, nullable = false, insertable = true, updatable = true, length = 32)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "USER_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "USER_ROLE", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Column(name = "USER_PWD", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    @Column(name = "INIT_PWD", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
    public String getInitPwd() {
        return initPwd;
    }

    public void setInitPwd(String initPwd) {
        this.initPwd = initPwd;
    }

    @Column(name = "NICK_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Column(name = "EMAIL", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "TEL", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Column(name = "VALID", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }
}
