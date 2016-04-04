package com.careydevelopment.twitterautomation.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.configuration.MyTwitter;
import com.careydevelopment.twitterautomation.model.FriendshipLightweight;
import com.careydevelopment.twitterautomation.util.Constants;

import twitter4j.Friendship;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

@RestController
public class GetTweepsController implements Constants {
	private static final Logger LOGGER = Logger.getLogger(GetTweepsController.class);
	
	private Twitter twitter = null;
	
    @RequestMapping(value="/getTweeps",method = RequestMethod.GET,produces="application/json")
    public List<FriendshipLightweight> getTweeps(@RequestParam(value="keyword", required=true) String key, Model model) {
		//get the twitter4j Twitter object from the singleton
		twitter = MyTwitter.instance().getTwitter();
		
		LOGGER.info("Keyword is " + key);
		
		//fetch the DNF ids
		List<Long> dnfIds = fetchDnfs();
		LOGGER.info("length of dnfs is " + dnfIds.size());
		
		List<FriendshipLightweight> ships = new ArrayList<FriendshipLightweight>();
		
		try {
			Query query = new Query(key).count(10);
			QueryResult result = twitter.search(query);
					    
			String[] returnedDudes = getReturnedDudes(result);		
			
			for (String s : returnedDudes) {
				LOGGER.info(s);
			}
			
			ResponseList<Friendship> friendships = twitter.lookupFriendships(returnedDudes);
			
			ships = getLightweights(friendships,dnfIds);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return ships;
    }
    
    
    /**
     * Translates heavyweight Friendship objets from Twitter4j into lightweight objects
     * Easier for JSON
     */
    private List<FriendshipLightweight> getLightweights(ResponseList<Friendship> friendships, List<Long> dnfIds) {
    	List<FriendshipLightweight> ships = new ArrayList<FriendshipLightweight>();
    	
    	for (Friendship friendship : friendships) {
    		
    		//be sure to skip the people who are DNF
    		if (!dnfIds.contains(friendship.getId())) {
	    		FriendshipLightweight light = new FriendshipLightweight();
	    		light.setFollowing(friendship.isFollowing());
	    		light.setId(friendship.getId());
	    		light.setScreenName(friendship.getScreenName());
	    		
	    		ships.add(light);
    		} else {
    			LOGGER.info("Skipping " + friendship.getScreenName() + " because that user is DNF");
    		}
    	}
    	
    	return ships;
    }
    
	/**
	 * Returns candidates for following
	 */
	private String[] getReturnedDudes(QueryResult result) throws TwitterException {
		String[] returnedDudes = new String[result.getCount()];
		long[] blocks = twitter.getBlocksIDs().getIDs();

		int i = 0;
		
		for (Status status : result.getTweets()) {
			User thisUser = status.getUser();
	    	boolean isBlocked = false;
	    	for (long l : blocks) {
	    		if (l == thisUser.getId()) {
	    			isBlocked = true;
	    		}
	    	}
			
	    	if (!isBlocked) {
	    		returnedDudes[i] = thisUser.getScreenName();
			    i++;
			}
		}
		
		return returnedDudes;
	}

    
    
	/**
	 * Reads a list of do-not-follows from the file.
	 * That file will be appended with the IDs of people we try to follow here
	 * So we don't keep following the same person over and over
	 */
	private List<Long> fetchDnfs() {
		BufferedReader br = null;
		List<Long> dnfIds = new ArrayList<Long>();
		
	    try {
	    	br = new BufferedReader(new FileReader(DNF_FILE));
	        String line = br.readLine();

	        while (line != null) {
	        	if (!line.trim().equals("")) {
			        Long id = new Long(line);
			        dnfIds.add(id);
	        	}

	            line = br.readLine();
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	try {
	    		br.close();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	    	    
	    /*for (Long id : dnfIds) {
	    	LOGGER.debug(id);
	    }*/
	    
	    return dnfIds;
	}
}
