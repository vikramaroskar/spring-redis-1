package com.symbo.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringRedis1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedis1Application.class, args);
	}

	@Bean
	public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
	  RedisTemplate<?, ?> template = new RedisTemplate<>();
	  template.setConnectionFactory(connectionFactory);

	  return template;
	}
	
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//	  return new BCryptPasswordEncoder();
//	}
}
