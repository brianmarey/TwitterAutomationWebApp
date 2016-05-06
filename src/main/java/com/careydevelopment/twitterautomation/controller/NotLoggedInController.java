package com.careydevelopment.twitterautomation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.careydevelopment.twitterautomation.util.SecurityHelper;

@Controller
public class NotLoggedInController {
	private static final Logger LOGGER = LoggerFactory.getLogger(NotLoggedInController.class);
	
    @RequestMapping("/notLoggedIn")
    public String home(Model model) {    	    
    	//model.addAttribute("homeActive", "active");
        return "notLoggedIn";
    }
        
}
