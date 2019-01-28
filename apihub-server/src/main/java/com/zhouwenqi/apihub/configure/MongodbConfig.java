package com.zhouwenqi.apihub.configure;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * mongodb配置
 * Created by zhouwenqi on 2019/1/25.
 */
@Configuration
public class MongodbConfig {

    @Bean
    public  MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context, BeanFactory beanFactory) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver,context);
        try{
            converter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
        }catch (NoSuchBeanDefinitionException e){
            e.printStackTrace();
        }
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }

}
