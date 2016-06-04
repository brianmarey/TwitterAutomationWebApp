package com.careydevelopment.twitterautomation.process;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.twitterautomation.jpa.entity.ViralFacebook;
import com.careydevelopment.twitterautomation.jpa.entity.ViralTweet;
import com.careydevelopment.twitterautomation.jpa.repository.ViralFacebookRepository;
import com.careydevelopment.twitterautomation.service.FacebookService;
import com.sdicons.json.model.JSONInteger;
import com.sdicons.json.model.JSONObject;
import com.sdicons.json.model.JSONString;

import twitter4j.Status;

public class ViralFacebookProcessor extends Thread {
	public static final Logger LOGGER = LoggerFactory.getLogger(ViralFacebookProcessor.class);
	
	ViralFacebookRepository viralFacebookRepository;
	FacebookService facebookService;
	
	
	
	public ViralFacebookProcessor(FacebookService facebookService, ViralFacebookRepository viralFacebookRepository) {
		this.facebookService = facebookService;
		this.viralFacebookRepository = viralFacebookRepository;
	}

	
	@Override
	public void run() {
	   LOGGER.info("Running viral facebook collector");

	   processViralVideosAndPhotosFromPages();
	}	
	
	
	
	private List<JSONObject> processViralVideosAndPhotosFromPages()  {
		List<JSONObject> posts = new ArrayList<JSONObject>();
		
		String[] specificPages = {"georgehtakei"};
		
		for (String page : specificPages) {
			LOGGER.info("Going with page " + page);
			try {
				posts = facebookService.getViralPhotosAndVideos(page);
				
			    for (JSONObject obj : posts) {
				   LOGGER.info("Now proessing " + obj);
				   handlePostAsPhotoOrVideo(obj,page);
			    }
			} catch (Exception e) {
				LOGGER.error("Problem reading list!",e);
			}
		}
			
		return posts;
	}
	
	
	private void handlePostAsPhotoOrVideo(JSONObject obj,String page) {
	   LOGGER.info("Looking at post " + obj);
	   JSONString typeJson = (JSONString)obj.get("status_type");
	   String type = typeJson.getValue();
	   
	   JSONString id = (JSONString)obj.get("id");
	   
	   String embedCode = facebookService.getEmbedCode(id.getValue(),page);
	   LOGGER.info(embedCode);
	   
	   int shareCount = 0;
	   JSONObject shares = (JSONObject)obj.get("shares");
		if (shares != null) {
			JSONInteger countJson = (JSONInteger)shares.get("count");
			if (countJson != null) {
				BigInteger count = countJson.getValue();
				shareCount = count.intValue();
			}
		}

	   handlePost(type,embedCode,id.getValue(),shareCount);
	}

	
	private void handlePost(String category, String embedCode,String id,int shares) {
		   ViralFacebook foundFacebook = viralFacebookRepository.findByFacebookId(id);
		   
		   if (foundFacebook == null) {
			   addFacebook(category,embedCode,id,shares);
		   } else {
			   updateFacebook(foundFacebook,shares);
		   }
	}

	
	private void updateFacebook(ViralFacebook foundFacebook, int shares) {
	   LOGGER.info("Already have it");
	   foundFacebook.setShares(shares);
	   viralFacebookRepository.save(foundFacebook);
	}
	
	
	private void addFacebook(String category, String embedCode, String id, int shares) {
		LOGGER.info("Adding it as " + category + " " + shares + " shares");
		
		ViralFacebook newFacebook = new ViralFacebook();
		newFacebook.setCategory(category);
		newFacebook.setDateSeen(new Date());
		newFacebook.setShares(shares);
		newFacebook.setEmbedCode(embedCode);
		newFacebook.setFacebookId(id);
		   
		try {
		   viralFacebookRepository.save(newFacebook);
		} catch (Exception e) {
			LOGGER.error("Problem saving facebook!",e);
		}
	}
}
