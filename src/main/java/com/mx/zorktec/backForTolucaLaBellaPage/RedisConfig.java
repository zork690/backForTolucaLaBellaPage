package com.mx.zorktec.backForTolucaLaBellaPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;



//@Configuration
public class RedisConfig {
	
	private static final  Logger LOG = LogManager.getLogger(RedisConfig.class);

	/*@Value("${spring.redis.host}")
	private String redisHost;
	
	@Value("${spring.redis.port}")
	private int redisPort;
	
	@Value("${spring.redis.pass}")
	private String redisPassword;
	
	//@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		LOG.info("REDIS HOST: "+this.redisHost);
		LOG.info("REDIS port: "+this.redisPort);
		return new LettuceConnectionFactory(new RedisStandaloneConfiguration(this.redisHost, this.redisPort));
	}
	
	
	@Bean
	@SuppressWarnings("deprecation")
	public RedisConnectionFactory redisConnectionFactoryR() {
		final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(20);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        
        final JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
        connectionFactory.setUsePool(true);
        LOG.info("-Data YML en redisConfig: "+this.redisHost +"-"+this.redisPort);
        connectionFactory.setPassword(this.redisPassword);
        connectionFactory.setHostName(this.redisHost);
        connectionFactory.setPort(this.redisPort);
        
        return connectionFactory;
		
	}
	
	@Bean
	public RedisTemplate<?,?> redisTemplate() {
		final RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactoryR());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		LOG.info("Redis template " + redisTemplate);
		return redisTemplate;
		
	} */
}
