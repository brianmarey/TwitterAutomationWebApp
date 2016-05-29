package com.careydevelopment.twitterautomation.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.propertiessupport.PropertiesFactory;
import com.careydevelopment.propertiessupport.PropertiesFile;

import twitter4j.ExtendedMediaEntity;
import twitter4j.MediaEntity;
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
	
	public static void main(String[] args) {

		
		try {
			Properties props = PropertiesFactory.getProperties(PropertiesFile.TWITTER_PROPERTIES);		
			String consumerKey=props.getProperty("brianmcarey.consumerKey");
			String consumerSecret=props.getProperty("brianmcarey.consumerSecret");
	    	
	    	ConfigurationBuilder builder = new ConfigurationBuilder();
	    	builder.setOAuthConsumerKey(consumerKey);
	    	builder.setOAuthConsumerSecret(consumerSecret);
	    	Configuration configuration = builder.build();
	    	TwitterFactory factory = new TwitterFactory(configuration);
	    	Twitter twitter = factory.getInstance();
			
			List<Status> statuses = new ArrayList<Status>();
			
			
			Trends trends = twitter.trends().getPlaceTrends(23424977);
			Trend[] trs = trends.getTrends();
			
			int count = 0;
			
			for (Trend tr : trs) {
				Query query = new Query(tr.getQuery()).count(COUNT_SIZE);
				QueryResult result = twitter.search(query);
				statuses.addAll(result.getTweets());
				count++;
				if(count>20) break;
				
				Thread.sleep(1000);
			}
			
			for (Status status : statuses) {
				if (status.getRetweetCount() > 500) {
					
					if (status.isRetweet()) {
						System.err.println("original id is " + status.getId());
						status = getOriginalStatus(status,twitter);
						System.err.println("now id is " + status.getId());
					}
					
					if (status.getMediaEntities().length > 0) {
						System.err.println(status.getId() + " " + status.getUser().getScreenName() + " " + status.getMediaEntities().length + " " + status.getExtendedMediaEntities().length);
						System.err.println("URL IS " + getUrl(status));
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
			}
			
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
	
	
	public static String getUrl(Status st) {
		String url= "https://twitter.com/" + st.getUser().getScreenName() + "/status/" + st.getId();
		return url;
	}
	
	public static Status getOriginalStatus(Status st,Twitter twitter) throws TwitterException {
		Status newStatus = st;
		
		if (st.getMediaEntities() != null && st.getMediaEntities().length == 1) {
			String url = st.getMediaEntities()[0].getExpandedURL();
			newStatus = getStatusFromUrl(st,url,twitter);
		}
		
		return newStatus;
	}
	
	
	public static Status getStatusFromUrl(Status st, String url, Twitter twitter) throws TwitterException {
		Status newStatus = st;
		long id = 0l;
		
		int photo = url.indexOf("/photo");
		if (photo > -1) {
			int status = url.lastIndexOf("status/",photo);
			if (status > -1) {
				String idS = url.substring(status + 7, photo);
				System.err.println("ID is " + idS);
				
				try {
					id = new Long(idS);
				} catch (Exception e) {
					
				}
			}
		}
		
		if (id == 0) {
			int video = url.indexOf("/photo");
			if (video > -1) {
				int status = url.lastIndexOf("status/",photo);
				if (status > -1) {
					String idS = url.substring(status + 7, photo);
					System.err.println("ID is " + idS);
					
					try {
						id = new Long(idS);
					} catch (Exception e) {
						
					}
				}
			}
		}
		
		if (id != 0) {
			long[] ids = new long[1];
			ids[0]=id;
			
			ResponseList<Status> stats = twitter.lookup(ids);
			newStatus = stats.get(0);
		}
		
		return newStatus;
	}
}
