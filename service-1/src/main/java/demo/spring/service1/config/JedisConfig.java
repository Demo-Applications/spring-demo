package demo.spring.service1.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfig {

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

	public RedisConnectionFactory redisConnectionFactory() {

		JedisPoolConfig poolConfig = setupRedisPoolConfig();
		RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
		redisConfig.setPassword(redisPassword);
		JedisClientConfiguration jedisClientConfig = JedisClientConfiguration.builder().usePooling()
				.poolConfig(poolConfig).and().clientName(redisClientname).connectTimeout(Duration.ofSeconds(60))
				.build();
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisConfig, jedisClientConfig);
		return jedisConnectionFactory;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());

		return redisTemplate;
	}

	private JedisPoolConfig setupRedisPoolConfig() {
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
}
