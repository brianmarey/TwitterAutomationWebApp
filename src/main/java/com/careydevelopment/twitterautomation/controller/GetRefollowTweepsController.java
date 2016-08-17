package com.careydevelopment.twitterautomation.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.twitterautomation.util.Constants;

import twitter4j.IDs;
import twitter4j.Twitter;

@RestController
public class GetRefollowTweepsController implements Constants {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetRefollowTweepsController.class);
	
	private static final int COUNT_SIZE = 100;
	
	private Twitter twitter = null;
	
	
    @RequestMapping(value="/getRefollowTweeps",method = RequestMethod.GET,produces="application/json")
    public List<Long> getRefollowTweeps(HttpServletRequest request, Model model) {

		List<Long> allFollowers = new LinkedList<Long>();

    	if (request.getSession().getAttribute(Constants.TWITTER_USER) == null) {
    		LOGGER.error("User not logged in!");
    		return allFollowers;
    	}
    	
    	//FollowRun followRun = logRun();
    	//request.getSession().setAttribute(CURRENT_FOLLOW_RUN, followRun);
   
		//get the twitter4j Twitter object from the singleton
		twitter = (Twitter)request.getSession().getAttribute(Constants.TWITTER);
		if (twitter == null) {
			LOGGER.error("No Twitter object!");
			return allFollowers;
		}
		
		
		try {
			long cursor = -1; //1463043556452890873l;
			for (int i=0;i<2;i++) {
				IDs ids = twitter.getFollowersIDs(cursor);
				long longids[] = ids.getIDs();
				//System.err.println("Length of longids is " + longids.length);
				for (int j=0;j<longids.length;j++) {
					allFollowers.add(longids[j]);
				}
				cursor = ids.getNextCursor();
				LOGGER.info("next cursor is " + cursor);
				if (cursor == 0) break;
			}
		} catch (Exception e) {
			LOGGER.error("Problem getting friends!",e);
			return allFollowers;
		}
		
		LOGGER.info("followers length is " + allFollowers.size());
        return allFollowers;
    }
    

    //saves the log of the run to the db
    /*private FollowRun logRun() {
    	FollowRun followRun = new FollowRun();
    	
    	String username = SecurityHelper.getUsername();
    	User user = userRepository.findById(username);
    	
    	followRun.setUser(user);
    	
    	FollowRun persisted = followRunRepository.save(followRun);
    	
    	return persisted;
    }*/
}
