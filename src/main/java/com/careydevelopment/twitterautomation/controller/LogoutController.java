package com.careydevelopment.twitterautomation.controller;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.careydevelopment.twitterautomation.util.Constants;

@Controller
public class LogoutController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogoutController.class);
	 
    @RequestMapping("/logout")
    public String login(HttpServletRequest request, Model model) {
    	
    	request.getSession().removeAttribute(Constants.LOGIN_KEY);
    	request.getSession().removeAttribute(Constants.TWITTER);
    	request.getSession().removeAttribute(Constants.TWITTER_USER);
    	
        return "notLoggedIn";
    }
}
