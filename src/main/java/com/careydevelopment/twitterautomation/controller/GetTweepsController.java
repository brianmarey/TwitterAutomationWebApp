package com.careydevelopment.twitterautomation.controller;

import java.io.BufferedReader;
import java.io.FileReader;
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

import com.careydevelopment.configuration.MyTwitter;
import com.careydevelopment.twitterautomation.jpa.entity.FollowRun;
import com.careydevelopment.twitterautomation.jpa.entity.User;
import com.careydevelopment.twitterautomation.jpa.repository.FollowRunRepository;
import com.careydevelopment.twitterautomation.jpa.repository.UserRepository;
import com.careydevelopment.twitterautomation.model.FriendshipLightweight;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.SecurityHelper;

import twitter4j.Friendship;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@RestController
public class GetTweepsController implements Constants {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetTweepsController.class);
	
	private static final int COUNT_SIZE = 100;
	
	private Twitter twitter = null;
	
	@Autowired
	FollowRunRepository followRunRepository;
	
	@Autowired
	UserRepository userRepository;
	
    @RequestMapping(value="/getTweeps",method = RequestMethod.GET,produces="application/json")
    public List<FriendshipLightweight> getTweeps(@RequestParam(value="keyword", required=true) String keywords,
    		@RequestParam(value="twitterUser", required=true) String twitterUser, HttpServletRequest request,
    		Model model) {
    	
    	FollowRun followRun = logRun();
    	request.getSession().setAttribute(CURRENT_FOLLOW_RUN, followRun);
   
    	
		//get the twitter4j Twitter object from the singleton
		twitter = MyTwitter.instance().getTwitter(twitterUser);
		
		//fetch the DNF ids
		List<Long> dnfIds = fetchDnfs();
		LOGGER.info("length of dnfs is " + dnfIds.size());
		
		String[] keys = keywords.split(",");
		
		
		List<FriendshipLightweight> ships = new ArrayList<FriendshipLightweight>();
		
		try {
			for (int i=0;i<keys.length;i++) {
				String key = keys[i];
				
				if (key != null && key.trim().length() > 2) {		
					LOGGER.info("Now searching for " + key);
					
					Query query = new Query(key).count(COUNT_SIZE);
					QueryResult result = twitter.search(query);
							    
					String[] returnedDudes = getReturnedDudes(result);		
					
					/*for (String s : returnedDudes) {
						LOGGER.info(s);
					}*/
					
					ResponseList<Friendship> friendships = twitter.lookupFriendships(returnedDudes);
					
					ships.addAll(getLightweights(friendships,dnfIds));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return ships;
    }
    

    //saves the log of the run to the db
    private FollowRun logRun() {
    	FollowRun followRun = new FollowRun();
    	
    	String username = SecurityHelper.getUsername();
    	User user = userRepository.findById(username);
    	
    	followRun.setUser(user);
    	
    	FollowRun persisted = followRunRepository.save(followRun);
    	
    	return persisted;
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
		//long[] blocks = twitter.getBlocksIDs().getIDs();

		int i = 0;
		
		for (Status status : result.getTweets()) {
			/*User thisUser = status.getUser();
	    	boolean isBlocked = false;
	    	for (long l : blocks) {
	    		if (l == thisUser.getId()) {
	    			isBlocked = true;
	    		}
	    	}
			
	    	if (!isBlocked) {*/
	    		returnedDudes[i] = status.getUser().getScreenName();
			    i++;
			//}
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
