package com.zhouwenqi.apihub.core.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity - 目录
 * Created by zhouwenqi on 2019/1/29.
 */
@Document(collection = "directory")
public class Directory extends BaseEntity {
    // 目录名
    private String name;
    // 描述
    private String description;
    // 所属项目
    private ObjectId projectId;
    // 创建人
    private ObjectId userId;
    // 自定义排序
    private Integer idx;

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

    public ObjectId getProjectId() {
        return projectId;
    }

    public void setProjectId(ObjectId projectId) {
        this.projectId = projectId;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }
}
