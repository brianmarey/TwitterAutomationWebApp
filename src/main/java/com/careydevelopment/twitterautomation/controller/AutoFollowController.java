package com.careydevelopment.twitterautomation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AutoFollowController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoFollowController.class);
	
	private static final String LOCAL_HOST_FILE = "/etc/tomcat8/resources/localhost.properties";
	
    @RequestMapping("/autofollow")
    public String autofollow(Model model) {
    	
    	try {
	    	Properties props = new Properties();
	    	
	    	File file = new File(LOCAL_HOST_FILE);
	    	FileInputStream inStream = new FileInputStream(file);
	    	
	    	props.load(inStream);
	    	
	    	String localHostPrefix = props.getProperty("localhost.prefix");
	    	
	    	model.addAttribute("localhost", localHostPrefix);
    	} catch (Exception e) {
    		e.printStackTrace();
    		LOGGER.error("Problem reading localhost file!");
    		throw new RuntimeException("Problem reading localhost file!");
    	}
    	
        return "autofollow";
    }
}
