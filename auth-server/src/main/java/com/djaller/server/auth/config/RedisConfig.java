package com.djaller.server.auth.config;

import com.djaller.server.common.tenant.model.TenantContext;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.context.annotation.RequestScope;

@Profile("redis")
@EnableRedisHttpSession
@EnableCaching
@Configuration
public class RedisConfig implements CachingConfigurer {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    @Override
    public RedisCacheManager cacheManager() {
        return RedisCacheManager.create(redisConnectionFactory());
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (o, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName());
            sb.append(method.getName());
            for (Object param : params) {
                sb.append(param.toString());
            }

            String tenant = TenantContext.getTenantSafe();
            return "%s::%s".formatted(tenant, sb);
        };
    }

    @RequestScope
    // @Bean(name = "sessionRepository")
    public RedisIndexedSessionRepository sessionRepository(RedisTemplate<Object, Object> redisTemplate) {
        var repo = new RedisIndexedSessionRepository(redisTemplate);
        var tenant = TenantContext.getTenantSafe();
        repo.setRedisKeyNamespace("%s:spring:session".formatted(tenant));
        return repo;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}
