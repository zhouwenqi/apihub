package com.zhouwenqi.apihub.core.model.request;

import javax.validation.constraints.NotBlank;

/**
 * Request - 登录
 * Created by zhouwenqi on 2019/1/29.
 */
public class ReqLogin extends ReqBase {
    // 帐号
    @NotBlank(message = "帐号不能为空")
    private String uid;
    // 密码
    // 密码
    @NotBlank(message = "密码不能为空")
    private String pwd;

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
}
