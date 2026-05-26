package com.yupi.yupao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 *
 */
@Configuration
public class WebMvcConfg implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://192.168.100.128:8080",
                        "http://192.168.100.128:90",
                        "http://friend:8080",
                        "http://127.0.0.1:8083",
                        "http://localhost:90",
                        "http://localhost:8080",
                        "http://localhost:3000")
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(3600);
    }
}
