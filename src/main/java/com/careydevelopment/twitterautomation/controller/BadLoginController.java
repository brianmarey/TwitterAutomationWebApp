package com.careydevelopment.twitterautomation.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.util.Constants;

@Controller
public class BadLoginController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BadLoginController.class);
	
    @RequestMapping("/badLogin")
    public String notAuthorized(HttpServletRequest request, Model model) {    	    
    	TwitterUser user = (TwitterUser)request.getSession().getAttribute(Constants.TWITTER_ENTITY);
    	model.addAttribute("user",user);
        return "badLogin";
    }
        
}
