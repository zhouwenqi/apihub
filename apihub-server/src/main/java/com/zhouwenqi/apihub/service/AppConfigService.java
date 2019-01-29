package com.zhouwenqi.apihub.service;

import com.zhouwenqi.apihub.core.entity.AppConfig;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * Service - 系统配置
 * Created by zhouwenqi on 2019/1/28.
 */
@Service("appConfigService")
public class AppConfigService extends BaseService<AppConfig> {
    public static AppConfig appConfig;

    /**
     * 从数据库中获取系统配置
     * @return
     */
    public AppConfig findSystemConfig(){
        Query query = new Query();
        AppConfig appConfig = mongoTemplate.findOne(query,AppConfig.class);
        if(null == appConfig){
            appConfig = new AppConfig();
            appConfig.setIsAllowLogin(true);
            appConfig.setIsAllowRegister(true);
            appConfig.setIsAllowRequest(true);
            appConfig.setIsAllowValidateRegisterEmail(true);
            mongoTemplate.save(appConfig,"appConfig");
        }
        return appConfig;
    }

    /**
     * 获取当前系统配置系统
     * @return
     */
    public AppConfig findCurrentSystemConfig(){
        if(null == appConfig){
            appConfig = findSystemConfig();
        }
        return appConfig;
    }
}
