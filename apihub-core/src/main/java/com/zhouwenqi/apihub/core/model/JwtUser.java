package com.zhouwenqi.apihub.core.model;

import java.io.Serializable;

/**
 * Entity - jwt
 * Created by zhouwenqi on 2019/1/29.
 */
public class JwtUser implements Serializable {
    // 用户名
    private String uid;
    // 用户id
    private String id;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
