package com.careydevelopment.twitterautomation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.careydevelopment.twitterautomation.util.SecurityHelper;

@Controller
public class AutoUnfollowController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoUnfollowController.class);
	
	private static final String LOCAL_HOST_FILE = "/etc/tomcat8/resources/localhost.properties";
	
	
    @RequestMapping("/autounfollow")
    public String autounfollow(Model model) {    	
    	model.addAttribute("localhost",getLocalHostPrefix());
    	model.addAttribute("autoUnfollowActive", "active");
    	
    	String username = SecurityHelper.getUsername();
    	
    	if (username != null && username.trim().length() > 1) {
    		model.addAttribute("username", username);
    	}
    	
        return "autounfollow";
    }
    
    
    /**
     * Necessary to prevent cross-domain problems with AJAX
     */
    private String getLocalHostPrefix() {
    	try {
	    	Properties props = new Properties();
	    	
	    	File file = new File(LOCAL_HOST_FILE);
	    	FileInputStream inStream = new FileInputStream(file);
	    	
	    	props.load(inStream);
	    	
	    	String localHostPrefix = props.getProperty("localhost.prefix");
	    	return localHostPrefix;
    	} catch (Exception e) {
    		e.printStackTrace();
    		LOGGER.error("Problem reading localhost file!");
    		throw new RuntimeException("Problem reading localhost file!");
    	}
    }
    
}
