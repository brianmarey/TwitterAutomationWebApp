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

import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.RoleHelper;

@Controller
public class AutoRefollowController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoRefollowController.class);
	
	private static final String LOCAL_HOST_FILE = "/etc/tomcat8/resources/localhost.properties";
	
	
    @RequestMapping("/autorefollow")
    public String autounfollow(@RequestParam(value="action", required=false) String action, 
    	HttpServletRequest request, Model model) {
    	
    	TwitterUser user = (TwitterUser)request.getSession().getAttribute(Constants.TWITTER_ENTITY);
    	
    	if (user == null) {
    		return "redirect:notLoggedIn";
    	}

    	if (!RoleHelper.isAuthorized(user, "Basic")) {
    		return "redirect:notAuthorized";
    	}

    	model.addAttribute("localhost",getLocalHostPrefix());
    	model.addAttribute("blastFollowActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("autoRefollowActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("blastFollowArrow", Constants.TWISTIE_OPEN);
    	
    	if (action != null) {
    		model.addAttribute("action",action);
    	}
    	
        return "autorefollow";
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
    		LOGGER.error("Problem reading localhost file!",e);
    		throw new RuntimeException("Problem reading localhost file!");
    	}
    }
    
}
