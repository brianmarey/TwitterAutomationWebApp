package com.careydevelopment.twitterautomation.controller;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.careydevelopment.propertiessupport.PropertiesFactory;
import com.careydevelopment.propertiessupport.PropertiesFactoryException;
import com.careydevelopment.propertiessupport.PropertiesFile;
import com.careydevelopment.twitterautomation.service.TwitterService;
import com.careydevelopment.twitterautomation.util.Constants;

import twitter4j.Twitter;
import twitter4j.auth.RequestToken;

@Controller
public class GetTokenController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GetTokenController.class);
	
	@Autowired
	private TwitterService twitterService;
	
    @RequestMapping("/getToken")
    public RedirectView getToken(HttpServletRequest request,Model model) {
    	//this will be the URL that we take the user to
    	String twitterUrl = "";
    	
		try {
			//get the Twitter object
			Twitter twitter = twitterService.getTwitter();
			
			//get the callback url so they get back here
			String callbackUrl = getCallbackUrl();

			//go get the request token from Twitter
			RequestToken requestToken = twitter.getOAuthRequestToken(callbackUrl);
			
			//put the token in the session because we'll need it later
			request.getSession().setAttribute("requestToken", requestToken);
			
			//let's put Twitter in the session as well
			request.getSession().setAttribute(Constants.TWITTER, twitter);
			
			//now get the authorization URL from the token
			twitterUrl = requestToken.getAuthorizationURL();
			
			LOGGER.info("Authorization url is " + twitterUrl);
		} catch (Exception e) {
			LOGGER.error("Problem getting authorization URL!",e);
		}
    	
		RedirectView redirectView = new RedirectView();
	    redirectView.setUrl(twitterUrl);
	    return redirectView;
    }

    
    private String getCallbackUrl() {
    	String callbackUrl = "";
    	
    	try {
    		Properties props = PropertiesFactory.getProperties(PropertiesFile.CALLBACK_PROPERTIES);
			callbackUrl = props.getProperty("twitter.callback");
    	} catch (PropertiesFactoryException pe) {
    		LOGGER.error("Problem getting localhost properties!",pe);
    	}
    	
    	return callbackUrl;
    }
  
}
