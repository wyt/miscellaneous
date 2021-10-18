package git.wyt.redis.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * @author wangyongtao
 * @date 2020/6/22
 */
@Configuration
public class RedisCnfig {

  @Bean({"redisTemplate"})
  public RedisTemplate<Serializable, byte[]> redisTemplateObject(RedisConnectionFactory factory) {
    RedisTemplate<Serializable, byte[]> template = new RedisTemplate();
    template.setConnectionFactory(factory);

    RedisSerializer stringSerializer = new StringRedisSerializer();
    template.setKeySerializer(stringSerializer);
    return template;
  }

  @Bean(name = "strRedisTemplate")
  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
    redisTemplate.setKeySerializer(stringSerializer);
    redisTemplate.setValueSerializer(stringSerializer);
    redisTemplate.setHashKeySerializer(stringSerializer);
    redisTemplate.setHashValueSerializer(stringSerializer);
    redisTemplate.setConnectionFactory(factory);
    return redisTemplate;
  }
}
