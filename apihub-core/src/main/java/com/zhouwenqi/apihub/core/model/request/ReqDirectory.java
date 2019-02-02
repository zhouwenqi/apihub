package com.zhouwenqi.apihub.core.model.request;

import org.bson.types.ObjectId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Request - 目录
 * Created by zhouwenqi on 2019/2/2.
 */
public class ReqDirectory extends ReqBase {
    // 目录id
    private ObjectId id;
    // 目录名
    @NotBlank(message = "目录名称不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]{2,20}",message = "目录名称格式不正确")
    private String name;
    // 描述
    private String description;
    // 上级目级id
    private ObjectId directoryId;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

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

    public ObjectId getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(ObjectId directoryId) {
        this.directoryId = directoryId;
    }
}
