package com.goodluncky.interceptor;

import com.goodluncky.util.HttpUtil;
import com.goodluncky.util.SSOClientUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

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
        //2.判断token;
        String token = request.getParameter("token");
        if (!StringUtils.isEmpty(token)){
            System.out.println("检测到服务器的token信息");
            //防止伪造，拿到服务器去验证
            //服务器的地址
            String httpURL = SSOClientUtil.SERVER_URL_PREFIX + "/verify";
            //需要验证的参数
            HashMap<String,String> params = new HashMap<String, String>();
            params.put("token",token);
            params.put("clientUrl",SSOClientUtil.getClientLogOutUrl());
            params.put("jsessionId",session.getId());
            try {
                String isVerify = HttpUtil.sendHttpRequest(httpURL, params);
                if ("true".equals(isVerify)){
                    System.out.println("服务器端校验通过");
                    session.setAttribute("isLogin",true);

                }
            }catch (Exception e){
                System.out.println("服务器验证异常");
                e.printStackTrace();
            }
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
