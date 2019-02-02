package com.zhouwenqi.apihub.configure;


import com.zhouwenqi.apihub.interceptor.AccessInterceptor;
import com.zhouwenqi.apihub.interceptor.OriginInterceptor;
import com.zhouwenqi.apihub.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册拦截器配置
 * Created by zhouwenqi on 2019/1/24.
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    OriginInterceptor originInterceptor(){
        return new OriginInterceptor();
    }
    @Bean
    TokenInterceptor tokenInterceptor(){ return new TokenInterceptor();}
    @Bean
    AccessInterceptor accessInterceptor(){ return new AccessInterceptor();}
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(originInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(tokenInterceptor()).addPathPatterns("/user/**").addPathPatterns("/project/**");
        registry.addInterceptor(accessInterceptor()).addPathPatterns("/project/**");
    }
}
