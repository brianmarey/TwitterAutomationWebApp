package com.careydevelopment.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.careydevelopment.twitterautomation.jpa.repository.ChiveImageRepository;
import com.careydevelopment.twitterautomation.jpa.repository.RedditImageRepository;
import com.careydevelopment.twitterautomation.jpa.repository.ViralContentRepository;
import com.careydevelopment.twitterautomation.jpa.repository.ViralFacebookRepository;
import com.careydevelopment.twitterautomation.jpa.repository.ViralTweetRepository;
import com.careydevelopment.twitterautomation.service.impl.FacebookService;
import com.careydevelopment.twitterautomation.service.impl.TwitterService;

@Configuration
@EnableScheduling
public class ScheduleTasksConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleTasksConfig.class);
	
	@Autowired
	ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	RedditImageRepository redditImageRepository;

	@Autowired
	ViralContentRepository viralContentRepository;

	@Autowired
	ChiveImageRepository chiveImageRepository;
	
	@Autowired
	ViralTweetRepository viralTweetRepository;
	
	@Autowired
	TwitterService twitterService;
	
	@Autowired
	FacebookService facebookService;
	
	@Autowired
	ViralFacebookRepository viralFacebookRepository;

/*	@Scheduled(fixedDelay=21600000)
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
	
	
	@Scheduled(fixedDelay=3600000)
	void getViralContent() {
		taskExecutor.execute(new Thread(){
			public void run() {
			   LOGGER.info("Running viral content collector");
			   
			   List<Blog> blogs = BlogHelper.getBlogs();
			   for (Blog bl : blogs) {
				   BlogReader reader = new BlogReader(bl);
				   List<ViralContent> vs = reader.getStories();
				   
				   for (ViralContent v : vs) {
					   String url = v.getUrl();
					   
					   ViralContent foundOne = viralContentRepository.findByUrl(url);
					   if (foundOne == null) {
						   LOGGER.info("Adding " + v.getHeadline());
						   try {
							   viralContentRepository.save(v);
						   } catch (Exception e) {
							   LOGGER.error("Problem saving " + v.getHeadline(),e);
						   }
					   } else {
						   LOGGER.info("Already have " + v.getHeadline());
						   foundOne.setShareCount(v.getShareCount());
						   viralContentRepository.save(foundOne);
					   }
				   }
			   }
			}
		});
	}
	
	
	@Scheduled(fixedDelay=7200000)
	void getViralTweets() {
		LOGGER.info("Launhing");
		ViralTweetProcessor proc = new ViralTweetProcessor(twitterService,viralTweetRepository);
		LOGGER.info("it is " + proc);
		taskExecutor.execute(proc);		
	}
	
	
	@Scheduled(fixedDelay=7200000)
	void getViralFacebook() {
		LOGGER.info("Launhing");
		ViralFacebookProcessor proc = new ViralFacebookProcessor(facebookService,viralFacebookRepository);
		LOGGER.info("it is " + proc);
		taskExecutor.execute(proc);		
	}*/
}
