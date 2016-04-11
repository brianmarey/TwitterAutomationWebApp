package com.careydevelopment.twitterautomation.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.configuration.MyTwitter;
import com.careydevelopment.twitterautomation.jpa.entity.FollowRun;
import com.careydevelopment.twitterautomation.jpa.entity.User;
import com.careydevelopment.twitterautomation.jpa.repository.FollowRunRepository;
import com.careydevelopment.twitterautomation.jpa.repository.UserRepository;
import com.careydevelopment.twitterautomation.model.FriendshipLightweight;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.SecurityHelper;

import twitter4j.Friendship;
import twitter4j.IDs;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@RestController
public class GetUnfollowTweepsController implements Constants {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetUnfollowTweepsController.class);
	
	private static final int COUNT_SIZE = 100;
	
	private Twitter twitter = null;
	
	@Autowired
	UserRepository userRepository;
	
    @RequestMapping(value="/getUnfollowTweeps",method = RequestMethod.GET,produces="application/json")
    public List<Long> getUnfollowTweeps(@RequestParam(value="twitterUser", required=true) String twitterUser, 
    		HttpServletRequest request, Model model) {
    	
    	//FollowRun followRun = logRun();
    	//request.getSession().setAttribute(CURRENT_FOLLOW_RUN, followRun);
   
		//get the twitter4j Twitter object from the singleton
		twitter = MyTwitter.instance().getTwitter(twitterUser);

		List<Long> allFollowees = new LinkedList<Long>();
		
		try {
			long cursor = -1; //1463043556452890873l;
			for (int i=0;i<2;i++) {
				IDs ids = twitter.getFriendsIDs(cursor);
				long longids[] = ids.getIDs();
				//System.err.println("Length of longids is " + longids.length);
				for (int j=0;j<longids.length;j++) {
					allFollowees.add(longids[j]);
				}
				cursor = ids.getNextCursor();
				LOGGER.info("next cursor is " + cursor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LOGGER.info("followees length is " + allFollowees.size());
        return allFollowees;
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
