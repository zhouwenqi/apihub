package com.zhouwenqi.apihub.core.entity;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Entity - 权限
 * Created by zhouwenqi on 2019/1/29.
 */
public class Authority extends BaseEntity {
    // 修改项目
    @Field("mp")
    private Boolean modifyProject;
    // 创建目录
    @Field("cd")
    private Boolean createDirectory;
    // 修改目录
    @Field("md")
    private Boolean modifyDirectory;
    // 删除目录
    @Field("dd")
    private Boolean deleteDirectory;
    // 创建api
    @Field("ca")
    private Boolean createApi;
    // 修改api
    @Field("ma")
    private Boolean modifyApi;
    // 删除api
    @Field("da")
    private Boolean deleteApi;
    // 添加项目成员
    @Field("cm")
    private Boolean createMember;
    // 修改项目成员
    @Field("mm")
    private Boolean modifyMember;
    // 删除项目成员
    @Field("dm")
    private Boolean deleteMember;

    public Boolean getModifyProject() {
        return modifyProject;
    }

    public void setModifyProject(Boolean modifyProject) {
        this.modifyProject = modifyProject;
    }

    public Boolean getCreateDirectory() {
        return createDirectory;
    }

    public void setCreateDirectory(Boolean createDirectory) {
        this.createDirectory = createDirectory;
    }

    public Boolean getModifyDirectory() {
        return modifyDirectory;
    }

    public void setModifyDirectory(Boolean modifyDirectory) {
        this.modifyDirectory = modifyDirectory;
    }

    public Boolean getDeleteDirectory() {
        return deleteDirectory;
    }

    public void setDeleteDirectory(Boolean deleteDirectory) {
        this.deleteDirectory = deleteDirectory;
    }

    public Boolean getCreateApi() {
        return createApi;
    }

    public void setCreateApi(Boolean createApi) {
        this.createApi = createApi;
    }

    public Boolean getModifyApi() {
        return modifyApi;
    }

    public void setModifyApi(Boolean modifyApi) {
        this.modifyApi = modifyApi;
    }

    public Boolean getDeleteApi() {
        return deleteApi;
    }

    public void setDeleteApi(Boolean deleteApi) {
        this.deleteApi = deleteApi;
    }

    public Boolean getCreateMember() {
        return createMember;
    }

    public void setCreateMember(Boolean createMember) {
        this.createMember = createMember;
    }

    public Boolean getModifyMember() {
        return modifyMember;
    }

    public void setModifyMember(Boolean modifyMember) {
        this.modifyMember = modifyMember;
    }

    public Boolean getDeleteMember() {
        return deleteMember;
    }

    public void setDeleteMember(Boolean deleteMember) {
        this.deleteMember = deleteMember;
    }
}
