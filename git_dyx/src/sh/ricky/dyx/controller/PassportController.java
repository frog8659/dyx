package sh.ricky.dyx.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sh.ricky.core.annotation.Outer;
import sh.ricky.core.constant.ConfigConstants;
import sh.ricky.core.constant.GlobalConstants;
import sh.ricky.core.util.FormUtil;
import sh.ricky.dyx.bean.UserInfo;
import sh.ricky.dyx.bean.UserQueryCondition;
import sh.ricky.dyx.service.UserService;

/**
 * 个人账户管理
 * 
 * @author SHI
 */
@Controller
@RequestMapping("/passport")
public class PassportController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录后门
     * 
     * @param user
     * @param session
     * @return
     */
    @Outer
    @RequestMapping("/backdoor")
    public String backdoor(UserInfo user, HttpSession session) {
        // 仅当debug模式打开时进行后门登录
        String debug = ConfigConstants.getInstance().get("debug.on");
        if (GlobalConstants.YES_VALUE.equals(debug)) {
            // 后门登录
            user = userService.backdoor(user);
            if (user == null) {
                return GlobalConstants.FORWARD_TIMEOUT;
            }

            // 将后门登录用户信息放入session
            session.setAttribute(GlobalConstants.SESSION_USER, user);
        }

        return "redirect:/home";
    }

    /**
     * 登录窗口
     * 
     * @param loginInfo
     * @param session
     * @param model
     * @return
     */
    @Outer
    @RequestMapping("/login")
    public String login(UserQueryCondition loginInfo, HttpSession session, Model model) {
        if (!FormUtil.isObjectEmpty(loginInfo)) {
            // 根据用户信息登录系统
            UserInfo user = userService.getUserInfo(loginInfo.getUserName(), loginInfo.getUserPwd());

            // 返回登录结果
            if (user == null) {
                model.addAttribute("message", "用户名或密码不正确！");
            } else {
                // 将登录用户信息放入session
                session.setAttribute(GlobalConstants.SESSION_USER, user);

                loginInfo.setUserId(user.getUserId());
            }

            // 返回登录信息
            model.addAttribute("loginInfo", loginInfo);
        }

        // 跳转至登录窗口
        return "passport/login";
    }

    /**
     * 注销登录
     * 
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        // 清除session中的用户数据
        Enumeration<?> enu = session.getAttributeNames();
        while (enu.hasMoreElements()) {
            session.removeAttribute((String) enu.nextElement());
        }

        return "redirect:" + ConfigConstants.getInstance().get("logout.url");
    }

    /**
     * 密码重置
     * 
     * @return
     */
    @Outer
    @RequestMapping("/pwd/reset")
    public String resetPwd() {
        return "passport/pwd_res";
    }

    /**
     * 密码修改
     * 
     * @param oriPwd 原密码
     * @param newPwd 新密码
     * @param captcha 验证码
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/pwd/change")
    public String changePwd(String oriPwd, String newPwd, String captcha, HttpSession session, Model model) {
        if (StringUtils.isBlank(newPwd)) {
            return "passport/pwd_chg";
        }

        String msg = userService.changePwd(oriPwd, newPwd, captcha);
        if (StringUtils.isNotBlank(msg)) {
            model.addAttribute("msg", msg);
            return "passport/pwd_chg";
        }

        // 登出系统
        this.logout(session);

        // 跳转至超时页面，重新登录
        return GlobalConstants.FORWARD_TIMEOUT;
    }
}
