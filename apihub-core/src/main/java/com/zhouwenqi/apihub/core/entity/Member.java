package com.zhouwenqi.apihub.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhouwenqi.apihub.core.common.IdDeserializer;
import com.zhouwenqi.apihub.core.common.IdSerializer;
import com.zhouwenqi.apihub.core.model.enums.RoleLevel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity - 项目成员
 * Created by zhouwenqi on 2019/1/29.
 */
@Document(collection = "member")
public class Member extends BaseEntity {
    // 项目id
    @Indexed
    private ObjectId projectId;
    // 用户id
    @Indexed
    private ObjectId userId;
    // 角色
    private RoleLevel roleLevel;
    // 权限
    private Authority authority;

    @JsonSerialize(using = IdSerializer.class)
    public ObjectId getProjectId() {
        return projectId;
    }

    @JsonDeserialize(using = IdDeserializer.class)
    public void setProjectId(ObjectId projectId) {
        this.projectId = projectId;
    }

    @JsonSerialize(using = IdSerializer.class)
    public ObjectId getUserId() {
        return userId;
    }

    @JsonDeserialize(using = IdDeserializer.class)
    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public RoleLevel getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(RoleLevel roleLevel) {
        this.roleLevel = roleLevel;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }
}
