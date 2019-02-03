package com.zhouwenqi.apihub.core.model.response;

import com.zhouwenqi.apihub.core.entity.Directory;

import java.util.List;

/**
 * Response - 目录
 * Created by zhouwenqi on 2019/2/3.
 */
public class RspDirectory extends Directory {
    // 子目录列表
    private List<Directory> directorys ;

    public List<Directory> getDirectorys() {
        return directorys;
    }

    public void setDirectorys(List<Directory> directorys) {
        this.directorys = directorys;
    }
}
