package com.zhouwenqi.apihub.interceptor;

import com.zhouwenqi.apihub.core.entity.Member;
import com.zhouwenqi.apihub.core.entity.Project;
import com.zhouwenqi.apihub.core.model.JwtUser;
import com.zhouwenqi.apihub.core.model.enums.RoleLevel;
import com.zhouwenqi.apihub.core.model.response.ResponseModel;
import com.zhouwenqi.apihub.core.model.response.ResponseResult;
import com.zhouwenqi.apihub.service.MemberService;
import com.zhouwenqi.apihub.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器 - 权限校验
 * Created by zhouwenqi on 2018/9/25.
 */
public class AccessInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private MemberService memberService;

    private Logger logger = LoggerFactory.getLogger(AccessInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod().toLowerCase();
        if(method.equals("options")){
            return true;
        }

        ResponseModel responseModel;
        JwtUser jwtUser  = (JwtUser)request.getAttribute("jwtUser");
        String projectId = request.getAttribute("projectId").toString();

        // 校验项目信息
        Project project = projectService.findById(projectId);
        if(null==project){
            responseModel = ResponseModel.getParameterError();
            responseModel.setMsg("项目不存在");
            ResponseResult.output(responseModel,response);
            return false;
        }
        request.setAttribute("project",project);

        // 校验成员信息
        Member member = memberService.find(jwtUser.getId(),projectId);
        if(null == member){
            responseModel = ResponseModel.getNotAuthority();
            responseModel.setMsg("禁止越界访问");
            ResponseResult.output(responseModel,response);
            logger.error("越界访问(uid:"+jwtUser.getUid()+", projectId:"+projectId+")");
            return false;
        }
        request.setAttribute("member",member);
        return true;
    }
}
