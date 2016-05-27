package com.careydevelopment.twitterautomation.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.ExtendedMediaEntity;
import twitter4j.Friendship;
import twitter4j.MediaEntity;
import twitter4j.PagableResponseList;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterHelper.class);
	
	private static final int MAX_PER_PAGE = 200;
	private static final int DEFAULT_SLEEP_TIME = 3000;
	private static final int COUNT_SIZE = 100;
	
	public static void main(String[] args) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("hP24s2NeWKEaWDZhHxsUQ")
		  .setOAuthConsumerSecret("ZdwkYshYIUAFi2BYcGMigx6gVqE6mExJWYXkAZrU4")
		  .setOAuthAccessToken("18305815-VblAe80R1gN67W4taWLMFUaytvFIGkGsSvpYmmcy5")
		  .setOAuthAccessTokenSecret("fZuoUdqVQsiA9NfC7jR3E8bDM2CkZnzNPlPgvjlNDSnLE");
		
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		List<Status> statuses = new ArrayList<Status>();
		
		try {
			/*Trends trends = twitter.trends().getPlaceTrends(23424977);
			Trend[] trs = trends.getTrends();
			
			int count = 0;
			
			for (Trend tr : trs) {
				Query query = new Query(tr.getQuery()).count(COUNT_SIZE);
				QueryResult result = twitter.search(query);
				statuses.addAll(result.getTweets());
				count++;
				if(count>6) break;
				
				Thread.sleep(5000);
			}
			
			for (Status status : statuses) {
				if (status.getRetweetCount() > 500) {
					if (status.getMediaEntities().length > 0) {
						System.err.println(status.getId() + " " + status.getUser().getScreenName() + " " + status.getMediaEntities().length);
						
						for (MediaEntity me : status.getMediaEntities()) {
							System.err.println(me.getURL() + " " + me.getExpandedURL() + " " + me.getMediaURL() + " " + me.getType());
						}
					}
					
					if (status.getExtendedMediaEntities().length > 0) {
						System.err.println(status.getId() + " " + status.getUser().getScreenName() + " " + status.getExtendedMediaEntities().length);
						
						for (ExtendedMediaEntity me : status.getExtendedMediaEntities()) {
							System.err.println(me.getURL() + " " + me.getURL() + " " + me.getMediaURL() + " " + me.getType());
						}
					}
					
				}
			}*/
			
			long[] ids = new long[1];
			//ids[0] = 735817451829526529l;
			ids[0]=735850589263794176l;
			
			ResponseList<Status> stats = twitter.lookup(ids);
			Status st = stats.get(0);
			System.err.println(st.getText());
			System.err.println(st.getURLEntities()[0].getExpandedURL());
			//System.err.println(st.getExtendedMediaEntities().length);
			//System.err.println(st.getMediaEntities().length);
			//System.err.println(st.getExtendedMediaEntities()[0].getURL());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
