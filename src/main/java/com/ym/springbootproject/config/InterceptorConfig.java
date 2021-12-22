package com.ym.springbootproject.config;

import com.ym.springbootproject.interceptor.InterfaceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author Meng
 * @Description: TODO 配置该拦截器
 * @date 2021/11/25 18:42
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截运营模块所有接口请求
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/excel/**");
    }

    @Bean
    public InterfaceInterceptor authenticationInterceptor() {
        return new InterfaceInterceptor();
    }
}
