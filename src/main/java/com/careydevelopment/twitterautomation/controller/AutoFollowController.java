package com.careydevelopment.twitterautomation.controller;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.careydevelopment.propertiessupport.PropertiesFactory;
import com.careydevelopment.propertiessupport.PropertiesFile;
import com.careydevelopment.twitterautomation.util.Constants;

@Controller
public class AutoFollowController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoFollowController.class);
	
	
    @RequestMapping("/autofollow")
    public String autofollow(@RequestParam(value="action", required=false) String action, 
    	HttpServletRequest request, Model model) { 
    	
    	if (request.getSession().getAttribute(Constants.LOGIN_KEY) == null) {
    		return "redirect:notLoggedIn";
    	}
    	
    	model.addAttribute("localhost",getLocalHostPrefix());
    	model.addAttribute("autofollowActive", "active");
    	
    	if (action != null) {
    		model.addAttribute("action",action);
    	}
    	
        return "autofollow";
    }
    
    
    /**
     * Necessary to prevent cross-domain problems with AJAX
     */
    private String getLocalHostPrefix() {
    	try {
    		Properties props = PropertiesFactory.getProperties(PropertiesFile.LOCALHOST_PROPERTIES);
	    	String localHostPrefix = props.getProperty("localhost.prefix");
	    	return localHostPrefix;
    	} catch (Exception e) {
    		e.printStackTrace();
    		LOGGER.error("Problem reading localhost file!");
    		throw new RuntimeException("Problem reading localhost file!");
    	}
    }
    
}
