package com.yupi.yupao.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson 配置
 *
 */
@Configuration
@EnableConfigurationProperties(RedissonConfig.class)  // 添加这个注解
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfig {

    private String host = "redis";  // 设置默认值
    private Integer port = 6379;     // 设置默认值
    private Integer database = 1;    // 设置默认值

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        // 添加空值检查
        String redisAddress = String.format("redis://%s:%s",
                host != null ? host : "redis",
                port != null ? port : 6379);
        config.useSingleServer()
                .setAddress(redisAddress)
                .setDatabase(database != null ? database : 1);
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
