package com.zhouwenqi.apihub.interceptor;

import com.zhouwenqi.apihub.core.entity.AppConfig;
import com.zhouwenqi.apihub.core.model.response.ResponseModel;
import com.zhouwenqi.apihub.core.model.response.ResponseResult;
import com.zhouwenqi.apihub.service.AppConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器 - 头部输出
 * Created by zhouwenqi on 2018/1/24.
 */
public class OriginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private AppConfigService appConfigService;
    // 允许跨域配置
    @Value("${app.response.access-control-allow-origin}")
    private String accessControlAllowOrigin;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, X-HTTP-Method-Override, Content-Type, Accept, apihub-token, project-id");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin",accessControlAllowOrigin);
        String method = request.getMethod().toLowerCase();
        if("options".equals(method)){
            return true;
        }

        // 检查项目是否允许访问
        AppConfig appConfig = appConfigService.findCurrentSystemConfig();
        if(null!=appConfig && !appConfig.getIsAllowRequest()){
            ResponseModel responseModel = ResponseModel.getAccessRequest();
            ResponseResult.output(responseModel,response);
            return false;
        }
        return true;
    }
}
