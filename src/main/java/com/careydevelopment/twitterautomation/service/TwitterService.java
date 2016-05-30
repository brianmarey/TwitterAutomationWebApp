package com.careydevelopment.twitterautomation.service;

import java.util.ArrayList;
import java.util.Calendar;
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
import twitter4j.OEmbed;
import twitter4j.OEmbedRequest;
import twitter4j.OEmbedRequest.Align;
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
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterService.class);
	
	private static final int MAX_PER_PAGE = 200;
	private static final int DEFAULT_SLEEP_TIME = 3000;
	private static final int COUNT_SIZE = 100;
	private static final int VIRAL_RETWEET_THRESHOLD = 500;
	private static final int MAX_WIDTH = 400;
	
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
    
    
	public String getUrl(Status st) {
		String url= "https://twitter.com/" + st.getUser().getScreenName() + "/status/" + st.getId();
		return url;
	}
	
	public Status getOriginalStatus(Status st,Twitter twitter) throws TwitterException {
		Status newStatus = st;
		
		if (st.getMediaEntities() != null && st.getMediaEntities().length == 1) {
			String url = st.getMediaEntities()[0].getExpandedURL();
			newStatus = getStatusFromUrl(st,url,twitter);
		}
		
		return newStatus;
	}
	
	
	public Status getStatusFromUrl(Status st, String url, Twitter twitter) throws TwitterException {
		Status newStatus = st;
		long id = 0l;
		
		int photo = url.indexOf("/photo");
		if (photo > -1) {
			int status = url.lastIndexOf("status/",photo);
			if (status > -1) {
				String idS = url.substring(status + 7, photo);
				
				try {
					id = new Long(idS);
				} catch (Exception e) {
					
				}
			}
		}
		
		if (id == 0) {
			int video = url.indexOf("/video");
			if (video > -1) {
				int status = url.lastIndexOf("status/",video);
				if (status > -1) {
					String idS = url.substring(status + 7, video);
					
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
			if (stats != null && stats.size() == 1) newStatus = stats.get(0);
			
			try {
				Thread.sleep(10000);
			} catch (Exception e) {}
		}
		
		return newStatus;
	}

	
	public List<String> getEmbedCodeForStatuses(List<Status> statuses, Twitter twitter) throws TwitterException {
		List<String> embeds = new ArrayList<String>();
		
		for (Status status : statuses) {
			String embed = getEmbedCodeForStatus(status,twitter);
			embeds.add(embed);			
			//LOGGER.info(oe.getHtml());
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ie) {
			}
		}
		
		return embeds;
	}


	public String getEmbedCodeForStatus(Status status, Twitter twitter) throws TwitterException {
		//LOGGER.info("Getting embed ode for " + status.getId() + " " + status.getText());
		String url= getUrl(status); 
		OEmbedRequest req = getOEmbedRequest(status.getId(),url); 
		OEmbed oe = twitter.getOEmbed(req);
		String html = oe.getHtml();
		
		return html;
	}
	
	
	public OEmbedRequest getOEmbedRequest(long id, String url) {
		OEmbedRequest req = new OEmbedRequest(id,url);
		req.HideMedia(false);
		req.setHideMedia(false);
		req.MaxWidth(MAX_WIDTH);
		req.omitScript(true);
		req.setAlign(Align.CENTER);
		
		return req;
	}
	
	
	public boolean isOriginal(List<Status> statuses, Status status) {
		boolean isOriginal = true;
		
		for (Status st : statuses) {
			//LOGGER.info("omparing " + st.getId() + " to " + status.getId());
			if (st.getId() == status.getId()) {
				//LOGGER.info("They're equal");
				isOriginal = false;
				break;
			}
		}
		
		return isOriginal;
	}
	
	
	public List<Status> getPopularTweetsFromTrendingTopics(Twitter twitter) throws TwitterException {
		Trend[] trs = getTrends(twitter); 
		List<Status> statuses = getStatusesFromTrends(twitter,trs);
		List<Status> viralStatuses = new ArrayList<Status>();
		
		for (Status status : statuses) {
			if (status.getRetweetCount() > VIRAL_RETWEET_THRESHOLD) {
				
				LOGGER.info("Looking at tweet " + status.getId() + " " + status.getText());
				
				if (status.isRetweet()) {
					status = getOriginalStatus(status,twitter);
				}
				
				//we only want tweets from within the last week
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, -7);
				
				//if (status.getCreatedAt().after(cal.getTime())) {
				if (status.getLang().equals("en")) {
					if (isOriginal(viralStatuses,status)) {
						if (status.getExtendedMediaEntities().length > 0 || status.getMediaEntities().length > 0) {
							viralStatuses.add(status);
						} else {
							LOGGER.info("Skipping because it has no photo or video");
						}
					} else {
						LOGGER.info("Skipping because it's not original");
					}
				} else {
					LOGGER.info("Skipping because it's not English");
				}
				
				
				/*if (status.getMediaEntities().length > 0) {
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
				}*/
				
			}
		}

		return viralStatuses;
	}
	
	
	public Trend[] getTrends(Twitter twitter) throws TwitterException {
		Trends trends = twitter.trends().getPlaceTrends(23424977);
		Trend[] trs = trends.getTrends();
		return trs;
	}
	
	
	public List<Status> getStatusesFromTrends(Twitter twitter, Trend[] trs) throws TwitterException {
		List<Status> statuses = new ArrayList<Status>();
		
		int count = 0;
		
		for (Trend tr : trs) {
			LOGGER.info("Looking at trend " + tr.getName());
			
			Query query = new Query(tr.getQuery()).count(COUNT_SIZE);
			QueryResult result = twitter.search(query);
			statuses.addAll(result.getTweets());
			count++;
			//if(count>20) break;
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException ie) {
				
			}
		}
		
		return statuses;
	}
	
	
	public Twitter getFullyCredentialedTwitter() {
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
	    	return twitter;
		} catch (Exception e) {
			LOGGER.error("Problem creating Twitter!",e);
			throw new RuntimeException(e.getMessage());
		}
	}

}
