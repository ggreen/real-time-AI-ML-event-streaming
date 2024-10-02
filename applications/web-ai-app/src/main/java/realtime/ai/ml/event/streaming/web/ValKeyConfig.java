package realtime.ai.ml.event.streaming.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class ValKeyConfig {

    @Value("${app.cache.ttl.seconds:10}")
    private long ttlSeconds;


    @Bean
    public RedisSerializer<String> serializer()
    {
        return  (RedisSerializer) new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory, RedisSerializer<String> serializer) {

        RedisSerializationContext<String, String> redisSerializationContext = RedisSerializationContext.fromSerializer(serializer);

        var cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(ttlSeconds))
                .serializeKeysWith(redisSerializationContext.getKeySerializationPair())
                .serializeValuesWith(redisSerializationContext.getValueSerializationPair())
                .disableCachingNullValues();

        return  RedisCacheManager.builder(connectionFactory).cacheDefaults(cacheConfiguration)
                .build();
    }

}
