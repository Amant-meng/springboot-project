package com.ym.springbootproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description TODO 项目启动类
 * @Author yangmeng
 * @Date  2021/9/17
 **/
@SpringBootApplication
@EnableScheduling
public class SpringbootProjectApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootProjectApplication.class, args);
    }

    //打成war包时需要重写里面的 configure方法
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringbootProjectApplication.class);
    }

}
