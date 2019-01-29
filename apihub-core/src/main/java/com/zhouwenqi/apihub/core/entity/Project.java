package com.zhouwenqi.apihub.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhouwenqi.apihub.core.common.IdDeserializer;
import com.zhouwenqi.apihub.core.common.IdSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity - 项目
 * Created by zhouwenqi on 2019/1/29.
 */
@Document(collection = "project")
public class Project extends BaseEntity {
    // 项目名称
    @Indexed
    private String name;
    // 项目描述
    private String description;
    // icon
    private String icon;
    // 项目创建人
    @Indexed
    private ObjectId userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @JsonSerialize(using = IdSerializer.class)
    public ObjectId getUserId() {
        return userId;
    }

    @JsonDeserialize(using = IdDeserializer.class)
    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }
}
