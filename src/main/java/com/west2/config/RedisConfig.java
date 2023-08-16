package com.west2.config;

import com.west2.common.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author 1
 * Redis配置类
 */
@Configuration
public class RedisConfig {


    /**
     * 用于压制警告信息的注解，告诉编译器忽略指定类型的警告
     */
    @Bean
    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        // 设置 RedisTemplate 的连接工厂，用于连接 Redis 数据库
        template.setConnectionFactory(connectionFactory);
        // 使用 FastJsonRedisSerializer 来序列化和反序列化 Redis 的 value 值
        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        // 初始化 RedisTemplate
        template.afterPropertiesSet();
        return template;
    }

}
