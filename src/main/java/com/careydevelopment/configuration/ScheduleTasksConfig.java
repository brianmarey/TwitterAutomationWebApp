package com.careydevelopment.configuration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.careydevelopment.twitterautomation.jpa.entity.RedditImage;
import com.careydevelopment.twitterautomation.jpa.repository.RedditImageRepository;
import com.careydevelopment.twitterautomation.reader.RedditImageReader;

@Configuration
@EnableScheduling
public class ScheduleTasksConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleTasksConfig.class);
	
	@Autowired
	RedditImageRepository redditImageRepository;

	
	/*@Scheduled(fixedDelay=21600000)
	public void doSomething() {
	   LOGGER.info("Running reddit funny image collector");
	   
	   RedditImageReader ri = new RedditImageReader("https://reddit.com/r/funny.json?limit=75");
	   List<RedditImage> images = ri.getImageUrls();
	   for (RedditImage image : images) {
		   String imageUrl = image.getImageUrl();
		   
		   RedditImage foundImage = redditImageRepository.findByUrl(imageUrl);
		
		   if (foundImage == null) {
			   //LOGGER.info("Adding " + image.getImageUrl() + " " + image.getCaption());
			   redditImageRepository.save(image);
		   } else {
			   //LOGGER.info("Already have " + image.getImageUrl());
		   }
	   }
	}*/


}
