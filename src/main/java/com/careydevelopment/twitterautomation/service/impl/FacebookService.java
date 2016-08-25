package com.careydevelopment.twitterautomation.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.careydevelopment.propertiessupport.PropertiesFactory;
import com.careydevelopment.propertiessupport.PropertiesFactoryException;
import com.careydevelopment.propertiessupport.PropertiesFile;
import com.careydevelopment.twitterautomation.util.JsonParser;
import com.sdicons.json.model.JSONArray;
import com.sdicons.json.model.JSONInteger;
import com.sdicons.json.model.JSONObject;
import com.sdicons.json.model.JSONString;

@Component
public class FacebookService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FacebookService.class);
	
	public FacebookService() {
		// TODO Auto-generated constructor stub
	}
	
	
	public List<JSONObject> getViralPhotosAndVideos(String page) {
		List<JSONObject> st = new ArrayList<JSONObject>();
		
		try {
			//https://graph.facebook.com/search?q=coffee&%20type=place&%20center=37.76,-122.427&%20distance=1000&access_token=xxx
			//https://graph.facebook.com/v2.6/1575049072?access_token=xxx
			//https://graph.facebook.com/mrbrianmcarey?access_token=xxx
			//https://graph.facebook.com/223697104492609/feed?access_token=xxx
			//223697104492609_483177508544566/likes?fields=total_count&summary=true
			//223697104492609_483177508544566?fields=shares
			//oauth token is found at https://developers.facebook.com/tools/accesstoken/
			//<iframe src="https://www.facebook.com/plugins/post.php?href=https://www.facebook.com/georgehtakei/posts/1587818511247596" style="width:600px;height:600px;border:none;overflow:hidden" scrolling="no" frameborder="0" allowTransparency="true"></iframe>

			JsonParser reader = getFeed(page);
			
			//JsonParser reader = new JsonParser("https://graph.facebook.com/v2.6/georgehtakei?access_token=" + accessToken);
			JSONObject json = reader.getJson();
			JSONArray data =(JSONArray)json.get("data");
			
			for (int i=0;i<data.size();i++) {
				JSONObject obj = (JSONObject)data.get(i);
				JSONString typeJson = (JSONString)obj.get("status_type");
				String type = typeJson.getValue();
				if (type != null && (type.equals("added_photos") || type.equals("added_video"))) {
					JSONObject shares = (JSONObject)obj.get("shares");
					if (shares != null) {
						JSONInteger countJson = (JSONInteger)shares.get("count");
						if (countJson != null) {
							BigInteger count = countJson.getValue();
							
							if (count.intValue() > 1000) {
								st.add(obj);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Problem reading Facebook viral posts!",e);
		}
		
		return st;
	}
	
	
	public String getEmbedCode(String id, String page, String category) {
		StringBuilder builder = new StringBuilder();
		
		String url = getUrl(id,page,category);
		
		if (category != null) {
			if (category.equals("added_photos")) {
				builder.append("<iframe src=\"https://www.facebook.com/plugins/post.php?href=");
				builder.append(url);
				builder.append("\" style=\"width:600px;height:600px;border:none;overflow:hidden\" scrolling=\"no\" frameborder=\"0\" allowTransparency=\"true\"></iframe>");				
			} else {
				builder.append("<iframe src=\"https://www.facebook.com/plugins/video.php?href=");
				builder.append(url);
				builder.append("\" style=\"width:600px;height:600px;border:none;overflow:hidden\" scrolling=\"no\" frameborder=\"0\" allowTransparency=\"true\" allowFullScreen=\"true\"></iframe>");
			}
		}
		
	
		return builder.toString();
	}
	

	private String getUrl(String id, String page, String category) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("https://www.facebook.com/");
		builder.append(page);
		
		if (category.equals("added_video")) {
			builder.append("/videos/");
		} else {
			builder.append("/posts/");
		}

		String[] ids = id.split("_");
		builder.append(ids[1]);

		return builder.toString();
	}
	
	
	private String getAccessToken() throws PropertiesFactoryException {
		/*Properties props = PropertiesFactory.getProperties(PropertiesFile.FACEBOOK_PROPERTIES);
		String accessToken = props.getProperty("access.token");
		return accessToken;*/
		return "";
	}
	
	
	private JsonParser getFeed(String page) throws PropertiesFactoryException {
		String feedUrl = getFeedUrl(page);
		JsonParser reader = new JsonParser(feedUrl);
		return reader;
	}
	
	
	private String getFeedUrl(String page) throws PropertiesFactoryException {
		StringBuilder builder = new StringBuilder();
		
		builder.append("https://graph.facebook.com/v2.6/");
		builder.append(page);
		builder.append("/feed?access_token=");
		builder.append(getAccessToken());
		builder.append("&fields=id,message,status_type,shares&limit=100");
		
		return builder.toString();
	}
	
	
	public static void main(String[] args) {
		try {
			Properties props = PropertiesFactory.getProperties(PropertiesFile.GOOGLE_PROPERTIES);
			String apiKey = props.getProperty("api.key");
			String feedUrl = "https://www.googleapis.com/pagespeedonline/v2/runPagespeed?url=http://brianmcarey.com&strategy=mobile&key=" + apiKey;
			JsonParser reader = new JsonParser(feedUrl);
			JSONObject json = reader.getJson();
			JSONObject ruleGroups =(JSONObject)json.get("ruleGroups");
			System.err.println(ruleGroups);
			JSONObject speed = (JSONObject)ruleGroups.get("SPEED");
			JSONInteger speedScore = (JSONInteger)speed.get("score");
			JSONObject usability = (JSONObject)ruleGroups.get("USABILITY");
			JSONInteger usabilityScore = (JSONInteger)usability.get("score");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
