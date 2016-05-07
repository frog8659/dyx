package sh.ricky.dyx.bean;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import sh.ricky.dyx.constant.UserConstants;

/**
 * @author SHI
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 2067659332249916621L;

    private String userId; // 用户编号

    private String userName; // 用户登录名

    private String nickName; // 用户显示名

    private String userRole; // 用户角色

    private List<String> authList; // 用户权限集合

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return StringUtils.isBlank(nickName) ? userName : nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserRole() {
        if ("1,6".contains(userRole)) {
            return UserConstants.USER_ROLE_CJGLY;
        } else if ("3".contains(userRole)) {
            return UserConstants.USER_ROLE_KFZY;
        } else if ("4".contains(userRole)) {
            return UserConstants.USER_ROLE_KFZG;
        }

        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public List<String> getAuthList() {
        return authList;
    }

    public void setAuthList(List<String> authList) {
        this.authList = authList;
    }
}
