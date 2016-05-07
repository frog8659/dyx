package sh.ricky.dyx.aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Repository;

import sh.ricky.core.annotation.Outer;
import sh.ricky.core.aspect.BaseAspect;
import sh.ricky.core.constant.DicConstants;
import sh.ricky.core.constant.GlobalConstants;
import sh.ricky.core.util.FormUtil;
import sh.ricky.core.util.ext.WebContext;
import sh.ricky.dyx.annotation.Permission;
import sh.ricky.dyx.bean.CfgMenu;
import sh.ricky.dyx.bean.UserInfo;

@Repository
@Aspect
public class PermissionAspect extends BaseAspect {

    private final Logger logger = Logger.getLogger(PermissionAspect.class);

    @Pointcut("execution (* sh.ricky..controller.*Controller.*(..))")
    public void controller() {
    }

    @Pointcut("@annotation(sh.ricky.dyx.annotation.Permission)")
    public void permission() {
    }

    /**
     * 根据权限控制链接访问
     * 
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("permission()")
    public Object access(ProceedingJoinPoint point) throws Throwable {
        Class<?> targetClass = point.getTarget().getClass();

        // 获取目标方法
        Method targetMethod = this.getMethod(point);

        if (targetMethod == null) {
            logger.error("找不到相匹配的函数");
            return GlobalConstants.FORWARD_ERROR;
        }

        // session中不存在用户信息，则登录超时
        HttpSession session = WebContext.getSession();
        UserInfo user = (UserInfo) session.getAttribute(GlobalConstants.SESSION_USER);
        if (user == null) {
            logger.error("Session中的用户信息不存在");

            // 会话超时，页面跳转
            return forward(targetMethod, GlobalConstants.FORWARD_TIMEOUT);
        }

        List<String> authList = user.getAuthList();

        if (authList != null && !authList.isEmpty()) {
            if (targetClass.isAnnotationPresent(Permission.class)) {
                boolean permit = false;
                for (String authId : targetClass.getAnnotation(Permission.class).authId()) {
                    if (authList.contains(authId)) {
                        permit = true;
                        break;
                    }
                }
                if (!permit) {
                    // 无权限访问，页面跳转
                    return forward(targetMethod, GlobalConstants.FORWARD_ACCESS_DENIED);
                }
            }

            if (targetMethod.isAnnotationPresent(Permission.class)) {
                boolean permit = false;
                for (String authId : targetMethod.getAnnotation(Permission.class).authId()) {
                    if (authList.contains(authId)) {
                        permit = true;
                        break;
                    }
                }
                if (!permit) {
                    // 无权限访问，页面跳转
                    return forward(targetMethod, GlobalConstants.FORWARD_ACCESS_DENIED);
                }
            }

            return point.proceed();
        }

        // 无权限访问，页面跳转
        return forward(targetMethod, GlobalConstants.FORWARD_ACCESS_DENIED);
    }

    /**
     * 根据权限读取菜单列表
     * 
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("controller()")
    public Object menu(ProceedingJoinPoint point) throws Throwable {
        Class<?> targetClass = point.getTarget().getClass();

        // 对以@Outer标注的类不进行session校验
        if (targetClass.isAnnotationPresent(Outer.class)) {
            return point.proceed();
        }

        // 获取目标方法
        Method targetMethod = this.getMethod(point);

        if (targetMethod == null) {
            logger.error("找不到相匹配的函数");
            return GlobalConstants.FORWARD_ERROR;
        }

        // 对以@Outer标注的方法不进行session校验
        if (targetMethod.isAnnotationPresent(Outer.class)) {
            return point.proceed();
        }

        // session中不存在用户信息，则登录超时
        HttpSession session = WebContext.getSession();
        UserInfo user = (UserInfo) session.getAttribute(GlobalConstants.SESSION_USER);
        if (user == null) {
            logger.error("Session中的用户信息不存在");

            // 会话超时，页面跳转
            return forward(targetMethod, GlobalConstants.FORWARD_TIMEOUT);
        }

        // 根据会话用户权限，读取菜单列表
        Map<String, CfgMenu> dicMenu = DicConstants.getInstance().getDicMenu();
        Map<String, CfgMenu> authMenu = new LinkedHashMap<String, CfgMenu>();
        List<CfgMenu> menuList = new ArrayList<CfgMenu>();
        List<String> authList = user.getAuthList();

        if (authList != null && !authList.isEmpty()) {
            for (CfgMenu menu : dicMenu.values()) {
                if (authList.contains(menu.getAuthId())) {
                    CfgMenu dest = new CfgMenu();
                    FormUtil.copyProperties(dest, menu);
                    authMenu.put(dest.getMenuId(), dest);
                }
            }
        }

        for (CfgMenu menu : authMenu.values()) {
            if (StringUtils.isBlank(menu.getParentId())) {
                this.findSubMenus(menu, authMenu);
                menuList.add(menu);
            }
        }

        // 将用户菜单列表放入请求对象
        HttpServletRequest request = WebContext.getRequest();
        request.setAttribute("menuList", menuList);

        return point.proceed();
    }

    private void findSubMenus(CfgMenu menu, Map<String, CfgMenu> dicMenu) {
        for (CfgMenu m : dicMenu.values()) {
            if (StringUtils.equals(menu.getMenuId(), m.getParentId())) {
                menu.addToSubMenuList(m);
                findSubMenus(m, dicMenu);
            }
        }
    }
}
