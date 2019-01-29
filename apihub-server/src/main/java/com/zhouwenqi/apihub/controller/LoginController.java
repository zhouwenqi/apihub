package com.zhouwenqi.apihub.controller;

import com.zhouwenqi.apihub.core.entity.AppConfig;
import com.zhouwenqi.apihub.core.entity.User;
import com.zhouwenqi.apihub.core.model.enums.UserStatus;
import com.zhouwenqi.apihub.core.model.request.ReqLogin;
import com.zhouwenqi.apihub.core.model.response.ResponseModel;
import com.zhouwenqi.apihub.exception.ApihubAccessRequestException;
import com.zhouwenqi.apihub.exception.ApihubFailedException;
import com.zhouwenqi.apihub.exception.ApihubParameterErrorException;
import com.zhouwenqi.apihub.service.AppConfigService;
import com.zhouwenqi.apihub.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller - 登录
 * Created by zhouwenqi on 2019/1/29.
 */
@RestController
public class LoginController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private AppConfigService appConfigService;

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 用户登录
     * @param reqLogin 登录参数
     * @param bindingResult
     * @return
     */
    @GetMapping("login")
    @ResponseBody
    public ResponseModel login(@Valid ReqLogin reqLogin, BindingResult bindingResult){
        // 校验参数
        if(bindingResult.hasErrors()){
            throw new ApihubParameterErrorException(bindingResult.getFieldError().getDefaultMessage());
        }

        AppConfig appConfig = appConfigService.findCurrentSystemConfig();
        if(null!=appConfig && !appConfig.getIsAllowLogin()){
            throw  new ApihubAccessRequestException("系统登录通道已关闭");
        }
        User user = userService.findByUid(reqLogin.getUid());
        if(null == user){
            logger.error("登录帐号不存在:["+reqLogin.getUid()+"]");
            throw new ApihubFailedException("帐号或密码错误");
        }
        String pwd = DigestUtils.md5Hex(reqLogin.getPwd()).toLowerCase();
        if(!user.getPwd().equals(pwd)){
            logger.error("登录密码错误 ("+reqLogin.getUid()+","+reqLogin.getPwd()+")");
            throw new ApihubFailedException("帐号或密码错误");
        }
        if(!user.getStatus().equals(UserStatus.ON)){
            logger.error("帐号状态异常 (uid:"+reqLogin.getUid()+",status:"+user.getStatus()+")");
            throw new ApihubFailedException("帐号状态异常,请联系管理员");
        }

        // 生成token
        String token = userService.buildToken(user);
        ResponseModel responseModel = ResponseModel.getSuccess();
        responseModel.addData("user",user);
        responseModel.addData("token",token);
        return responseModel;

    }
}
