package com.zhouwenqi.apihub.core.repository;

import com.zhouwenqi.apihub.core.entity.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository - 项目
 * Created by zhouwenqi on 2019/2/1.
 */
@Repository
public interface ProjectRepository extends MongoRepository<Project,String> {
}
