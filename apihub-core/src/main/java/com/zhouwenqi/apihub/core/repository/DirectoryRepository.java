package com.zhouwenqi.apihub.core.repository;

import com.zhouwenqi.apihub.core.entity.Directory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository - 目录
 * Created by zhouwenqi on 2019/2/2.
 */
@Repository
public interface DirectoryRepository extends MongoRepository<Directory,String> {
}
