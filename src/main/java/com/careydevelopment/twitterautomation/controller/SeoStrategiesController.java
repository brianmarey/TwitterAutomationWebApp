package com.careydevelopment.twitterautomation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.careydevelopment.propertiessupport.PropertiesFactory;
import com.careydevelopment.propertiessupport.PropertiesFile;
import com.careydevelopment.twitterautomation.jpa.entity.Project;
import com.careydevelopment.twitterautomation.jpa.entity.SeoStrategy;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.repository.SeoStrategyRepository;
import com.careydevelopment.twitterautomation.service.impl.LoginService;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.RoleHelper;

@Controller
public class SeoStrategiesController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SeoStrategiesController.class);
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private SeoStrategyRepository seoStrategyRepository;

	
    @RequestMapping("/seoStrategies")
    public String blastfollow(HttpServletRequest request, Model model,
    		@RequestParam(value="status", required=false) String status,
    		@RequestParam(value="success", required=false) String success,
    		@CookieValue(value="accessToken" , defaultValue ="") String accessToken,
        	@CookieValue(value="accessTokenSecret" , defaultValue ="") String accessTokenSecret) {    	
    	
    	TwitterUser user = (TwitterUser)request.getSession().getAttribute(Constants.TWITTER_ENTITY);    	
    	
    	if (user == null) {
    		if (!accessToken.equals("") && !accessTokenSecret.equals("")) {
        		//this user has cookies and might be able to login
        		try {
        			loginService.login(model, request, accessToken, accessTokenSecret);
        			user = (TwitterUser)request.getSession().getAttribute(Constants.TWITTER_ENTITY);
        		} catch (Exception e) {
        			return "redirect:notLoggedIn";
        		}
        	} else {        	
        		return "redirect:notLoggedIn";		
        	}
    	}

    	model.addAttribute("twitterUser",user);
    	
    	if (!RoleHelper.isAuthorized(user, "Basic")) {
    		return "redirect:notAuthorized";
    	}
    	
    	if (user.isBadLogin()) {
    		return "redirect:badLogin";
    	}
    	
    	List<SeoStrategy> strategies = getStrategies(user,status,success);
    	model.addAttribute("strategies",strategies);
    	
    	if (strategies != null) {
    		model.addAttribute("numberOfStrategies", strategies.size());
    	} else {
    		model.addAttribute("numberOfStrategies", 0);
    	}
    	
    	model.addAttribute("projectsActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("seoStrategiesActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("projectsArrow", Constants.TWISTIE_OPEN);
    	    	
        return "seoStrategies";
    }

    
    private List<SeoStrategy> getStrategies(TwitterUser user, String status, String success) {
    	List<SeoStrategy> strategies = new ArrayList<SeoStrategy>();
    	
    	if (status == null || status.trim().length() < 3) {
    		if (success == null  || success.trim().length() < 3) {
    			strategies = seoStrategyRepository.findByOwner(user.getId());
    		} else {
    			strategies = seoStrategyRepository.findByOwnerAndSuccess(user.getId(), success);
    		}
    	} else {
    		if (success == null  || success.trim().length() < 3) {
    			strategies = seoStrategyRepository.findByOwnerAndStatus(user.getId(), status);
    		} else {
    			strategies = seoStrategyRepository.findByOwnerAndSuccessAndStatus(user.getId(), success, status);
    		}    		
    	}
    	
    	return strategies;
    }
}
