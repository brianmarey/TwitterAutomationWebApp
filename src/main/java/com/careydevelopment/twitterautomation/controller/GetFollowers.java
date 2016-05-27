package com.careydevelopment.twitterautomation.controller;

import java.util.ArrayList;
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

import com.careydevelopment.twitterautomation.service.TwitterService;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.TwitterHelper;

import twitter4j.Twitter;
import twitter4j.User;

@RestController
public class GetFollowers implements Constants {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetFollowers.class);

	
	@Autowired
	private TwitterService twitterService;
	
    @RequestMapping(value="/getFollowers",method = RequestMethod.GET,produces="application/json")
    public List<String> getFollowers(@RequestParam(value="username", required=true) String username,
    		HttpServletRequest request,Model model) {
    	
		List<String> finalNames = new ArrayList<String>();
		List<User> users = new ArrayList<User>();
		
		Twitter twitter = (Twitter)request.getSession().getAttribute(Constants.TWITTER);
		
		if (twitter == null) {
			return finalNames;
		}
		
		if (username == null || username.trim().length() == 0) {
			return finalNames;
		}
		
		try {
			twitterService.getFollowers(twitter, users, username, 3);			
			finalNames = twitterService.getFriendshipsFromUsers(users, twitter);
		} catch (Exception e) {
			LOGGER.error("Problem fetching followers",e);
		}
		
		LOGGER.info("I have " + finalNames.size() + " followers for " + username);
				
        return finalNames;
    }
}
