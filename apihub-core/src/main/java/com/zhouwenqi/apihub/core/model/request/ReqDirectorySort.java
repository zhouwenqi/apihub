package com.zhouwenqi.apihub.core.model.request;

import org.bson.types.ObjectId;

/**
 * Request - 目录排序
 * Created by zhouwenqi on 2019/2/2.
 */
public class ReqDirectorySort extends ReqBase {
    // 目录id列表
    private String[] ids;
    // 父级目录id
    private ObjectId directionId;

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public ObjectId getDirectionId() {
        return directionId;
    }

    public void setDirectionId(ObjectId directionId) {
        this.directionId = directionId;
    }
}
