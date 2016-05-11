package com.careydevelopment.twitterautomation.controller;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.careydevelopment.propertiessupport.PropertiesFactory;
import com.careydevelopment.propertiessupport.PropertiesFile;
import com.careydevelopment.twitterautomation.domain.Tip;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.RoleHelper;
import com.careydevelopment.twitterautomation.util.TipsHelper;

import twitter4j.User;

@Controller
public class BlastFollowController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BlastFollowController.class);
	
	
    @RequestMapping("/blastfollow")
    public String blastfollow(HttpServletRequest request, Model model) {    	
    	User user = (User)request.getSession().getAttribute(Constants.TWITTER_USER);
    	
    	if (user == null) {
    		return "redirect:notLoggedIn";
    	}

    	TwitterUser twitterUser = (TwitterUser)request.getSession().getAttribute(Constants.TWITTER_ENTITY);
    	
    	if (!RoleHelper.isAuthorized(twitterUser, "Basic")) {
    		return "redirect:notAuthorized";
    	}
    	
    	model.addAttribute("localhost",getLocalHostPrefix());
    	model.addAttribute("blastFollowActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("dashboardActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("blastFollowArrow", Constants.TWISTIE_OPEN);
    
    	setDisplayAttributes(model,user);
    	
        return "blastfollow";
    }
    
    
    private void setDisplayAttributes(Model model, User user) {
    	DecimalFormat df = new DecimalFormat("###.##");
    	df.setRoundingMode(RoundingMode.FLOOR);
    	
    	double ratio = ((double)user.getFollowersCount())/((double)user.getFriendsCount());
    	LOGGER.info("ratio is " + ratio);
    	
    	String ratioS = df.format(ratio);
    	model.addAttribute("ratio",ratioS);
    	
    	TipsHelper helper = new TipsHelper(user);
    	List<Tip> tips = helper.getTips();
    	model.addAttribute("tips",tips);
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
