package com.careydevelopment.twitterautomation.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.careydevelopment.ipreader.IPLocation;
import com.careydevelopment.ipreader.IPLocationReader;
import com.careydevelopment.twitterautomation.jpa.entity.Role;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.entity.UserConfig;
import com.careydevelopment.twitterautomation.jpa.repository.RoleRepository;
import com.careydevelopment.twitterautomation.jpa.repository.TwitterUserRepository;
import com.careydevelopment.twitterautomation.jpa.repository.UserConfigRepository;
import com.careydevelopment.twitterautomation.util.Constants;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.AccessToken;

public class LoginService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
	

	@Autowired
	TwitterUserRepository twitterUserRepository;

	@Autowired
	UserConfigRepository userConfigRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	TwitterService twitterService;
	
	@Autowired
	EncryptionService encryptionService;
	
	/**
	 * Handles login for user whose tokens are stored in a cookie
	 */
	public Twitter login(Model model, HttpServletRequest request, String accessToken, String accessTokenSecret) throws TwitterException {
		AccessToken token = new AccessToken(accessToken, accessTokenSecret);
		Twitter twitter = twitterService.getTwitter();
		twitter.setOAuthAccessToken(token);
		request.getSession().setAttribute(Constants.TWITTER, twitter);
		
		return login(twitter,model,request,null,token);
	}
	
	
	public Twitter login(Twitter twitter, Model model, HttpServletRequest request, HttpServletResponse response, AccessToken token) throws TwitterException {
        handleLogin(twitter,model,request);
        
        //remove the request token from the session
        request.getSession().removeAttribute("requestToken");
        
        //add the access token to the session
        request.getSession().setAttribute(Constants.LOGIN_KEY, token.getToken());
        
        User user = twitter.showUser(twitter.getId());
        request.getSession().setAttribute(Constants.TWITTER_USER, user);
        
        if (response != null) {
        	addCookies(token,response);
        }        
        
        return twitter;
	}


	private void addCookies(AccessToken token, HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", token.getToken());
        cookie.setMaxAge(Constants.COOKIE_LIFE);
        response.addCookie(cookie);
        
        Cookie cookie2 = new Cookie("accessTokenSecret", token.getTokenSecret());
        cookie2.setMaxAge(Constants.COOKIE_LIFE);
        response.addCookie(cookie2);
	}
	
	
	private void setIpAddress(Twitter twitter, HttpServletRequest request, TwitterUser u) throws TwitterException {
    	String ipAddress = null;
    	
    	try {
        	IPLocation ipLocation = IPLocationReader.getIPLocation();    		
        	ipAddress = ipLocation.getIp();
        	request.getSession().setAttribute(Constants.IP_ADDRESS, ipAddress);
        	u.setIpAddress(ipAddress);
    	} catch (Exception ie) {
    		LOGGER.error("Problem getting IP address for user " + twitter.getScreenName(),ie);
    	}    	
	}
	
	
    private void handleLogin(Twitter twitter, Model model, HttpServletRequest request) throws TwitterException {    	
    	//just check to make sure we're happy
        String screenName = twitter.getScreenName();
        
        TwitterUser u = twitterUserRepository.findByScreenName(screenName);
        
        if (u == null) {
        	u = handleNewUser(model,screenName,request);
        } else {
        	handleUpdateUser(model,u,request);
        }
                
        UserConfig uc = u.getUserConfig();
        if (uc == null) {
        	uc = createUserConfig(u);
        }
        
    	//set the IP Address in the session
    	setIpAddress(twitter,request,u);
    
    	twitterUserRepository.save(u);
    	
    	checkForBadLogin(u);
    	
    	u.setUserConfig(uc);
    	
        LOGGER.info(screenName + " has logged on from ip " + u.getIpAddress());
    }
    
    
    private void checkForBadLogin(TwitterUser u) {
    	List<TwitterUser> others = twitterUserRepository.findByIpAddress(u.getIpAddress());
    	if (others != null) {
    		for (TwitterUser tu : others) {
    			if (!tu.getScreenName().equals(u.getScreenName())) {
    				Date lastLogin = tu.getLastLogin();
    				Calendar cal = Calendar.getInstance();
    				cal.add(Calendar.DAY_OF_MONTH, -1);
    				if (lastLogin.after(cal.getTime())) {
    					u.setBadLogin(true);
    					u.setBadLoginMessage("Another user has already logged in with this IP address within the last day.");
    				}
    			}
    		}
    	}
    }
    
    
    private UserConfig createUserConfig(TwitterUser u) {
    	UserConfig uc = new UserConfig();
    	uc.setMaxProjects(Constants.DEFAULT_MAX_PROJECTS);
    	uc.setMaxUrlsPerProject(Constants.DEFAULT_MAX_URLS_PER_PROJECT);
    	uc.setCurrentRefreshes(0);
    	uc.setMaxRefreshes(Constants.DEFAULT_MAX_REFRESHES);
    	uc.setRefreshDate(new Date());
    	uc.setUser(u);
    	
    	userConfigRepository.save(uc);
    	
    	return uc;
    }
 
    
    private TwitterUser handleNewUser(Model model, String screenName, HttpServletRequest request) {
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
    	
    	return u;
    }
    
    
    private void handleUpdateUser(Model model, TwitterUser user, HttpServletRequest request) {
    	user.setLastLogin(new Date());
    	model.addAttribute("twitterUser",user);   	
    	request.getSession().setAttribute(Constants.TWITTER_ENTITY, user);
    }
	
}
