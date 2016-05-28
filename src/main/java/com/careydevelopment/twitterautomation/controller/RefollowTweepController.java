package com.careydevelopment.twitterautomation.controller;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.twitterautomation.model.RefollowResult;
import com.careydevelopment.twitterautomation.model.UnfollowResult;
import com.careydevelopment.twitterautomation.util.Constants;

import twitter4j.Relationship;
import twitter4j.Twitter;
import twitter4j.User;

@RestController
public class RefollowTweepController implements Constants {
	private static final Logger LOGGER = LoggerFactory.getLogger(RefollowTweepController.class);
	
	private List<String> whiteList = null;
	private Twitter twitter = null;
	
	//@Autowired
	//FolloweeRepository followeeRepository;
	
    @RequestMapping(value="/refollowTweep",method = RequestMethod.GET,produces="application/json")
    public RefollowResult refollowTweep(@RequestParam(value="id", required=true) String id, 
    		HttpServletRequest request, Model model) {
		
    	//get the twitter4j Twitter object from the singleton
		twitter = (Twitter)request.getSession().getAttribute(Constants.TWITTER);
		if (twitter == null) {
			LOGGER.error("No Twitter in refollow!");
			return new RefollowResult();
		}
		
		//FollowRun followRun =(FollowRun)request.getSession().getAttribute(CURRENT_FOLLOW_RUN);
		//LOGGER.info("Current follow run is " + followRun);
		
		LOGGER.info("id for refollow " + id);
	
		Long lid = new Long(id);
		
		//FollowResult followResult = new FollowResult();
		//int unfollowResultCode = 1;
		
		//Followee followee = new Followee();
	
		//whiteList = getWhiteList();

		String followResult = "";
		
		User user = null;
		Relationship rel = null;
		
		try {
			rel = twitter.showFriendship(twitter.getId(), lid);
			user = twitter.showUser(lid);
			
			followResult = attemptRefollow(rel,user);
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
			followResult = "Problem retrieving user!";
		}		
		
		LOGGER.info("returning " + followResult);
		
		RefollowResult result = new RefollowResult();
		result.setFollowResult(followResult);
		
        return result;
    }

    
    private String attemptRefollow(Relationship rel, User user) {
		String followResult = "";
		
    	try {
			if (!rel.isTargetFollowedBySource()) {
				LOGGER.info(user.getScreenName() + " is following me bu I'm not following back!");
				
				twitter.createFriendship(user.getId());
				followResult = "Refollowed " + user.getScreenName();
			} else {
				LOGGER.info("You are following " + user.getScreenName());
				followResult = "You are following " + user.getScreenName();
			}
		} catch (Exception e) {
			LOGGER.error("Problem attempting refollow",e);
			followResult = "Problem refollowing " + user.getScreenName();
		}

    	return followResult;
    }
    
}
