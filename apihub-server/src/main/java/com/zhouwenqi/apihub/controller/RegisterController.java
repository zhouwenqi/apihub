package com.zhouwenqi.apihub.controller;

import com.zhouwenqi.apihub.core.entity.AppConfig;
import com.zhouwenqi.apihub.core.entity.User;
import com.zhouwenqi.apihub.core.entity.UserVerify;
import com.zhouwenqi.apihub.core.model.request.ReqRegister;
import com.zhouwenqi.apihub.core.model.request.ReqVerify;
import com.zhouwenqi.apihub.core.model.response.ResponseModel;
import com.zhouwenqi.apihub.exception.ApihubFailedException;
import com.zhouwenqi.apihub.exception.ApihubNotParameterException;
import com.zhouwenqi.apihub.exception.ApihubParameterErrorException;
import com.zhouwenqi.apihub.service.AppConfigService;
import com.zhouwenqi.apihub.service.UserService;
import com.zhouwenqi.apihub.service.UserVerifyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * Controller - 注册
 * Created by zhouwenqi on 2019/1/28.
 */
@RestController
public class RegisterController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private AppConfigService appConfigService;
    @Autowired
    private UserVerifyService userVerifyService;

    /**
     * 注册帐号
     * @param reqRegister 注册信息
     * @param bindingResult
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseModel register(@Valid ReqRegister reqRegister, BindingResult bindingResult){
        // 校验参数
        if(bindingResult.hasErrors()){
            throw new ApihubParameterErrorException(bindingResult.getFieldError().getDefaultMessage());
        }

        // 校验注册验证码
        AppConfig appConfig = appConfigService.findCurrentSystemConfig();
        if(null==appConfig || appConfig.getIsAllowValidateRegisterEmail()){
            if(StringUtils.isBlank(reqRegister.getVerifyCode())){
                throw new ApihubNotParameterException("验证码不能为空");
            }
            ReqVerify reqVerify = new ReqVerify();
            reqVerify.setEmail(reqRegister.getUid());
            reqVerify.setCodeType(0);
            reqVerify.setVerifyCode(reqRegister.getVerifyCode());
            UserVerify userVerify = userVerifyService.find(reqVerify);
            if(null==userVerify){
                throw new ApihubFailedException("无效的验证码");
            }

            if(userVerify.getExpireDate().before(new Date())){
                throw new ApihubFailedException("验证码已过期");
            }
            // 让验证码过期
            userVerifyService.lost(reqVerify);
        }

        // 注册用户
        User user = userService.register(reqRegister);
        ResponseModel responseModel = ResponseModel.getSuccess();
        responseModel.addData("user",user);
        return responseModel;
    }
}
