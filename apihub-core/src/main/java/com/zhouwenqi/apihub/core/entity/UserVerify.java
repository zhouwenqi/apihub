package com.zhouwenqi.apihub.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhouwenqi.apihub.core.common.IdDeserializer;
import com.zhouwenqi.apihub.core.common.IdSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Entity - 用户验证
 * Created by zhouwenqi on 2019/1/28.
 */
@Document("userVerify")
public class UserVerify extends BaseEntity {
    // 用户id
    private ObjectId userId;
    // email
    @Indexed
    private String email;
    // 验证码
    private String code;
    // 验证码类型(0:注册验证邮箱,1:验证帐号)
    private Integer codeType;
    // 到期时间
    private Date expireDate;

    @JsonSerialize(using = IdSerializer.class)
    public ObjectId getUserId() {
        return userId;
    }

    @JsonDeserialize(using = IdDeserializer.class)
    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}
