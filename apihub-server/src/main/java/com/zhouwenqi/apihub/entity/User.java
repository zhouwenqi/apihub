package com.zhouwenqi.apihub.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhouwenqi.apihub.common.IdDeserializer;
import com.zhouwenqi.apihub.common.IdSerializer;
import com.zhouwenqi.apihub.model.enums.RequestSource;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhouwenqi on 2019/1/25.
 */
public class User implements Serializable {
    @Id
    private ObjectId id;
    private String uid;
    private String pwd;
    private String email;
    private RequestSource requestSource;
    private Date createDate;
    private Date editDate;

    @JsonSerialize(using = IdSerializer.class)
    public ObjectId getId() {
        return id;
    }

    @JsonDeserialize(using = IdDeserializer.class)
    public void setId(ObjectId id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RequestSource getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(RequestSource requestSource) {
        this.requestSource = requestSource;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }
}
