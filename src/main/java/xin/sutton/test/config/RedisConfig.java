package xin.sutton.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis配置
 *
 * @author codingZhengsz
 * @since 2018-10-30 17:20
 **/
@Configuration
public class RedisConfig {

    @Autowired
    Environment env;

    @Bean
    public JedisPoolConfig poolConfig(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(Integer.parseInt(env.getProperty("redis.maxIdle")));
        poolConfig.setMaxWaitMillis(Integer.parseInt(env.getProperty("redis.maxWait")));
        poolConfig.setTestOnBorrow(Boolean.parseBoolean(env.getProperty("redis.testOnBorrow")));
        return poolConfig;
    }

    @Bean
    public JedisConnectionFactory connectionFactory(){
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setPoolConfig(poolConfig());
        connectionFactory.setPort(Integer.parseInt(env.getProperty("redis.port")));
        connectionFactory.setHostName(env.getProperty("redis.host"));
        connectionFactory.setTimeout(Integer.parseInt(env.getProperty("redis.timeout")));
        return connectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }



}
