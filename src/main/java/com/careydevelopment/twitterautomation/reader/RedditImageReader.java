package com.careydevelopment.twitterautomation.reader;



import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.twitterautomation.jpa.entity.RedditImage;
import com.careydevelopment.twitterautomation.util.FacebookHelper;
import com.careydevelopment.twitterautomation.util.ImageHelper;
import com.careydevelopment.twitterautomation.util.JsonParser;
import com.sdicons.json.model.JSONArray;
import com.sdicons.json.model.JSONObject;
import com.sdicons.json.model.JSONString;

public class RedditImageReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedditImageReader.class);

	List<RedditImage> imageUrls = new ArrayList<RedditImage>();
	
	public static void main(String[] args) {
		RedditImageReader ri = new RedditImageReader("https://reddit.com/r/funny.json?limit=75");
		List<RedditImage> images = ri.getImageUrls();
		for (RedditImage image : images) {
			LOGGER.info("aption is " + image.getCaption());
			LOGGER.info("url is " + image.getImageUrl());
			LOGGER.info("sub is " + image.getSubreddit());
			LOGGER.info("\n\n\n");
		}
	}
	
	
	public RedditImageReader(String url) {
		JSONObject json = null;
		int attemptCount = 0;
		
		while (json == null) {
			JsonParser reader = new JsonParser(url);
			json = reader.getJson();		
			
			if (json == null) {
				attemptCount++;
				if (attemptCount > 25) {
					LOGGER.warn("Couldn't get thru to reddit for image downloads. Giving up.");
					break;
				}
				
				try {
					LOGGER.info("Problem reading images from reddit - trying again in 10 seconds");
					Thread.sleep(10000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		if (json != null) fetchUrls(json);
	}
	
	
	private void fetchUrls(JSONObject json) {
        JSONObject val = (JSONObject)json.get("data");
        
        JSONArray chil = (JSONArray)val.get("children");
        
        for (int i=0; i<chil.size();i++) {
        	RedditImage ri = new RedditImage();
        	
            JSONObject obj = (JSONObject)chil.get(i);            
            JSONObject data = (JSONObject)obj.get("data");
            
            JSONString subredditJson = (JSONString)data.get("subreddit");
            if (subredditJson != null && subredditJson.getValue() != null) {
            	ri.setSubreddit(subredditJson.getValue());
            }

            JSONString titleJson = (JSONString)data.get("title");
            if (titleJson != null && titleJson.getValue() != null) {
            	ri.setCaption(titleJson.getValue());
            }

            JSONString u = (JSONString)data.get("url");
            
            if (u != null && u.getValue() != null) {
            	String image = u.getValue();

            	if (image.indexOf(".gifv") == -1 && (image.indexOf("gif")>-1 || image.indexOf("jpeg")>-1 || image.indexOf("png")>-1 || image.indexOf("jpg")>-1)) {
            		ri.setImageUrl(image);
            	} else {
            		String imageUrl = FacebookHelper.getImageFromUrl(image);
            		if (imageUrl != null && !imageUrl.equals("")) {
            			ri.setImageUrl(ImageHelper.stripParameters(imageUrl));
            		}
            	}
            }            
            
            if (ri.getImageUrl() != null && ri.getImageUrl().length() > 3) {
            	imageUrls.add(ri);	
            }
        }
	}
	
	public List<RedditImage> getImageUrls() {
		return imageUrls;
	}
}
