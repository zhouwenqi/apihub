package com.zhouwenqi.apihub.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhouwenqi.apihub.core.common.IdDeserializer;
import com.zhouwenqi.apihub.core.common.IdSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity - basic
 * Created by zhouwenqi on 2019/1/28.
 */
public class BaseEntity implements Serializable {
    @Id
    private ObjectId id;
    // 创建时间
    @Indexed
    private Date createDate;
    // 修改时间
    @Indexed
    private Date editDate;

    @JsonSerialize(using = IdSerializer.class)
    public ObjectId getId() {
        return id;
    }

    @JsonDeserialize(using = IdDeserializer.class)
    public void setId(ObjectId id) {
        this.id = id;
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
