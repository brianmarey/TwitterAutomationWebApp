package com.careydevelopment.twitterautomation.controller;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.configuration.MyTwitter;
import com.careydevelopment.twitterautomation.model.FollowResult;
import com.careydevelopment.twitterautomation.util.Constants;

import twitter4j.Twitter;

@RestController
public class FollowTweepController implements Constants {
	private static final Logger LOGGER = LoggerFactory.getLogger(FollowTweepController.class);
	
    @RequestMapping(value="/followTweep",method = RequestMethod.GET,produces="application/json")
    public FollowResult followTweep(@RequestParam(value="id", required=true) String id, 
    		@RequestParam(value="screenName", required=true) String screenName,
    		@RequestParam(value="twitterUser", required=true) String twitterUser,
    		Model model) {
		//get the twitter4j Twitter object from the singleton
		Twitter twitter = MyTwitter.instance().getTwitter(twitterUser);
		
		LOGGER.info("id for follow " + id + " " + screenName);
				
		FollowResult followResult = new FollowResult();
		
		try {
			Long twitterId = new Long(id);
			twitter.createFriendship(screenName);
		    addToDnf(twitterId);
		    followResult.setMessage("Followed ");
		} catch (Exception e) {
			e.printStackTrace();
			followResult.setMessage("Failed to follow ");
		}
		
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
