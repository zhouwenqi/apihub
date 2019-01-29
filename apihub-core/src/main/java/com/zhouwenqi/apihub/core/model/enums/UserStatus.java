package com.zhouwenqi.apihub.core.model.enums;

/**
 * Enum - 用户状态
 * Created by zhouwenqi on 2019/1/28.
 */
public enum UserStatus implements BaseEnum {
    ON(0,"启用"),
    OFF(1,"关闭");
    private final Integer index;
    private final String tag;
    UserStatus(Integer index,String tag){
        this.index = index;
        this.tag = tag;
    }
}
