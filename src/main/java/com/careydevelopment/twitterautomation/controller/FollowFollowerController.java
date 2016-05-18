package com.careydevelopment.twitterautomation.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.twitterautomation.jpa.entity.Followee;
import com.careydevelopment.twitterautomation.model.FollowResult;
import com.careydevelopment.twitterautomation.util.Constants;

import twitter4j.Twitter;
import twitter4j.User;

@RestController
public class FollowFollowerController implements Constants {
	private static final Logger LOGGER = LoggerFactory.getLogger(FollowFollowerController.class);
	
    @RequestMapping(value="/followFollower",method = RequestMethod.GET,produces="application/json")
    public FollowResult followTweep(@RequestParam(value="id", required=true) String id, 
    		HttpServletRequest request, Model model) {
    	
		Twitter twitter = (Twitter)request.getSession().getAttribute(Constants.TWITTER);
				
		FollowResult followResult = new FollowResult();
		
		Followee followee = new Followee();
		
		try {
			if (twitter == null) {
				throw new Exception("Twitter is null");
			}
		
			Long twitterId = new Long(id);
			followee.setTwitterId(twitterId);
			
			User user = twitter.createFriendship(twitterId);
		    //addToDnf(twitterId);
		    followResult.setMessage("Followed " + user.getScreenName());
		    LOGGER.info("Successfully followed " + user.getScreenName());
		} catch (Exception e) {
			LOGGER.warn("Follow attempt failed",e);
			followResult.setMessage("Follow attempt failed");
		}
		
		
        return followResult;
    }
        
}
