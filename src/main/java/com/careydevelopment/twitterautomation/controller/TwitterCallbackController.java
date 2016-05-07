package com.careydevelopment.twitterautomation.controller;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.careydevelopment.twitterautomation.domain.Tip;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.repository.TwitterUserRepository;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.TipsHelper;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@Controller
public class TwitterCallbackController {
	
	@Autowired
	TwitterUserRepository twitterUserRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterCallbackController.class);
	
	
	@RequestMapping("/")
	public String home(HttpServletRequest request, Model model) {
		Twitter twitter = (Twitter) request.getSession().getAttribute(Constants.TWITTER);
		
		if (twitter == null) {
			return "redirect:notLoggedIn";
		}

        try {
            TwitterUser u = twitterUserRepository.findByScreenName(twitter.getScreenName());
            model.addAttribute("twitterUser",u);
            
            User user = twitter.showUser(twitter.getId());
            request.getSession().setAttribute(Constants.TWITTER_USER, user);
            
            setDisplayAttributes(model,user);
        } catch (Exception e) {
            LOGGER.error("Problem getting token!",e);
            return "redirect:notLoggedIn";
        }
        
        return "dashboard";
	}
 
	
    @RequestMapping("/twitterCallback")
    public String twitterCallback(@RequestParam(value="oauth_verifier", required=true) String oauthVerifier,
    		HttpServletRequest request, Model model) {

    	//get the objects from the session
    	Twitter twitter = (Twitter) request.getSession().getAttribute(Constants.TWITTER);
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        
        try {
        	//get the access token
            AccessToken token = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
            
            handleLogin(twitter,model);
            
            //remove the request token from the session
            request.getSession().removeAttribute("requestToken");
            
            //add the access token to the session
            request.getSession().setAttribute(Constants.LOGIN_KEY, token.getToken());
            
            User user = twitter.showUser(twitter.getId());
            request.getSession().setAttribute(Constants.TWITTER_USER, user);
            
            setDisplayAttributes(model,user);
        } catch (Exception e) {
            LOGGER.error("Problem getting token!",e);
            return "redirect:notLoggedIn";
        }
        
        return "dashboard";
    }

    
    private void setDisplayAttributes(Model model, User user) {
    	DecimalFormat df = new DecimalFormat("###.##");
    	df.setRoundingMode(RoundingMode.FLOOR);
    	
    	double ratio = ((double)user.getFollowersCount())/((double)user.getFriendsCount());
    	LOGGER.info("ratio is " + ratio);
    	
    	String ratioS = df.format(ratio);
    	model.addAttribute("ratio",ratioS);
    	
    	TipsHelper helper = new TipsHelper(user);
    	List<Tip> tips = helper.getTips();
    	model.addAttribute("tips",tips);
    }
    
    
    private void handleLogin(Twitter twitter, Model model) throws TwitterException {
    	//just check to make sure we're happy
        String screenName = twitter.getScreenName();
        LOGGER.info("My screen name is " + screenName);
        
        TwitterUser u = twitterUserRepository.findByScreenName(screenName);
        LOGGER.info("Twitter user is " + u);
        if (u == null) {
        	handleNewUser(model,screenName);
        } else {
        	handleUpdateUser(model,u);
        }
    }
    
    
    private void handleNewUser(Model model, String screenName) {
    	TwitterUser u = new TwitterUser();
    	u.setScreenName(screenName);
    	u.setLastLogin(new Date());
    	u.setNewUser(true);
    	twitterUserRepository.save(u);
    	model.addAttribute(u);
    }
    
    
    private void handleUpdateUser(Model model, TwitterUser user) {
    	user.setLastLogin(new Date());
    	twitterUserRepository.save(user);
    	model.addAttribute("twitterUser",user);
    }
    
    
    @RequestMapping("/loggedInPage")
    public String loggedInPage(HttpServletRequest request, Model model) {    	
    	//Twitter twitter = (Twitter)request.getSession().getAttribute("twitter");	
    	//String screenName = twitter.getScreenName();
    	//model.addAttribute("screenName",screenName);
    	
    	return "dashboard";
    }

}
