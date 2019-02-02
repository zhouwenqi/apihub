package com.zhouwenqi.apihub.controller;

import com.zhouwenqi.apihub.core.entity.Member;
import com.zhouwenqi.apihub.core.entity.Project;
import com.zhouwenqi.apihub.core.entity.User;
import com.zhouwenqi.apihub.core.model.enums.FileType;
import com.zhouwenqi.apihub.core.model.enums.ProjectRange;
import com.zhouwenqi.apihub.core.model.enums.RoleLevel;
import com.zhouwenqi.apihub.core.model.request.ReqProject;
import com.zhouwenqi.apihub.core.model.response.ResponseModel;
import com.zhouwenqi.apihub.core.repository.UserRepository;
import com.zhouwenqi.apihub.core.utils.ApihubUtils;
import com.zhouwenqi.apihub.core.utils.RegexUtils;
import com.zhouwenqi.apihub.exception.ApihubNotAuthorityException;
import com.zhouwenqi.apihub.exception.ApihubNotParameterException;
import com.zhouwenqi.apihub.exception.ApihubParameterErrorException;
import com.zhouwenqi.apihub.service.FileService;
import com.zhouwenqi.apihub.service.MemberService;
import com.zhouwenqi.apihub.service.ProjectService;
import com.zhouwenqi.apihub.service.UserService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controller - 用户
 * Created by zhouwenqi on 2019/1/24.
 */
@RestController
@RequestMapping("user")
public class UserController extends TokenController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private MemberService memberService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 获取用户信息
     * @param id 用户id
     * @return
     */
    @GetMapping("info")
    @ResponseBody
    public ResponseModel info(String id){
        if(StringUtils.isBlank(id)){
            throw new ApihubNotParameterException("缺少用户id");
        }
        User user = userService.findById(id);
        if(null == user){
            throw new ApihubParameterErrorException("用户id错误");
        }
        ResponseModel responseModel = ResponseModel.getSuccess();
        responseModel.addData("user",user);
        return responseModel;

    }

    /**
     * 修改用户资料
     * @param user
     * @return
     */
    @PutMapping("info")
    @ResponseBody
    public ResponseModel update(User user){
        User updateUser = getUser();
        if(StringUtils.isBlank(user.getNickName())){
            throw new ApihubParameterErrorException("昵称不能为空");
        }
        if(StringUtils.isNotBlank(user.getEmail())){
            if(!RegexUtils.isEmail(user.getEmail())){
                throw new ApihubParameterErrorException("Email格式不正确");
            }
        }
        if(StringUtils.isNotBlank(user.getMobile())){
            if(!RegexUtils.isMobile(user.getMobile())){
                throw new ApihubParameterErrorException("手机号码格式不正确");
            }
        }
        updateUser.setGender(user.getGender());
        updateUser.setEmail(user.getEmail());
        updateUser.setMobile(user.getMobile());
        updateUser.setNickName(user.getNickName());
        updateUser.setEditDate(new Date());
        userRepository.save(updateUser);
        ResponseModel responseModel = ResponseModel.getSuccess();
        responseModel.addData("user",updateUser);
        return responseModel;
    }

    /**
     * 上传用户头像
     * @param photo 图像文件
     * @return
     * @throws Exception
     */
    @PostMapping("photo")
    @ResponseBody
    public ResponseModel uploadPhoto(@RequestParam("photo") MultipartFile photo) throws Exception{
        if(photo.isEmpty()){
            throw new ApihubNotParameterException("缺少文件参数");
        }

        // 获取文件扩展名
        String fileSuffix = ApihubUtils.getFileSuffix(photo.getOriginalFilename());
        if(StringUtils.isBlank(fileSuffix)){
            throw new ApihubParameterErrorException("文件类型错误");
        }

        User user = getUser();
        String fileName = user.getId().toString()+"."+fileSuffix;
        ObjectId photoId = fileService.uploadImage(photo.getInputStream(),fileName,fileSuffix,FileType.PHOTO);
        user.setPhoto(photoId);
        userRepository.save(user);
        ResponseModel responseModel = ResponseModel.getSuccess();
        responseModel.addData("photoId",photoId.toString());
        return responseModel;
    }

    /**
     * 创建项目
     * @param reqProject 项目信息
     * @param bindingResult
     * @return
     */
    @PostMapping("project")
    @ResponseBody
    public ResponseModel createProject(@Valid ReqProject reqProject, BindingResult bindingResult){
        // 校验参数
        if(bindingResult.hasErrors()){
            throw new ApihubParameterErrorException(bindingResult.getFieldError().getDefaultMessage());
        }
        // 获取当前用户信息
        User user = getUser();
        Project project = new Project();
        project.setName(reqProject.getName());
        project.setDescription(reqProject.getDescription());
        project.setUserId(user.getId());
        // 检查项目是否已存在
        if(projectService.isRepeat(project,false)){
            throw new ApihubParameterErrorException("项目名重复");
        }
        // 创建项目
        projectService.createProject(project);
        // 加入项目
        memberService.join(user.getId(),project.getId(), RoleLevel.MANAGER);
        ResponseModel responseModel = ResponseModel.getSuccess();
        responseModel.addData("project",project);
        return responseModel;
    }

    /**
     * 更新项目信息
     * @param reqProject 项目信息
     * @param bindingResult
     * @return
     */
    @PutMapping("project")
    @ResponseBody
    public ResponseModel updateProject(@Valid ReqProject reqProject, BindingResult bindingResult){
        // 校验参数
        if(bindingResult.hasErrors()){
            throw new ApihubParameterErrorException(bindingResult.getFieldError().getDefaultMessage());
        }
        if(null == reqProject.getId()){
            throw new ApihubNotParameterException("项目id不能为空");
        }
        // 获取当前用户信息
        User user = getUser();
        Member member = memberService.find(user.getId(),reqProject.getId());
        if(null == member){
            throw new ApihubNotAuthorityException("非项目成员");
        }
        if(!member.getAuthority().getModifyProject()){
            throw new ApihubNotAuthorityException("权限不够");
        }

        Project project = projectService.findById(reqProject.getId());
        if(null == project){
            throw new ApihubParameterErrorException("项目不存在");
        }
        project.setName(reqProject.getName());
        project.setDescription(reqProject.getDescription());
        // 检查项目是否已存在
        if(projectService.isRepeat(project,true)){
            throw new ApihubParameterErrorException("项目名重复");
        }
        projectService.updateProject(project);
        ResponseModel responseModel = ResponseModel.getSuccess();
        responseModel.addData("project",project);
        return responseModel;
    }

    @GetMapping("projects")
    @ResponseBody
    public ResponseModel projects(ProjectRange range){
        if(null == range){
            range = ProjectRange.ALL;
        }
        User user = getUser();
        System.out.println("RANGE:"+range);
        List<Project> projects;
        switch (range){
            case CREATE:
                projects = projectService.findByCreate(user.getId());
                break;
            case JOIN:
                projects = projectService.findByJoin(user.getId());
                break;
            case ALL:
            default:
                projects = projectService.findByAll(user.getId());
                break;
        }
        ResponseModel responseModel = ResponseModel.getSuccess();
        responseModel.addData("projects",projects);
        return responseModel;
    }

}
