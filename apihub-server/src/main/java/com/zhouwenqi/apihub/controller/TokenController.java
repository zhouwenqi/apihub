package com.zhouwenqi.apihub.controller;

import com.zhouwenqi.apihub.core.entity.Member;
import com.zhouwenqi.apihub.core.entity.Project;
import com.zhouwenqi.apihub.core.entity.User;
import com.zhouwenqi.apihub.core.model.JwtUser;
import org.springframework.web.context.request.RequestAttributes;

/**
 * Controller -Token
 * Created by zhouwenqi on 2019/1/30.
 */
public class TokenController extends BaseController {
    public Project getProject(){
        return (Project)getContextAttribute().getAttribute("project", RequestAttributes.SCOPE_REQUEST);
    }
    public JwtUser getJwtUser(){
        return (JwtUser)getContextAttribute().getAttribute("jwtUser", RequestAttributes.SCOPE_REQUEST);
    }
    public User getUser(){
        return (User)getContextAttribute().getAttribute("user", RequestAttributes.SCOPE_REQUEST);
    }
    public Member getMember(){
        return (Member)getContextAttribute().getAttribute("member", RequestAttributes.SCOPE_REQUEST);
    }
}
