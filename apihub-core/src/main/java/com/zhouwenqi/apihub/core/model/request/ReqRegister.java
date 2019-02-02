package com.zhouwenqi.apihub.core.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Request - 注册
 * Created by zhouwenqi on 2019/1/28.
 */
public class ReqRegister extends ReqBase {
    // 帐号
    @NotBlank(message = "帐号不能为空")
    @Pattern(regexp = "([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$", message = "帐号必需是Email格式")
    private String uid;
    // 密码
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "[a-zA-Z0-9_-]{6,18}$",message = "密码只能是6-18位数或字母组成")
    private String pwd;
    // 昵称
    @NotBlank(message = "昵称不能为空")
    private String nickName;
    // 性别
    private String gender;
    // 手机号码
    @Pattern(regexp = "^(((1[0-9]))+\\d{9})$",message = "手机号码格式不正确")
    private String mobile;
    // 验证码
    private String verifyCode;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
