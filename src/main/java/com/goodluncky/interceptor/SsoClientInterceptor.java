package com.goodluncky.interceptor;

import com.goodluncky.util.SSOClientUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SsoClientInterceptor implements HandlerInterceptor {
    //true 放行
    //false 被拦截
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.判断是否存在会话 isLogin = true
        HttpSession session = request.getSession();
        Boolean isLogin = (Boolean) session.getAttribute("isLogin");
        if (isLogin != null && isLogin){ //登录了，可以放行
            return true;
        }
        //没有会话，跳转到统一认证中心，检测系统是否登录！
        SSOClientUtil.redirectToSSOURL(request,response);
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
