package com.ym.springbootproject.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类
 * @Author yangmeng
 * @date 2021/6/25
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig{

    @Bean
    public Docket getDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApi())
                //是否开启 (true 开启  false隐藏。生产环境建议隐藏)
                //.enable(false)
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                //.apis(RequestHandlerSelectors.withClassAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("com.ym.springbootproject.moudle"))
                //指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo getApi() {

        return new ApiInfoBuilder()
            //设置文档标题(API名称)
            .title("SpringBoot中使用Swagger2接口规范")
            //文档描述
            .description("接口说明")
            //版本号
            .version("版本号：V1.0.0")
            .termsOfServiceUrl("537048@163.com")
            // 作者信息
            .contact(new Contact("YangMeng", "", ""))
            .license("版权所有 ©小锰哥")
            .build();
    }
}
