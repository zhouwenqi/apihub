package com.zhouwenqi.apihub.interceptor;

import com.zhouwenqi.apihub.core.entity.User;
import com.zhouwenqi.apihub.core.model.JwtUser;
import com.zhouwenqi.apihub.core.model.enums.UserStatus;
import com.zhouwenqi.apihub.core.model.response.ResponseModel;
import com.zhouwenqi.apihub.core.model.response.ResponseResult;
import com.zhouwenqi.apihub.core.utils.JwtUtils;
import com.zhouwenqi.apihub.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器 - Token校验
 * Created by zhouwenqi on 2018/9/25.
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod().toLowerCase();
        if("options".equals(method)){
            return true;
        }

        ResponseModel responseModel;
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String path = request.getServletPath();
        String token = null;
        String projectId = null;

        if(null!=request.getHeader("apihub-token")){
            token = request.getHeader("apihub-token");
        }
        if(null!=request.getParameter("apihub-token")){
            token = request.getParameter("apihub-token");
        }

        // 判断token是否存在
        if(StringUtils.isBlank(token)){
            responseModel = ResponseModel.getNotToken();
            ResponseResult.output(responseModel,response);
            return false;
        }

        // 解码token
        try{
            JwtUser jwtUser = JwtUtils.decodeUser(token);
            request.setAttribute("jwtUser",jwtUser);
            User user = userService.findByUid(jwtUser.getUid());
            if(null==user){
                responseModel = ResponseModel.getParameterError();
                responseModel.setMsg("用户信息不存在");
                ResponseResult.output(responseModel,response);
                return false;
            }
            if(!user.getStatus().equals(UserStatus.ON)){
                responseModel = ResponseModel.getParameterError();
                responseModel.setMsg("用户状态异常");
                ResponseResult.output(responseModel,response);
                logger.error("用户状态异常(status:"+user.getStatus()+")");
                return false;
            }
            request.setAttribute("user",user);
        }catch (Exception e){
            logger.error("token解码失败:"+token);
            responseModel = ResponseModel.getTokenError();
            responseModel.setMsg("token无效");
            ResponseResult.output(responseModel,response);
            return false;
        }

        // 校验项目id
        if(antPathMatcher.match("/project/**", path)){
            projectId = request.getHeader("project-id");
            if(StringUtils.isBlank(projectId)){
                responseModel = ResponseModel.getNotParameter();
                responseModel.setMsg("缺少项目id参数");
                ResponseResult.output(responseModel,response);
                return false;
            }

            request.setAttribute("projectId", projectId);
        }
        return true;
    }
}
