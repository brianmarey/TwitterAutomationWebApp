package com.careydevelopment.twitterautomation.controller.blastfollow;

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

import com.careydevelopment.twitterautomation.model.FriendshipLightweight;
import com.careydevelopment.twitterautomation.service.impl.TwitterService;
import com.careydevelopment.twitterautomation.util.Constants;

import twitter4j.Friendship;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@RestController
public class GetTweepsController implements Constants {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetTweepsController.class);
	
	@Autowired
	private TwitterService twitterService;
	
    @RequestMapping(value="/getTweeps",method = RequestMethod.GET,produces="application/json")
    public List<FriendshipLightweight> getTweeps(@RequestParam(value="keyword", required=true) String keyword,
    		HttpServletRequest request,Model model) {
    	
    	List<FriendshipLightweight> ships = new ArrayList<FriendshipLightweight>();
		
    	//get the twitter4j Twitter object from the singleton
		Twitter twitter = (Twitter)request.getSession().getAttribute(Constants.TWITTER);

		if (twitter == null) {
			return ships;
		}
		
		if (keyword == null || keyword.trim().length() == 0) {
			return ships;
		}
		
		//fetch the DNF ids
		//List<Long> dnfIds = fetchDnfs();
		//LOGGER.info("length of dnfs is " + dnfIds.size());

		String[] keys = new String[1];//keywords.split(",");
		keys[0] = keyword;
		
		try {
			ships = twitterService.getFriendshipsByKeyword(twitter, keys);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Problem finding tweeps by keyword!",e);
		}
		
        return ships;
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
