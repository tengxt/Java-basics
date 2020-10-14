package tengxt.mvc.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import tengxt.constant.CrowdConstant;
import tengxt.entity.Admin;
import tengxt.exception.AccessForbiddenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取 Session 对象
        HttpSession session = request.getSession();

        // 2. 从 Session 域中获取Admin对象
        Admin admin = (Admin) session.getAttribute(CrowdConstant.LOGIN_ADMIN_NAME);

        // 3. 判断 admin对象是否为空
        if (null == admin) {
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDDEN);
        }
        // 4. 如果 Admin 对象不为 null，则返回true
        return true;
    }
}
