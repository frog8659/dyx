package sh.ricky.dyx.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import sh.ricky.core.constant.DicConstants;
import sh.ricky.core.constant.GlobalConstants;
import sh.ricky.core.util.MD5Util;
import sh.ricky.core.util.ext.WebContext;
import sh.ricky.dyx.bean.UserInfo;
import sh.ricky.dyx.bean.UserQueryCondition;
import sh.ricky.dyx.dao.UserDAO;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    /**
     * 用户登录后门
     * 
     * @param user
     * @return
     */
    public UserInfo backdoor(UserInfo user) {
        try {
            // 设置后门用户登录信息
            user.setUserId(GlobalConstants.BACKDOOR_ID);
            // 设置用户显示名
            user.setNickName(StringUtils.trimToEmpty(user.getUserName()));
            // 设置用户权限
            user.setAuthList(DicConstants.getInstance().getDicRoleAuth().get(user.getUserRole()));

            // 返回后门用户信息
            return user;
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * 根据登录信息获取用户会话信息
     * 
     * @param userName
     * @param userPwd
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public UserInfo getUserInfo(String userName, String userPwd) {
        try {
            // 登录用户名密码不能为空
            if (StringUtils.isBlank(userName) || StringUtils.isBlank(userPwd)) {
                return null;
            }

            // 根据用户名、密码，获取登录用户对象
            UserQueryCondition condition = new UserQueryCondition();
            condition.setUserName(userName);
            condition.setUserPwd(MD5Util.getMD5(userPwd).toLowerCase());
            Map<String, Object> user = userDAO.getCompUser(condition);
            if (user == null || user.isEmpty()) {
                return null;
            }

            // 新建用户信息对象，用于保存会话信息
            UserInfo userInfo = new UserInfo();

            // 设置用户ID
            userInfo.setUserId(((Long) user.get("id")).toString());
            // 设置用户登录名
            userInfo.setUserName((String) user.get("username"));
            // 设置用户显示名
            userInfo.setNickName((String) user.get("name"));
            // 设置用户角色
            userInfo.setUserRole(((Integer) user.get("account_category")).toString());
            // 设置用户权限
            userInfo.setAuthList(DicConstants.getInstance().getDicRoleAuth().get(userInfo.getUserRole()));

            // 返回用户信息对象
            return userInfo;
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * 根据用户名密码获取门店用户对象
     * 
     * @param userName
     * @param userPwd
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Map<String, Object> getShopUser(String userName, String userPwd) {
        UserQueryCondition condition = new UserQueryCondition();
        condition.setUserName(userName);
        condition.setUserPwd(MD5Util.getMD5(userPwd).toLowerCase());
        return userDAO.getShopUser(condition);
    }

    /**
     * 密码修改
     * 
     * @param oriPwd 原密码
     * @param newPwd 新密码
     * @param captcha 验证码
     * @return 返回结果信息
     */
    @Transactional
    public String changePwd(String oriPwd, String newPwd, String captcha) {
        HttpSession session = WebContext.getSession();

        String capt = (String) session.getAttribute(GlobalConstants.SESSION_CAPTCHA_IMG);

        if (!StringUtils.equalsIgnoreCase(captcha, capt)) {
            return "验证码不正确！";
        }

        // 会话用户对象
        UserInfo user = (UserInfo) session.getAttribute(GlobalConstants.SESSION_USER);

        // 确认用户原密码信息
        UserInfo userInfo = this.getUserInfo(user.getUserName(), oriPwd);

        if (userInfo == null) {
            return "原密码不正确！";
        }

        try {
            if (userDAO.updatePwd(Integer.parseInt(user.getUserId()), MD5Util.getMD5(newPwd).toLowerCase()) == 0) {
                return "密码修改失败！";
            }
        } catch (Throwable t) {
            return "密码修改失败！";
        }

        return "";
    }
}
