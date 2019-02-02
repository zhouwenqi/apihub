package com.zhouwenqi.apihub.controller;

import com.zhouwenqi.apihub.core.entity.Member;
import com.zhouwenqi.apihub.core.entity.Project;
import com.zhouwenqi.apihub.core.entity.User;
import com.zhouwenqi.apihub.core.model.enums.RoleLevel;
import com.zhouwenqi.apihub.core.model.response.ResponseModel;
import com.zhouwenqi.apihub.exception.ApihubNotParameterException;
import com.zhouwenqi.apihub.exception.ApihubParameterErrorException;
import com.zhouwenqi.apihub.service.MemberService;
import com.zhouwenqi.apihub.service.ProjectService;
import com.zhouwenqi.apihub.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller - 项目
 * Created by zhouwenqi on 2019/2/1.
 */
@RestController
@RequestMapping("project")
public class ProjectController extends TokenController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private UserService userService;

    @PostMapping("join")
    @ResponseBody
    public ResponseModel join(String userId){
        if(StringUtils.isBlank(userId)){
            throw new ApihubNotParameterException("用户id不能为空");
        }
        User user = userService.findById(userId);
        if(null == user){
            throw new ApihubParameterErrorException("用户不存在");
        }
        Project project = getProject();
        Member member = memberService.join(user.getId(),project.getId(), RoleLevel.BUILD);
        ResponseModel responseModel = ResponseModel.getSuccess();
        responseModel.addData("member",member);
        return responseModel;
    }

    /**
     * 获取项目成员列表
     * @return
     */
    @GetMapping("members")
    @ResponseBody
    public ResponseModel members(){
        Project project = getProject();
        ResponseModel responseModel = ResponseModel.getSuccess();
        List<Member> members = memberService.findByProjectId(project.getId());
        responseModel.addData("members",members);
        return responseModel;
    }



}
