package com.careydevelopment.twitterautomation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MaxedOutRefreshesController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MaxedOutRefreshesController.class);
	
    @RequestMapping("/maxedOutRefreshes")
    public String notAuthorized(Model model) {    	    
    	//model.addAttribute("homeActive", "active");
        return "maxedOutRefreshes";
    }
}
