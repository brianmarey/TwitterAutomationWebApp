package com.careydevelopment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.careydevelopment.twitterautomation.service.TwitterService;

@Configuration
public class BeanConfig {

	@Bean
	public TwitterService twitterService() {
		return new TwitterService();
	}

}
