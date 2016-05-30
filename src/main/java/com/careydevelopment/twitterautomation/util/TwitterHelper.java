package com.careydevelopment.twitterautomation.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.propertiessupport.PropertiesFactory;
import com.careydevelopment.propertiessupport.PropertiesFile;

import twitter4j.OEmbed;
import twitter4j.OEmbedRequest;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterHelper.class);
	
	private static final int MAX_PER_PAGE = 200;
	private static final int DEFAULT_SLEEP_TIME = 3000;
	private static final int COUNT_SIZE = 100;
	private static final int VIRAL_RETWEET_THRESHOLD = 500;
	private static final int MAX_WIDTH = 400;
	
	public static void main(String[] args) {

		
		try {
			Properties props = PropertiesFactory.getProperties(PropertiesFile.TWITTER_PROPERTIES);		
			String consumerKey=props.getProperty("brianmcarey.consumerKey");
			String consumerSecret=props.getProperty("brianmcarey.consumerSecret");
			String accessToken=props.getProperty("brianmcarey.accessToken");
			String accessSecret=props.getProperty("brianmcarey.accessSecret");
	    	
	    	ConfigurationBuilder builder = new ConfigurationBuilder();
	    	builder.setOAuthConsumerKey(consumerKey);
	    	builder.setOAuthConsumerSecret(consumerSecret);
	    	builder.setOAuthAccessToken(accessToken);
	    	builder.setOAuthAccessTokenSecret(accessSecret);
	    	Configuration configuration = builder.build();
	    	TwitterFactory factory = new TwitterFactory(configuration);
	    	Twitter twitter = factory.getInstance();
			
			/*List<Status> statuses = getPopularTweetsFromTrendingTopics(twitter);
			LOGGER.info("Size is " + statuses.size());
			for (Status status : statuses) {
				LOGGER.info("" + status.getId()+ " " + status.getText() + status.getLang());
			}
			
			List<String> embeds = getEmbedCodeForStatuses(statuses,twitter);
			
			for (String embed: embeds) {
				LOGGER.info(embed);
				LOGGER.info("\n\n\n");
			}*/
			
			/*
			
			long[] ids = new long[1];
			//ids[0] = 735817451829526529l;
			ids[0]=736785301079629825l;
			
			ResponseList<Status> stats = twitter.lookup(ids);
			Status st = stats.get(0);
			System.err.println(st.getText());
			//System.err.println(st.getURLEntities()[0].getExpandedURL());
			System.err.println(st.getExtendedMediaEntities().length);
			System.err.println(st.getMediaEntities().length);
			System.err.println(st.getExtendedMediaEntities()[0].getURL());
			String url= "https://twitter.com/" + st.getUser().getScreenName() + "/status/" + st.getId();
			OEmbedRequest req = new OEmbedRequest(st.getId(),url);
			req.HideMedia(false);
			req.setHideMedia(false);
			req.MaxWidth(400);
			OEmbed oe = twitter.getOEmbed(req);
			System.err.println(oe.getHtml());*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
