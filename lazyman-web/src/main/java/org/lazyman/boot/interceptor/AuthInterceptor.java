package org.lazyman.boot.interceptor;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.boot.auth.service.IAuthService;
import org.lazyman.common.constant.StringPool;
import org.lazyman.common.util.WebUtils;
import org.lazyman.boot.permission.RequiresPermissions;
import org.lazyman.boot.permission.RequiresRoles;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(-1)
@Slf4j
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private IAuthService iAuthService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //options请求跨域
        if (StringPool.OPTIONS.equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        //认证检查
        boolean result = iAuthService.preAuth(WebUtils.getAccessToken());
        if (!result) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return result;
        }
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //权限检查
            RequiresRoles requiresRoles = handlerMethod.getMethodAnnotation(RequiresRoles.class);
            if (ObjectUtil.isNotEmpty(requiresRoles)) {
                result = iAuthService.checkRole(requiresRoles.value(), requiresRoles.logical());
                if (!result) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return result;
                }
            }
            RequiresPermissions requiresPermissions = handlerMethod.getMethodAnnotation(RequiresPermissions.class);
            if (ObjectUtil.isNotEmpty(requiresPermissions)) {
                result = iAuthService.checkPermission(requiresPermissions.value(), requiresPermissions.logical());
                if (!result) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return result;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        iAuthService.afterAuth();
    }
}
