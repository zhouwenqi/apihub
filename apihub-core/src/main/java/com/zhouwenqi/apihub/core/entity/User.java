package com.zhouwenqi.apihub.core.entity;

import com.zhouwenqi.apihub.core.model.enums.UserStatus;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity - 用户
 * Created by zhouwenqi on 2019/1/28.
 */
@Document(collection = "user")
public class User extends BaseEntity {
    // 用户名
    @Indexed
    private String uid;
    // 密码
    private String pwd;
    // 昵称
    private String nickName;
    // 性别
    private String gender;
    // 照片
    private String photo;
    // 邮箱
    private String email;
    // 手机号
    private String mobile;
    // 用户状态
    private UserStatus status;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
