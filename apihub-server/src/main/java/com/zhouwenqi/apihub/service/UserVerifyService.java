package com.zhouwenqi.apihub.service;

import com.zhouwenqi.apihub.core.entity.UserVerify;
import com.zhouwenqi.apihub.core.model.request.ReqVerify;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service - 用户验证
 * Created by zhouwenqi on 2019/1/28.
 */
@Service("userVerifyService")
public class UserVerifyService extends BaseService<UserVerify> {
    /**
     * 查询验证码信息
     * @param email Email
     * @param codeType 验证码类型(0:注册验证邮箱,1:验证帐号)
     * @return
     */
    public UserVerify find(String email,Integer codeType){
        Criteria criteria = Criteria.where("email").is(email);
        criteria.and("codeType").is(codeType);
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query,UserVerify.class);
    }

    /**
     * 查询验证码信息
     * @param verify 验证信息
     * @return
     */
    public UserVerify find(ReqVerify verify){
        Criteria criteria = Criteria.where("email").is(verify.getEmail());
        criteria.and("codeType").is(verify.getCodeType());
        criteria.and("code").is(verify.getVerifyCode());
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query,UserVerify.class);
    }

    /**
     * 让验证码立即过期
     * @param verify 验证信息
     */
    public void lost(ReqVerify verify){
        Criteria criteria = Criteria.where("email").is(verify.getEmail());
        criteria.and("code").is(verify.getVerifyCode());
        criteria.and("codeType").is(verify.getCodeType());
        Query query = new Query(criteria);
        Update update = Update.update("expireDate",new Date());
        super.updateMulti(query,update,"userVerify");
    }
}
