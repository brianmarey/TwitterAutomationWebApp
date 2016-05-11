package com.careydevelopment.twitterautomation.controller;

import java.util.ArrayList;
import java.util.Calendar;
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

import com.careydevelopment.twitterautomation.jpa.entity.Role;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.repository.RoleRepository;
import com.careydevelopment.twitterautomation.jpa.repository.TwitterUserRepository;
import com.careydevelopment.twitterautomation.util.Constants;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@Controller
public class TwitterCallbackController {
	
	@Autowired
	TwitterUserRepository twitterUserRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterCallbackController.class);
 
	
    @RequestMapping("/twitterCallback")
    public String twitterCallback(@RequestParam(value="oauth_verifier", required=true) String oauthVerifier,
    		HttpServletRequest request, Model model) {

    	//get the objects from the session
    	Twitter twitter = (Twitter) request.getSession().getAttribute(Constants.TWITTER);
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        
        try {
        	//get the access token
            AccessToken token = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
            
            handleLogin(twitter,model,request);
            
            //remove the request token from the session
            request.getSession().removeAttribute("requestToken");
            
            //add the access token to the session
            request.getSession().setAttribute(Constants.LOGIN_KEY, token.getToken());
            
            User user = twitter.showUser(twitter.getId());
            request.getSession().setAttribute(Constants.TWITTER_USER, user);
            
            //setDisplayAttributes(model,user);
        } catch (Exception e) {
            LOGGER.error("Problem getting token!",e);
            return "redirect:notLoggedIn";
        }
        
        return "redirect:blastfollow";
    }
    
    
    private void handleLogin(Twitter twitter, Model model, HttpServletRequest request) throws TwitterException {
    	//just check to make sure we're happy
        String screenName = twitter.getScreenName();
        LOGGER.info("My screen name is " + screenName);
        
        TwitterUser u = twitterUserRepository.findByScreenName(screenName);
        LOGGER.info("Twitter user is " + u);
        if (u == null) {
        	handleNewUser(model,screenName,request);
        } else {
        	handleUpdateUser(model,u,request);
        }
    }
    
    
    private void handleNewUser(Model model, String screenName, HttpServletRequest request) {
    	TwitterUser u = new TwitterUser();
    	u.setScreenName(screenName);
    	u.setLastLogin(new Date());
    	u.setNewUser(true);
    	
    	twitterUserRepository.save(u);
    	
    	Role role = new Role();
    	role.setUser(u);
    	role.setRoleName("Basic");
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.YEAR, 20);
    	role.setExpires(cal.getTime());
    	
    	roleRepository.save(role);
    	
    	List<Role> roles = new ArrayList<Role>();
    	roles.add(role);
    	u.setRoles(roles);
    	
    	model.addAttribute(u);
    	request.getSession().setAttribute(Constants.TWITTER_ENTITY, u);
    }
    
    
    private void handleUpdateUser(Model model, TwitterUser user, HttpServletRequest request) {
    	user.setLastLogin(new Date());
    	twitterUserRepository.save(user);
    	model.addAttribute("twitterUser",user);   	
    	request.getSession().setAttribute(Constants.TWITTER_ENTITY, user);
    }
    
    
    @RequestMapping("/loggedInPage")
    public String loggedInPage(HttpServletRequest request, Model model) {    	
    	//Twitter twitter = (Twitter)request.getSession().getAttribute("twitter");	
    	//String screenName = twitter.getScreenName();
    	//model.addAttribute("screenName",screenName);
    	
    	return "blastfollow";
    }

}
