package sh.ricky.dyx.aspect;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import sh.ricky.core.aspect.BaseAspect;
import sh.ricky.core.constant.ConfigConstants;
import sh.ricky.core.constant.GlobalConstants;
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

        // 认证失败则返回消息
        Map<String, Object> error = new HashMap<String, Object>();
        error.put("error", "认证失败");

        // 获取请求对象
        HttpServletRequest request = null;
        Object[] args = point.getArgs();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
                continue;
            }
        }

        if (request == null) {
            return error;
        }

        // 门店用户登录
        String userName = request.getParameter("userName");
        userName = StringUtils.isBlank(userName) ? (String) request.getAttribute("userName") : userName;
        // 门店用户密码
        String userPwd = request.getParameter("userPwd");
        userPwd = StringUtils.isBlank(userPwd) ? (String) request.getAttribute("userPwd") : userPwd;
        // 门店用户对象
        Map<String, Object> user = userService.getShopUser(userName, userPwd);
        // 将对象放入请求
        request.setAttribute("shopUser", user);

        return user == null ? error : point.proceed();
    }

    /**
     * 设置返回状态
     * 
     * @param arg
     * @throws Throwable
     */
    @SuppressWarnings("unchecked")
    @AfterReturning(pointcut = "mob()", returning = "arg")
    public void setReturnStat(Object arg) throws Throwable {
        Map<String, Object> map = null;

        if (arg instanceof Map) {
            map = (Map<String, Object>) arg;
        } else if (arg instanceof ResponseEntity) {
            map = ((ResponseEntity<Map<String, Object>>) arg).getBody();
        }

        if (map != null) {
            if (map.containsKey("error")) {
                map.put("status", GlobalConstants.NO_VALUE);
            }
            if (!map.containsKey("status")) {
                map.put("status", GlobalConstants.YES_VALUE);
            }
        }
    }
}
