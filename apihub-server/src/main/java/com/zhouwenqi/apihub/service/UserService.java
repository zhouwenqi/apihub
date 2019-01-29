package com.zhouwenqi.apihub.service;

import com.zhouwenqi.apihub.core.entity.User;
import com.zhouwenqi.apihub.core.model.JwtUser;
import com.zhouwenqi.apihub.core.model.enums.UserStatus;
import com.zhouwenqi.apihub.core.model.request.ReqRegister;
import com.zhouwenqi.apihub.core.utils.JwtUtils;
import com.zhouwenqi.apihub.exception.ApihubFailedException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service - 用户
 * Created by zhouwenqi on 2019/1/25.
 */
@Service("userService")
public class UserService extends BaseService<User> {
    /**
     * 根据帐号查询用户信息
     * @param uid 帐号
     * @return
     */
    public User findByUid(String uid){
        Criteria criteria = Criteria.where("uid").is(uid);
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query,User.class);
    }

    /**
     * 注册用户
     * @param reqRegister 注册信息
     * @return
     */
    public User register(ReqRegister reqRegister){
        User regUser = findByUid(reqRegister.getUid());
        if(null!=regUser){
            throw new ApihubFailedException("帐号已存在");
        }
        String pwd = DigestUtils.md5Hex(reqRegister.getPwd()).toLowerCase();
        User user = new User();
        user.setUid(reqRegister.getUid());
        user.setNickName(reqRegister.getNickName());
        user.setMobile(reqRegister.getMobile());
        user.setGender(reqRegister.getGender());
        user.setPwd(pwd);
        user.setCreateDate(new Date());
        user.setEditDate(new Date());
        user.setStatus(UserStatus.ON);
        super.insert(user,"user");
        return user;
    }

    /**
     * 生成token
     * @param user 用户信息
     * @return
     */
    public String buildToken(User user){
        JwtUser jwtUser = new JwtUser();
        jwtUser.setId(user.getId().toString());
        jwtUser.setUid(user.getUid());
        return JwtUtils.encode(jwtUser);
    }
}
