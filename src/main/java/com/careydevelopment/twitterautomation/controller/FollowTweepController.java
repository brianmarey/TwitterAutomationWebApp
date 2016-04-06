package com.careydevelopment.twitterautomation.controller;

import java.io.FileWriter;
import java.io.PrintWriter;

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
import com.careydevelopment.twitterautomation.jpa.entity.Followee;
import com.careydevelopment.twitterautomation.jpa.repository.FolloweeRepository;
import com.careydevelopment.twitterautomation.model.FollowResult;
import com.careydevelopment.twitterautomation.util.Constants;

import twitter4j.Twitter;

@RestController
public class FollowTweepController implements Constants {
	private static final Logger LOGGER = LoggerFactory.getLogger(FollowTweepController.class);
	
	@Autowired
	FolloweeRepository followeeRepository;
	
    @RequestMapping(value="/followTweep",method = RequestMethod.GET,produces="application/json")
    public FollowResult followTweep(@RequestParam(value="id", required=true) String id, 
    		@RequestParam(value="screenName", required=true) String screenName,
    		@RequestParam(value="twitterUser", required=true) String twitterUser,
    		HttpServletRequest request, Model model) {
		//get the twitter4j Twitter object from the singleton
		Twitter twitter = MyTwitter.instance().getTwitter(twitterUser);
		
		FollowRun followRun =(FollowRun)request.getSession().getAttribute(CURRENT_FOLLOW_RUN);
		LOGGER.info("Current follow run is " + followRun);
		
		LOGGER.info("id for follow " + id + " " + screenName);
				
		FollowResult followResult = new FollowResult();
		int followResultCode = 1;
		
		Followee followee = new Followee();
		
		try {
			Long twitterId = new Long(id);
			followee.setTwitterId(twitterId);
			twitter.createFriendship(screenName);
		    addToDnf(twitterId);
		    followResult.setMessage("Followed ");
		} catch (Exception e) {
			e.printStackTrace();
			followResult.setMessage("Failed to follow ");
			followResultCode = 2;
		}
		
		followee.setFollowRun(followRun);
		followee.setScreenName(screenName);
		followee.setStatus(followResultCode);
		
		followeeRepository.save(followee);
		
        return followResult;
    }
    
    
	/**
	 * Adds the given id to the DNF file so we don't try to follow him again
	 */
	private void addToDnf(long id) {
		try {
			PrintWriter f0 = new PrintWriter(new FileWriter(DNF_FILE,true));
			f0.println(""+id);
			f0.close();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Problem writing ID to file!",e);
		}
	}
}
