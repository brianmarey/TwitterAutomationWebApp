package com.careydevelopment.configuration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.careydevelopment.twitterautomation.jpa.entity.ChiveImage;
import com.careydevelopment.twitterautomation.jpa.entity.RedditImage;
import com.careydevelopment.twitterautomation.jpa.repository.ChiveImageRepository;
import com.careydevelopment.twitterautomation.jpa.repository.RedditImageRepository;
import com.careydevelopment.twitterautomation.reader.ChiveReader;
import com.careydevelopment.twitterautomation.reader.RedditImageReader;

@Configuration
@EnableScheduling
public class ScheduleTasksConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleTasksConfig.class);
	
	@Autowired
	ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	RedditImageRepository redditImageRepository;


	@Autowired
	ChiveImageRepository chiveImageRepository;

	@Scheduled(fixedDelay=21600000)
	void getFunnyImagesFromReddit() {		
		taskExecutor.execute(new Thread(){
			public void run() {
			   LOGGER.info("Running reddit funny image collector");
			   
			   RedditImageReader ri = new RedditImageReader("https://reddit.com/r/funny.json?limit=75");
			   List<RedditImage> images = ri.getImageUrls();
			   for (RedditImage image : images) {
				   String imageUrl = image.getImageUrl();
				   
				   RedditImage foundImage = redditImageRepository.findByUrl(imageUrl);
				
				   if (foundImage == null) {
					   LOGGER.info("Adding " + image.getImageUrl() + " " + image.getCaption());
					   redditImageRepository.save(image);
				   } else {
					   LOGGER.info("Already have " + image.getImageUrl());
				   }
			   }
			}
		});
	}
	
	
	@Scheduled(fixedDelay=14400000)
	void getChiveImages() {
		taskExecutor.execute(new Thread(){
			public void run() {
			   LOGGER.info("Running chive image collector");
			   
				ChiveReader cr = new ChiveReader();
				List<String> urls = cr.getAllUrls();
				
				for (String s : urls) {
				   ChiveImage foundImage = chiveImageRepository.findByUrl(s);
				
				   if (foundImage == null) {
					   LOGGER.info("Adding " + s);
					   ChiveImage image = new ChiveImage();
					   image.setUrl(s);
					   chiveImageRepository.save(image);
				   } else {
					   LOGGER.info("Already have " + s);
				   }
			    }
			}
		});
	}
}
