package sh.ricky.dyx.aspect;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sh.ricky.core.aspect.BaseAspect;
import sh.ricky.core.constant.ConfigConstants;
import sh.ricky.core.constant.GlobalConstants;
import sh.ricky.dyx.bean.UserInfo;
import sh.ricky.dyx.service.UserService;

@Repository
@Aspect
public class MobAspect extends BaseAspect {

    @Autowired
    private UserService userService;

    @Pointcut("execution (* sh.ricky..webservice.*WebService.*(..)) || execution (* sh.ricky.dyx.controller.OrderController.uploadMob(..))")
    public void mob() {
    }

    @Around("mob()")
    public Object authentication(ProceedingJoinPoint point) throws Throwable {
        // 仅当debug模式打开时不进行认证校验
        String debug = ConfigConstants.getInstance().get("debug.on");
        if (GlobalConstants.YES_VALUE.equals(debug)) {
            return point.proceed();
        }

        Map<String, Object> error = new HashMap<String, Object>();
        error.put("error", "认证失败");

        HttpServletRequest request = null;
        Object[] args = point.getArgs();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
                continue;
            }
        }

        if (request == null) {
            return null;
        }

        String userName = request.getParameter("userName");
        userName = StringUtils.isBlank(userName) ? (String) request.getAttribute("userName") : userName;

        String userPwd = request.getParameter("userPwd");
        userPwd = StringUtils.isBlank(userPwd) ? (String) request.getAttribute("userPwd") : userPwd;

        UserInfo user = userService.getUserInfo(userName, userPwd);

        return user == null ? error : point.proceed();
    }
}
