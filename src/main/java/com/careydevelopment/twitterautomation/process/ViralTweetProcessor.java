package com.careydevelopment.twitterautomation.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.careydevelopment.twitterautomation.jpa.entity.ViralTweet;
import com.careydevelopment.twitterautomation.jpa.repository.ViralTweetRepository;
import com.careydevelopment.twitterautomation.service.TwitterService;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class ViralTweetProcessor extends Thread {
	public static final Logger LOGGER = LoggerFactory.getLogger(ViralTweetProcessor.class);
	
	ViralTweetRepository viralTweetRepository;
	TwitterService twitterService;
	
	private Twitter twitter;
	
	public ViralTweetProcessor(TwitterService twitterService, ViralTweetRepository viralTweetRepository) {
		LOGGER.info("Instantiating");
		this.twitterService = twitterService;
		this.viralTweetRepository = viralTweetRepository;
	}

	
	@Override
	public void run() {
	   LOGGER.info("Running viral tweet collector");
	   twitter = twitterService.getFullyCredentialedTwitter();
	   
	   List<Status> statuses = getStatuses();
	   
	   for (Status status : statuses) {
		   LOGGER.info("Looking at tweet " + status.getId() + " " + status.getText() + " " + status.getExtendedMediaEntities().length + " " + status.getMediaEntities().length);
		   
		   if (status.getExtendedMediaEntities().length == 1) {
			   LOGGER.info("type is " + status.getExtendedMediaEntities()[0].getType());
			   
			   if (status.getExtendedMediaEntities()[0].getType().equals("video")) {
				   handleTweet("video",status);
			   } else if (status.getExtendedMediaEntities()[0].getType().equals("photo")) {
				   handleTweet("photo",status);
			   }
		   } else if (status.getMediaEntities().length == 1) {
			   handleTweet("photo",status);
		   }
	   }			   
	}	
	
	
	private void handleTweet(String category, Status status) {
		   ViralTweet foundTweet = viralTweetRepository.findByTweetId(status.getId());
		   
		   if (foundTweet == null) {
			   addTweet(category,status);
		   } else {
			   updateTweet(foundTweet,status);
		   }
	}

	
	private void updateTweet(ViralTweet foundTweet, Status status) {
	   LOGGER.info("Already have it");
	   foundTweet.setRetweets(status.getRetweetCount());
	   viralTweetRepository.save(foundTweet);
	}
	
	
	private void addTweet(String category, Status status) {
		LOGGER.info("Adding it as " + category);
		
		ViralTweet newTweet = new ViralTweet();
		newTweet.setCategory(category);
		newTweet.setDateSeen(new Date());
		newTweet.setRetweets(status.getRetweetCount());
		newTweet.setTweetId(status.getId());
		   
		try {
		   String html = twitterService.getEmbedCodeForStatus(status, twitter);
		   newTweet.setHtml(html);
		   viralTweetRepository.save(newTweet);
		   Thread.sleep(10000);
		} catch (Exception e) {
			   LOGGER.error("Problem saving tweet!",e);
		}
	}
	
	
	private List<Status> getStatuses() {
		List<Status> statuses = new ArrayList<Status>();
		   
		try {
		   statuses = twitterService.getPopularTweetsFromTrendingTopics(twitter);
		} catch(TwitterException te) {
		   LOGGER.error("Problem getting statuses!",te);
		}
		
		return statuses;
	}
	
}
