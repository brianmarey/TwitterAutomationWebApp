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

import com.careydevelopment.configuration.MyTwitter;
import com.careydevelopment.twitterautomation.model.UnfollowResult;
import com.careydevelopment.twitterautomation.util.Constants;

import twitter4j.Relationship;
import twitter4j.Twitter;
import twitter4j.User;

@RestController
public class UnfollowTweepController implements Constants {
	private static final Logger LOGGER = LoggerFactory.getLogger(UnfollowTweepController.class);
	
	private List<String> whiteList = null;
	private Twitter twitter = null;
	
	//@Autowired
	//FolloweeRepository followeeRepository;
	
    @RequestMapping(value="/unfollowTweep",method = RequestMethod.GET,produces="application/json")
    public UnfollowResult unfollowTweep(@RequestParam(value="id", required=true) String id, 
    		HttpServletRequest request, Model model) {
		
    	//get the twitter4j Twitter object from the singleton
		twitter = (Twitter)request.getSession().getAttribute(Constants.TWITTER);
		if (twitter == null) {
			LOGGER.error("No Twitter in unfollow!");
			return new UnfollowResult();
		}
		
		//FollowRun followRun =(FollowRun)request.getSession().getAttribute(CURRENT_FOLLOW_RUN);
		//LOGGER.info("Current follow run is " + followRun);
		
		LOGGER.info("id for unfollow " + id);
	
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
			
			followResult = attemptUnfollow(rel,user);
		} catch (Exception e) {
			LOGGER.error("Problem attempting unfollow",e);
			followResult = "Problem retrieving user info!";
		}		
		
		LOGGER.info("returning " + followResult);
		
		UnfollowResult result = new UnfollowResult();
		result.setFollowResult(followResult);
		
        return result;
    }

    
    private String attemptUnfollow(Relationship rel, User user) {
		String followResult = "";
		
    	try {
			if (!rel.isSourceFollowedByTarget()) {
				LOGGER.info(user.getScreenName() + " isn't following me back!");
				
				//twitter.destroyFriendship(user.getId());
				followResult = "Unfollowed " + user.getScreenName();
			} else {
				LOGGER.info(user.getScreenName() + " is following me");
				followResult = user.getScreenName() + " is following you";
			}
		} catch (Exception e) {
			LOGGER.error("Problem attempting unfollow",e);
			followResult = "Problem unfollowing " + user.getScreenName();
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
	
	
	private static final List<String> getWhiteList() {
		List<String> whiteList = new ArrayList<String>();
		
		whiteList.add("jrsalzman");
		whiteList.add("kattimpf");
		whiteList.add("anthonybialy");
		whiteList.add("gabrielmalor");
		whiteList.add("gaypatriot");
		whiteList.add("lauren_southern");
		whiteList.add("prisonplanet");
		whiteList.add("metricbuttload ");
		whiteList.add("tarynonthenews");
		whiteList.add("jasonmattera");
		whiteList.add("anncoulter");
		whiteList.add("imao_");
		whiteList.add("esotericcd");
		whiteList.add("sistertoldjah");
		whiteList.add("redsteeze");
		whiteList.add("exjon");
		whiteList.add("blakeneff");
		whiteList.add("matthops82");
		whiteList.add("countermoonbat");
		whiteList.add("lilmissrightie");
		whiteList.add("justicewillett");
		whiteList.add("heminator");
		whiteList.add("cjoe15");
		whiteList.add("shannityshair");
		whiteList.add("gopteens");
		whiteList.add("baseballcrank");
		whiteList.add("benshapiro");
		whiteList.add("redsteeze");
		whiteList.add("leonhwolf");
		whiteList.add("ladylibertas76");
		whiteList.add("ag_conservative");
		whiteList.add("awrhawkins");
		whiteList.add("martian_munk");
		whiteList.add("biasedgirl");
		whiteList.add("rbpundit");
		whiteList.add("guypbenson");
		whiteList.add("charlescwcooke");
		whiteList.add("jonahnro");
		whiteList.add("richkaszak");
		whiteList.add("sargon_of_akkad");
		return whiteList;
	}
}
