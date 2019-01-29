package com.zhouwenqi.apihub.core.model.enums;

/**
 * Enum - 角色
 * Created by zhouwenqi on 2019/1/29.
 */
public enum  RoleLevel implements BaseEnum {
    MANAGER(0,"管理员"),
    BUILD(1,"后端开发"),
    FONTEND(2,"前端开发"),
    TEST(3,"测试人员");
    private final Integer index;
    private final String tag;
    RoleLevel(Integer index,String tag){
        this.index = index;
        this.tag = tag;
    }
}
