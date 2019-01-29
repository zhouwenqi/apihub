package com.zhouwenqi.apihub.core.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity - 系统配置
 * Created by zhouwenqi on 2019/1/28.
 */
@Document(collection = "appConfig")
public class AppConfig extends BaseEntity {
    // 是否允许注册
    private Boolean isAllowRegister;
    // 是否允许登录
    private Boolean isAllowLogin;
    // 是否允许访问
    private Boolean isAllowRequest;
    // 是否需要验证注册邮箱
    private Boolean isAllowValidateRegisterEmail;

    public Boolean getAllowRegister() {
        return isAllowRegister;
    }

    public void setIsAllowRegister(Boolean isAllowRegister) {
        this.isAllowRegister = isAllowRegister;
    }

    public Boolean getIsAllowLogin() {
        return isAllowLogin;
    }

    public void setIsAllowLogin(Boolean isAllowLogin) {
        this.isAllowLogin = isAllowLogin;
    }

    public Boolean getIsAllowRequest() {
        return isAllowRequest;
    }

    public void setIsAllowRequest(Boolean isAllowRequest) {
        this.isAllowRequest = isAllowRequest;
    }

    public Boolean getIsAllowValidateRegisterEmail() {
        return isAllowValidateRegisterEmail;
    }

    public void setIsAllowValidateRegisterEmail(Boolean isAllowValidateRegisterEmail) {
        this.isAllowValidateRegisterEmail = isAllowValidateRegisterEmail;
    }
}
