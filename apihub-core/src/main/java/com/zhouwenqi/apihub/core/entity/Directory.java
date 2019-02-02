package com.zhouwenqi.apihub.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhouwenqi.apihub.core.common.IdDeserializer;
import com.zhouwenqi.apihub.core.common.IdSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity - 目录
 * Created by zhouwenqi on 2019/1/29.
 */
@Document(collection = "directory")
public class Directory extends BaseEntity {
    // 目录名
    @Indexed
    private String name;
    // 描述
    private String description;
    // 上级目录id
    private ObjectId directoryId;
    // 所属项目
    @Indexed
    private ObjectId projectId;
    // 创建人
    @Indexed
    private ObjectId userId;
    // 自定义排序
    @Indexed
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

    @JsonSerialize(using = IdSerializer.class)
    public ObjectId getDirectoryId() {
        return directoryId;
    }

    @JsonDeserialize(using = IdDeserializer.class)
    public void setDirectoryId(ObjectId directoryId) {
        this.directoryId = directoryId;
    }

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

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }
}
