package com.careydevelopment.twitterautomation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.careydevelopment.twitterautomation.util.Constants;

@Controller
public class AutoUnfollowController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoUnfollowController.class);
	
	private static final String LOCAL_HOST_FILE = "/etc/tomcat8/resources/localhost.properties";
	
	
    @RequestMapping("/autounfollow")
    public String autounfollow(@RequestParam(value="action", required=false) String action, 
    	HttpServletRequest request, Model model) {    	
    	
    	if (request.getSession().getAttribute(Constants.TWITTER_USER) == null) {
    		return "redirect:notLoggedIn";
    	}
    	
    	model.addAttribute("localhost",getLocalHostPrefix());
    	model.addAttribute("autounfollowActive", "active");
    	
    	if (action != null) {
    		model.addAttribute("action",action);
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
