package com.careydevelopment.twitterautomation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.careydevelopment.twitterautomation.service.LoginService;
import com.careydevelopment.twitterautomation.util.Constants;

import twitter4j.Twitter;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@Controller
public class TwitterCallbackController {
	
	@Autowired
	private LoginService loginService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterCallbackController.class);
 
	
    @RequestMapping("/twitterCallback")
    public String twitterCallback(@RequestParam(value="oauth_verifier", required=true) String oauthVerifier,
    		HttpServletRequest request, HttpServletResponse response, Model model) {

    	//get the objects from the session
    	Twitter twitter = (Twitter) request.getSession().getAttribute(Constants.TWITTER);
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        
        try {
        	//get the access token
            AccessToken token = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
            
            loginService.login(twitter,model,request,response, token);
        } catch (Exception e) {
            LOGGER.error("Problem getting token!",e);
            return "redirect:notLoggedIn";
        }
        
        return "redirect:seoplayhouse";
    }
    
    
    @RequestMapping("/loggedInPage")
    public String loggedInPage(HttpServletRequest request, Model model) {    	
    	//Twitter twitter = (Twitter)request.getSession().getAttribute("twitter");	
    	//String screenName = twitter.getScreenName();
    	//model.addAttribute("screenName",screenName);
    	
    	return "seoplayhouse";
    }

}
