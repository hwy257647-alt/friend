package com.yupi.yupao.config;
 
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 自定义 Swagger 接口文档的配置（基于 OpenAPI 3）
 *
 */
@Configuration
@Profile({"dev", "test"})
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("鱼皮用户中心")
                        .description("鱼皮用户中心接口文档")
                        .version("1.0")
                        .contact(new Contact()
                                .name("yupi")
                                .url("https://github.com/liyupi")
                                .email("xxx@qq.com")));
    }
}
