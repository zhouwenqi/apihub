package com.zhouwenqi.apihub.core.model.request;

import org.bson.types.ObjectId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Request - 项目信息
 * Created by zhouwenqi on 2019/2/1.
 */
public class ReqProject extends ReqBase {
    // 项目id
    private ObjectId id;
    // 项目名称
    @NotBlank(message = "项目名称不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]{4,20}",message = "项目名称格式不正确")
    private String name;
    // 项目描述
    private String description;

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
}
