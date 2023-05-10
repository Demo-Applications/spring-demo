package demo.spring.service1.config;

import java.time.Duration;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

  @Value("${spring.redis.impl.client:jedis}")
  private String redisClient;

  @Value("${spring.redis.host}")
  private String redisHost;

  @Value("${spring.redis.port}")
  private int redisPort;

  @Value("${spring.redis.password}")
  private String redisPassword;

  @Value("${spring.redis.jedis.pool.max-active}")
  private int redisMaxActive;

  @Value("${spring.redis.jedis.pool.max-idle}")
  private int redisMaxIdle;

  @Value("${spring.redis.jedis.pool.min-idle}")
  private int redisMinIdle;

  @Value("${spring.redis.jedis.client.name}")
  private String redisClientname;

  /** Alternative to using <code>@value</code> annotation */
  @Autowired private RedisProperties redisProperties;

  private static int CONNECTION_TIMEOUT = 60;

  @Bean
  @Autowired
  public RedisTemplate<String, Object> redisTemplate(ApplicationContext context) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

    RedisConnectionFactory redisConnectionFactory =
        (RedisConnectionFactory) context.getBean(redisClient);
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());

    return redisTemplate;
  }

  /**
   * Configures Redis client implemented with jedis, connection pool
   *
   * @return
   */
  @Bean("jedis")
  public RedisConnectionFactory redisConnectionFactoryWithJedis() {
    RedisStandaloneConfiguration redisConfig =
        new RedisStandaloneConfiguration(redisHost, redisPort);
    redisConfig.setPassword(redisPassword);

    JedisPoolConfig poolConfig = setupJedisPoolConfig();
    JedisClientConfiguration jedisClientConfig =
        JedisClientConfiguration.builder()
            .usePooling()
            .poolConfig(poolConfig)
            .and()
            .clientName(redisClientname)
            .connectTimeout(Duration.ofSeconds(60))
            .build();
    JedisConnectionFactory jedisConnectionFactory =
        new JedisConnectionFactory(redisConfig, jedisClientConfig);
    return jedisConnectionFactory;
  }

  /**
   * Configures Redis client implemented with lettuce, connection pool
   *
   * @return
   */
  @Bean("lettuce")
  public RedisConnectionFactory redisConnectionFactoryWithLettuce() {
    RedisStandaloneConfiguration redisConfig =
        new RedisStandaloneConfiguration(redisHost, redisPort);
    redisConfig.setPassword(redisPassword);

    GenericObjectPoolConfig poolConfig = setupLettucePoolConfig();

    LettuceClientConfiguration lettuceClientConfiguration =
        LettucePoolingClientConfiguration.builder()
            .poolConfig(poolConfig)
            .clientName(redisClientname)
            .commandTimeout(Duration.ofSeconds(CONNECTION_TIMEOUT))
            .build();

    LettuceConnectionFactory lettuceConnectionFactory =
        new LettuceConnectionFactory(redisConfig, lettuceClientConfiguration);
    return lettuceConnectionFactory;
  }

  private JedisPoolConfig setupJedisPoolConfig() {
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxTotal(redisMaxActive);
    poolConfig.setMaxIdle(redisMaxIdle);
    poolConfig.setMinIdle(redisMinIdle);
    poolConfig.setTestOnBorrow(false);
    poolConfig.setTestOnReturn(false);
    poolConfig.setTestWhileIdle(false);
    poolConfig.setMinEvictableIdleTime(Duration.ofMinutes(30));
    poolConfig.setTimeBetweenEvictionRuns(Duration.ofMinutes(30));
    poolConfig.setNumTestsPerEvictionRun(3);
    poolConfig.setBlockWhenExhausted(true);
    poolConfig.setFairness(false);
    poolConfig.setLifo(true);
    return poolConfig;
  }

  public GenericObjectPoolConfig setupLettucePoolConfig() {
    GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
    poolConfig.setMaxTotal(redisMaxActive);
    poolConfig.setMaxWait(Duration.ofMillis(1000));
    poolConfig.setMaxIdle(redisMaxIdle);
    poolConfig.setMinIdle(redisMinIdle);
    poolConfig.setTestOnBorrow(false);
    poolConfig.setTestOnReturn(false);
    poolConfig.setTestWhileIdle(false);
    poolConfig.setMinEvictableIdleTime(Duration.ofMinutes(30));
    poolConfig.setTimeBetweenEvictionRuns(Duration.ofMinutes(30));
    poolConfig.setNumTestsPerEvictionRun(3);
    poolConfig.setBlockWhenExhausted(true);
    poolConfig.setFairness(false);
    poolConfig.setLifo(true);
    return poolConfig;
  }
}
