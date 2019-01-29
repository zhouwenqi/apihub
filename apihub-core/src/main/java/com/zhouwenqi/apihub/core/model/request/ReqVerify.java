package com.zhouwenqi.apihub.core.model.request;

import java.io.Serializable;

/**
 * Request - 验证
 * Created by zhouwenqi on 2019/1/28.
 */
public class ReqVerify extends ReqBase {
    // email或帐号
    private String email;
    // 验证码
    private String verifyCode;
    // 验证码类型(0:注册验证邮箱,1:验证帐号)
    private Integer codeType;
    // 密码
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
