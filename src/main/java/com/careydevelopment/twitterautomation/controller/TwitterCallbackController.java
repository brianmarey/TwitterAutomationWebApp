package com.careydevelopment.twitterautomation.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.careydevelopment.twitterautomation.util.Constants;

import twitter4j.Twitter;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@Controller
public class TwitterCallbackController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterCallbackController.class);
 
    @RequestMapping("/twitterCallback")
    public String twitterCallback(@RequestParam(value="oauth_verifier", required=true) String oauthVerifier,
    		HttpServletRequest request, Model model) {

    	//get the objects from the session
    	Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        
        try {
        	//get the access token
            AccessToken token = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
            
            //just check to make sure we're happy
            String screenName = twitter.getScreenName();
            LOGGER.info("My screen name is " + screenName);
            
            //remove the request token from the session
            request.getSession().removeAttribute("requestToken");
            
            //add the access token to the session
            request.getSession().setAttribute(Constants.LOGIN_KEY, token.getToken());
            
            User user = twitter.showUser(twitter.getId());
            request.getSession().setAttribute(Constants.TWITTER_USER, user);
        } catch (Exception e) {
            LOGGER.error("Problem getting token!",e);
            return "redirect:notLoggedIn";
        }
        
        return "redirect:loggedInPage";
    }

    
    @RequestMapping("/loggedInPage")
    public String loggedInPage(HttpServletRequest request, Model model) {    	
    	//Twitter twitter = (Twitter)request.getSession().getAttribute("twitter");	
    	//String screenName = twitter.getScreenName();
    	//model.addAttribute("screenName",screenName);
    	
    	return "loggedInPage";
    }

}
