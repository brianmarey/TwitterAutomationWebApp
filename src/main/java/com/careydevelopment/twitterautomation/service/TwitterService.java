package com.careydevelopment.twitterautomation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.propertiessupport.PropertiesFactory;
import com.careydevelopment.propertiessupport.PropertiesFactoryException;
import com.careydevelopment.propertiessupport.PropertiesFile;
import com.careydevelopment.twitterautomation.model.FriendshipLightweight;
import com.careydevelopment.twitterautomation.util.Constants;

import twitter4j.Friendship;
import twitter4j.PagableResponseList;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterService.class);
	
	private static final int MAX_PER_PAGE = 200;
	private static final int DEFAULT_SLEEP_TIME = 3000;
	private static final int COUNT_SIZE = 100;
	
	public void getFollowers(Twitter twitter, List<User> users, String screenName, int iterations) throws TwitterException, InterruptedException {
		long cursor = -1;
		
		for (int i=0;i<iterations;i++) {
			PagableResponseList<User> tweeps = twitter.getFollowersList(screenName, cursor, MAX_PER_PAGE);
			
			for (User user : tweeps) {
				users.add(user);
			}

			cursor = tweeps.getNextCursor();
			if (cursor == 0) break;
			Thread.sleep(DEFAULT_SLEEP_TIME);
		}
	}
	
	
	public List<String> getFriendshipsFromUsers(List<User> users, Twitter twitter) throws TwitterException, InterruptedException {
		List<String> finalNames = new ArrayList<String>();		
		
		int start = 0;
		int length = 100;
		while (finalNames.size() < Constants.MAX_FOLLOW_SIZE && start < users.size() - (length + 1)) {
			long[] subIds = new long[length];
			int index = 0;
			
			for (int i=start;i<start+length;i++) {
				User user = users.get(i);
				long id = user.getId();
				subIds[index] = id;
				index++;
			}
			
			ResponseList<Friendship> friendships = twitter.lookupFriendships(subIds);
			
			for (Friendship friendship : friendships) {
				//LOGGER.info(friendship.getScreenName() + " " + friendship.isFollowedBy() + " " + friendship.isFollowing());
				if (!friendship.isFollowing()) {
					//LOGGER.info("Now adding " + friendship.getScreenName());
					finalNames.add(friendship.getScreenName());
				}
			}
			
			start += length;
			Thread.sleep(3000);
		}

		return finalNames;
	}

	
	public List<FriendshipLightweight> getFriendshipsByKeyword(Twitter twitter, String[] keys) throws TwitterException {

		List<FriendshipLightweight> ships = new ArrayList<FriendshipLightweight>();

		for (int i=0;i<keys.length;i++) {
			String key = keys[i];
			//LOGGER.info("in here 3 " + key);
			//if (!key.startsWith("#")) key = "#" + key;
			
			if (key != null && key.trim().length() > 2) {		
				//LOGGER.info("Now searching for " + key);
				
				Query query = new Query(key).count(COUNT_SIZE);
				QueryResult result = twitter.search(query);
						    
				String[] returnedDudes = getReturnedDudes(result);		
				
				//if (returnedDudes != null) LOGGER.info("Size of returned dudes is " + returnedDudes.length);
				
				/*for (String s : returnedDudes) {
					LOGGER.info(s);
				}*/

				ResponseList<Friendship> friendships = twitter.lookupFriendships(returnedDudes);
				
				ships.addAll(getLightweights(friendships));
			}
		}
		
		//LOGGER.info("getti gout " + ships.size());
		return ships;
	}
	
	
    /**
     * Translates heavyweight Friendship objets from Twitter4j into lightweight objects
     * Easier for JSON
     */
    private List<FriendshipLightweight> getLightweights(ResponseList<Friendship> friendships) {
    	List<FriendshipLightweight> ships = new ArrayList<FriendshipLightweight>();
    	
    	for (Friendship friendship : friendships) {
    		
    		//be sure to skip the people who are DNF
    		//if (!dnfIds.contains(friendship.getId())) {
    			if (!friendship.isFollowedBy()) {
		    		FriendshipLightweight light = new FriendshipLightweight();
		    		light.setFollowing(friendship.isFollowing());
		    		light.setId(friendship.getId());
		    		light.setScreenName(friendship.getScreenName());
		    		
		    		ships.add(light);
    			} else {
    				//LOGGER.info("Skipping " + friendship.getScreenName() + " because that person is already following you.");
    			}
    		/*} else {
    			LOGGER.info("Skipping " + friendship.getScreenName() + " because that user is DNF");
    		}*/
    	}
    	
    	return ships;
    }

    
    
	/**
	 * Returns candidates for following
	 */
	private String[] getReturnedDudes(QueryResult result) throws TwitterException {
		String[] returnedDudes = new String[result.getCount()];
		//long[] blocks = twitter.getBlocksIDs().getIDs();

		int i = 0;
		
		for (Status status : result.getTweets()) {
			/*User thisUser = status.getUser();
	    	boolean isBlocked = false;
	    	for (long l : blocks) {
	    		if (l == thisUser.getId()) {
	    			isBlocked = true;
	    		}
	    	}
			
	    	if (!isBlocked) {*/
	    		returnedDudes[i] = status.getUser().getScreenName();
			    i++;
			//}
		}
		
		return returnedDudes;
	}
	
	
    public Twitter getTwitter() {
    	Twitter twitter = null;
    	
    	try {
			Properties props = PropertiesFactory.getProperties(PropertiesFile.TWITTER_PROPERTIES);		
			String consumerKey=props.getProperty("brianmcarey.consumerKey");
			String consumerSecret=props.getProperty("brianmcarey.consumerSecret");
	    	
	    	ConfigurationBuilder builder = new ConfigurationBuilder();
	    	builder.setOAuthConsumerKey(consumerKey);
	    	builder.setOAuthConsumerSecret(consumerSecret);
	    	Configuration configuration = builder.build();
	    	TwitterFactory factory = new TwitterFactory(configuration);
	    	twitter = factory.getInstance();
    	} catch (PropertiesFactoryException pe) {
    		LOGGER.error("Problem reading propertiesfile!",pe);
    		throw new RuntimeException ("Problem reading properties file!");
    	}
    	
    	return twitter;
    }
    
    
    public Twitter getTwitterWithToken(String accessToken, String accessTokenSecret) {
    	Twitter twitter = getTwitter();
    	
    	AccessToken token = new AccessToken(accessToken, accessTokenSecret);
    	twitter.setOAuthAccessToken(token);
    	
    	return twitter;
    }
}