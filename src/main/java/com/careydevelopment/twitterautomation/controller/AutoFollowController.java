package com.careydevelopment.twitterautomation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AutoFollowController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoFollowController.class);
	
    @RequestMapping("/autofollow")
    public String autofollow(Model model) {
    	
    	//LOGGER.
    	
        return "autofollow";
    }
}
