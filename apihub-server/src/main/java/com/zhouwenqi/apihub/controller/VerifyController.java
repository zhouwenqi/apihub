package com.zhouwenqi.apihub.controller;

import com.zhouwenqi.apihub.core.entity.User;
import com.zhouwenqi.apihub.core.entity.UserVerify;
import com.zhouwenqi.apihub.core.model.enums.UserStatus;
import com.zhouwenqi.apihub.core.model.response.ResponseModel;
import com.zhouwenqi.apihub.core.utils.ApihubUtils;
import com.zhouwenqi.apihub.core.utils.RegexUtils;
import com.zhouwenqi.apihub.exception.ApihubAccessRequestException;
import com.zhouwenqi.apihub.exception.ApihubFailedException;
import com.zhouwenqi.apihub.exception.ApihubNotParameterException;
import com.zhouwenqi.apihub.exception.ApihubParameterErrorException;
import com.zhouwenqi.apihub.service.MailService;
import com.zhouwenqi.apihub.service.UserService;
import com.zhouwenqi.apihub.service.UserVerifyService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;

/**
 * Controller - 验证码
 * Created by zhouwenqi on 2019/1/29.
 */
@RestController
@RequestMapping("/verify")
public class VerifyController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserVerifyService userVerifyService;
    @Autowired
    private MailService mailService;

    private Logger logger = LoggerFactory.getLogger(VerifyController.class);

    /**
     * 发送验证码
     * (相同类型的验证码同一邮箱5分钟内只发送一条)
     * @param email Email
     * @param codeType 验证码类型
     * @return
     */
    @GetMapping("send")
    @ResponseBody
    public ResponseModel send(String email,Integer codeType){
        // 参数验校
        if(StringUtils.isBlank(email) || null==codeType){
            throw new ApihubNotParameterException("缺少参数");
        }
        if(!RegexUtils.isEmail(email)){
            throw new ApihubParameterErrorException("email格式不正确");
        }
        if(codeType!=0 && codeType!=1){
            logger.error("验证码类型参数超出范围[0,1] (codeType = "+codeType+")");
            throw new ApihubParameterErrorException("验证码类型参数超出范围");

        }
        User user = userService.findByUid(email);
        if(codeType != 0){
            if(null == user){
                throw new ApihubFailedException("帐号不存在");
            }
            if(!user.getStatus().equals(UserStatus.ON)){
                throw new ApihubAccessRequestException("帐号状态异常");
            }
        } else {
            if(null != user){
                throw new ApihubFailedException("此帐号已被注册");
            }
        }
        // 设置过期时间
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        // 过期时间5分钟
        calendar.add(Calendar.MINUTE,5);
        Date expireDate = calendar.getTime();
        // 生成验证码
        String code = ApihubUtils.getVerifyCode(6).toUpperCase();
        UserVerify userVerify = userVerifyService.find(email,codeType);
        if(null==userVerify || userVerify.getExpireDate()==null || userVerify.getExpireDate().before(currentDate)){
            userVerify = new UserVerify();
            userVerify.setCode(code);
            userVerify.setEmail(email);
            userVerify.setCodeType(codeType);
            userVerify.setCreateDate(new Date());
            userVerify.setExpireDate(expireDate);
            if(user != null){
                userVerify.setUserId(user.getId());
            }
            userVerifyService.insert(userVerify,"userVerify");
            mailService.sendVerifyEmail(email,userVerify);
            logger.info("验证码已发送(email = "+email+", code = " + code + ", codeType = "+codeType+")");
        }

        ResponseModel responseModel = ResponseModel.getSuccess();

        responseModel.setMsg("验证码已发送到您的邮箱");
        return responseModel;
    }
}
