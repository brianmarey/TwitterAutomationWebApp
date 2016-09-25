package com.careydevelopment.twitterautomation.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.entity.UserConfig;
import com.careydevelopment.twitterautomation.jpa.repository.UserConfigRepository;
import com.careydevelopment.twitterautomation.service.impl.LoginService;
import com.careydevelopment.twitterautomation.util.Constants;

@Controller
public class AgreeTosController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgreeTosController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	UserConfigRepository userConfigRepository;
	
	@RequestMapping("/agreeTos")
	public String agreeTos(HttpServletRequest request, Model model,
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
    	
    	if (user.isBadLogin()) {
    		return "redirect:badLogin";
    	}

    	UserConfig uc = user.getUserConfig();
    	uc.setTosAgreement("true");
    	
    	userConfigRepository.save(uc);
		
        return "redirect:seoplayhouse";
	}
}
