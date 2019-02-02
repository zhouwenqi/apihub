package com.zhouwenqi.apihub.core.repository;

import com.zhouwenqi.apihub.core.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository - 用户
 * Created by zhouwenqi on 2019/1/30.
 */
@Repository
public interface UserRepository extends MongoRepository<User,String> {
}
