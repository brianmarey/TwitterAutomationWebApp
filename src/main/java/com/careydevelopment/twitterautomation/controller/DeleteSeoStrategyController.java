package com.careydevelopment.twitterautomation.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.careydevelopment.twitterautomation.jpa.entity.Project;
import com.careydevelopment.twitterautomation.jpa.entity.SeoStrategy;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.repository.SeoStrategyRepository;
import com.careydevelopment.twitterautomation.service.impl.LoginService;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.RoleHelper;

@Controller
public class DeleteSeoStrategyController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteSeoStrategyController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	SeoStrategyRepository seoStrategyRepository;
		
    @RequestMapping(value="/deleteSeoStrategy", method=RequestMethod.GET)
    public String createProjectUrl(HttpServletRequest request, Model model,
    	@RequestParam(value="strategyId", required=true) Long strategyId,
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

    	if (!RoleHelper.isAuthorized(user, "Basic")) {
    		return "redirect:notAuthorized";
    	}
    	
    	if (user.isBadLogin()) {
    		return "redirect:badLogin";
    	}
    	
    	if (user.getUserConfig() != null && !"true".equalsIgnoreCase(user.getUserConfig().getTosAgreement())) {
    		return "redirect:seoplayhouse";
    	}
    	
    	SeoStrategy seoStrategy = seoStrategyRepository.findOne(strategyId);
    	
    	Project project = seoStrategy.getProjectUrl().getProject();
    	
    	if (!project.getOwner().getId().equals(user.getId())) {
    		return "redirect:notAuthorized";
    	}
    	
    	seoStrategyRepository.delete(seoStrategy);
    	
        return "redirect:seoStrategies";
    }
}
