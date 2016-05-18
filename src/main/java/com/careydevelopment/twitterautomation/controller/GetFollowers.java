package com.careydevelopment.twitterautomation.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.twitterautomation.util.Constants;

import twitter4j.Friendship;
import twitter4j.IDs;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

@RestController
public class GetFollowers implements Constants {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetFollowers.class);
	
	private Twitter twitter = null;
	
    @RequestMapping(value="/getFollowers",method = RequestMethod.GET,produces="application/json")
    public List<Long> getFollowers(@RequestParam(value="username", required=true) String username,
    		HttpServletRequest request,Model model) {
    	
    	List<Long> ids = new ArrayList<Long>();
		List<Long> finalIds = new ArrayList<Long>();

    	//get the twitter4j Twitter object from the singleton
		twitter = (Twitter)request.getSession().getAttribute(Constants.TWITTER);
		
		if (twitter == null) {
			return ids;
		}
		
		if (username == null || username.trim().length() == 0) {
			return ids;
		}
		
		try {
			long cursor = -1; //1463043556452890873l;
			for (int i=0;i<2;i++) {
				IDs followers = twitter.getFollowersIDs(username,cursor);
				long longids[] = followers.getIDs();
				//System.err.println("Length of longids is " + longids.length);
				for (int j=0;j<longids.length;j++) {
					ids.add(longids[j]);
				}
				cursor = followers.getNextCursor();
				LOGGER.info("next cursor is " + cursor);
				if (cursor == 0) break;
			}	
			
			long[] allIds = new long[ids.size()];
			for (int i=0;i<ids.size();i++) {
				allIds[i] = ids.get(i);
			}
			
			int start = 0;
			int length = 100;
			while (finalIds.size() < MAX_FOLLOW_SIZE && start < allIds.length - (length + 1)) {			
				long[] subIds = new long[length];
				subIds = Arrays.copyOfRange(allIds, start, start + length);
				
				ResponseList<Friendship> friendships = twitter.lookupFriendships(subIds);
	
				for (Friendship friendship : friendships) {
					LOGGER.info(friendship.getScreenName() + " " + friendship.isFollowedBy() + " " + friendship.isFollowing());
					if (!friendship.isFollowedBy()) {
						finalIds.add(friendship.getId());
					}
				}
				
				start += length;
				Thread.sleep(3000);
			}
		} catch (Exception e) {
			LOGGER.error("Problem fetching user " + username,e);
		}
		
		
		LOGGER.info("I have " + finalIds.size() + " followers for " + username);
				
        return finalIds;
    }
}
