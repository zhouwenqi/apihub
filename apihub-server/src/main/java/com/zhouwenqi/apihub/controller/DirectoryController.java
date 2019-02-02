package com.zhouwenqi.apihub.controller;

import com.zhouwenqi.apihub.core.entity.Directory;
import com.zhouwenqi.apihub.core.entity.Member;
import com.zhouwenqi.apihub.core.entity.Project;
import com.zhouwenqi.apihub.core.entity.User;
import com.zhouwenqi.apihub.core.model.request.ReqDirectory;
import com.zhouwenqi.apihub.core.model.request.ReqDirectorySort;
import com.zhouwenqi.apihub.core.model.response.ResponseModel;
import com.zhouwenqi.apihub.exception.ApihubNotAuthorityException;
import com.zhouwenqi.apihub.exception.ApihubNotParameterException;
import com.zhouwenqi.apihub.exception.ApihubParameterErrorException;
import com.zhouwenqi.apihub.service.DirectoryService;
import com.zhouwenqi.apihub.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller - 目录
 * Created by zhouwenqi on 2019/2/2.
 */
@RestController
@RequestMapping("project")
public class DirectoryController extends TokenController {
    @Autowired
    private DirectoryService directoryService;
    @Autowired
    private MemberService memberService;
    /**
     * 创建目录
     * @param reqDirectory 目录信息
     * @param bindingResult
     * @return
     */
    @PostMapping("directory")
    @ResponseBody
    public ResponseModel createDirectory(@Valid ReqDirectory reqDirectory, BindingResult bindingResult){
        // 校验参数
        if(bindingResult.hasErrors()){
            throw new ApihubParameterErrorException(bindingResult.getFieldError().getDefaultMessage());
        }

        User user = getUser();
        Project project = getProject();

        Member member = memberService.find(user.getId(),project.getId());
        if(!member.getAuthority().getCreateDirectory()){
            throw new ApihubNotAuthorityException("权限不够");
        }

        Directory directory = new Directory();
        directory.setName(reqDirectory.getName());
        directory.setDescription(reqDirectory.getDescription());
        directory.setDirectoryId(reqDirectory.getDirectoryId());
        directory.setUserId(user.getId());
        directory.setProjectId(project.getId());

        if(directoryService.isRepeat(directory,false)){
            throw new ApihubParameterErrorException("目录名已存在");
        }

        directoryService.create(directory);
        ResponseModel responseModel = ResponseModel.getSuccess();
        responseModel.addData("directory",directory);
        return responseModel;
    }

    /**
     * 修改目录
     * @param reqDirectory 目录信息
     * @param bindingResult
     * @return
     */
    @PutMapping("directory")
    @ResponseBody
    public ResponseModel updateDirectory(@Valid ReqDirectory reqDirectory, BindingResult bindingResult){
        // 校验参数
        if(bindingResult.hasErrors()){
            throw new ApihubParameterErrorException(bindingResult.getFieldError().getDefaultMessage());
        }
        if(null == reqDirectory.getId()){
            throw  new ApihubNotParameterException("目录id不能为空");
        }
        User user = getUser();
        Project project = getProject();

        // 判断目录结构是否正确
        if(null != reqDirectory.getDirectoryId()){
            Directory parentDirectory = directoryService.findById(reqDirectory.getDirectoryId());
            if(null == parentDirectory || (null != parentDirectory.getDirectoryId() || parentDirectory.getProjectId() != project.getId())){
                throw new ApihubParameterErrorException("目录结构错误");
            }
        }

        Directory directory = directoryService.findById(reqDirectory.getId());
        if(null == directory){
            throw new ApihubParameterErrorException("目录不存在");
        }


        Member member = memberService.find(user.getId(),project.getId());
        if(!member.getAuthority().getModifyDirectory()){
            throw new ApihubNotAuthorityException("权限不够");
        }

        directory.setName(reqDirectory.getName());
        directory.setDescription(reqDirectory.getDescription());

        if(directoryService.isRepeat(directory,true)){
            throw new ApihubParameterErrorException("目录名已存在");
        }

        directoryService.update(directory);
        ResponseModel responseModel = ResponseModel.getSuccess();
        responseModel.addData("directory",directory);
        return responseModel;
    }

    @PutMapping("directory/sort")
    @ResponseBody
    public ResponseModel sort(ReqDirectorySort reqDirectorySort){
        Project project = getProject();
        if(null !=reqDirectorySort.getDirectionId()){
            Directory parentDirectory = directoryService.findById(reqDirectorySort.getDirectionId());
            if(null == parentDirectory || (null != parentDirectory.getDirectoryId() || parentDirectory.getProjectId() != project.getId())){
                throw new ApihubParameterErrorException("目录结构错误");
            }
        }
        if(null == reqDirectorySort.getIds() || reqDirectorySort.getIds().length<=0){
            throw new ApihubParameterErrorException("目录列表不能为空");
        }
        directoryService.sort(reqDirectorySort,project.getId());
        return ResponseModel.getSuccess();
    }

}
