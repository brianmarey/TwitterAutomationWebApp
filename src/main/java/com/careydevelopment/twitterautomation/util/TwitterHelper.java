package com.careydevelopment.twitterautomation.util;

import java.math.BigInteger;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.propertiessupport.PropertiesFactory;
import com.careydevelopment.propertiessupport.PropertiesFactoryException;
import com.careydevelopment.propertiessupport.PropertiesFile;
import com.sdicons.json.model.JSONArray;
import com.sdicons.json.model.JSONInteger;
import com.sdicons.json.model.JSONObject;
import com.sdicons.json.model.JSONString;

public class TwitterHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterHelper.class);
	
	private static final int MAX_PER_PAGE = 200;
	private static final int DEFAULT_SLEEP_TIME = 3000;
	private static final int COUNT_SIZE = 100;
	private static final int VIRAL_RETWEET_THRESHOLD = 500;
	private static final int MAX_WIDTH = 400;
	
	public static void main(String[] args) {

		
		try {
			//https://graph.facebook.com/search?q=coffee&%20type=place&%20center=37.76,-122.427&%20distance=1000&access_token=216862972019284|oOkloD11dq4nmqUU9kS99yL1lE0
			//https://graph.facebook.com/v2.6/1575049072?access_token=216862972019284|oOkloD11dq4nmqUU9kS99yL1lE0
			//https://graph.facebook.com/mrbrianmcarey?access_token=216862972019284|oOkloD11dq4nmqUU9kS99yL1lE0
			//https://graph.facebook.com/223697104492609/feed?access_token=216862972019284|oOkloD11dq4nmqUU9kS99yL1lE0
			//223697104492609_483177508544566/likes?fields=total_count&summary=true
			//223697104492609_483177508544566?fields=shares
			//oauth token is found at https://developers.facebook.com/tools/accesstoken/
			//<iframe src="https://www.facebook.com/plugins/post.php?href=https://www.facebook.com/georgehtakei/posts/1587818511247596" style="width:600px;height:600px;border:none;overflow:hidden" scrolling="no" frameborder="0" allowTransparency="true"></iframe>
			
			
			/*Properties props = PropertiesFactory.getProperties(PropertiesFile.TWITTER_PROPERTIES);		
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
	    	
	    	User u = twitter.showUser("brianmcarey");
	    	ResponseList<UserList> lists = twitter.getUserLists("brianmcarey");
	    	for (UserList list : lists) {
	    		long listId = list.getId();
	    		Paging paging = new Paging (1,100);
	    		
	    		for (int i=1;i<10;i++) {
	    			paging.setPage(i);
	    			ResponseList<Status> statuses = twitter.getUserListStatuses(listId, paging);
	    			
	    			for (Status status : statuses) {
	    				LOGGER.info(status.getText() + " " + status.getId());
	    			}
	    			
	    			try {
	    				Thread.sleep(1000);
	    			} catch(Exception e) {
	    				e.printStackTrace();
	    			}
	    		}
	    	}
			*/
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
