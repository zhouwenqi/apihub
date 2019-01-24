package com.zhouwenqi.apihub.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器 - 头部输出
 * Created by zhouwenqi on 2018/1/24.
 */
public class OriginInterceptor extends HandlerInterceptorAdapter {
    @Value("${app.response.access-control-allow-origin}")
    // 允许跨域配置
    private String accessControlAllowOrigin;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, X-HTTP-Method-Override, Content-Type, Accept, Apihub-Token");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin",accessControlAllowOrigin);
        return true;
    }
}
