package com.careydevelopment.twitterautomation.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.repository.ProjectUrlRepository;
import com.careydevelopment.twitterautomation.service.UrlMetricsService;
import com.careydevelopment.twitterautomation.service.impl.LoginService;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.RefreshUtil;
import com.careydevelopment.twitterautomation.util.RoleHelper;

@Controller
public class RefreshController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RefreshController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	ProjectUrlRepository projectUrlRepository;
	
	@Autowired
	UrlMetricsService urlMetricsService;
	
	@Autowired
	RefreshUtil refreshUtil;
	
    @RequestMapping(value="/refreshReport", method=RequestMethod.GET)
    public String projectView(HttpServletRequest request, Model model,
    	@CookieValue(value="accessToken" , defaultValue ="") String accessToken,
    	@CookieValue(value="accessTokenSecret" , defaultValue ="") String accessTokenSecret,
    	@RequestParam(value="projectUrlId", required=true) Long projectUrlId) { 
    	
    	TwitterUser user = (TwitterUser)request.getSession().getAttribute(Constants.TWITTER_ENTITY);
    	
    	if (user == null) {
    		if (!accessToken.equals("") && !accessTokenSecret.equals("")) {
        		//this user has cookies and might be able to login
        		try {
        			loginService.login(model, request, accessToken, accessTokenSecret);
        			user = (TwitterUser)request.getSession().getAttribute(Constants.TWITTER_ENTITY);
        		} catch (Exception e) {
        			return "redirect:notLoggedIn";
        		}
        	} else {        	
        		return "redirect:notLoggedIn";		
        	}    	
    	}

    	if (!RoleHelper.isAuthorized(user, "Basic")) {
    		return "redirect:notAuthorized";
    	}
    	
    	if (user.isBadLogin()) {
    		return "redirect:badLogin";
    	}
    	
    	if (user.getUserConfig() != null && !"true".equalsIgnoreCase(user.getUserConfig().getTosAgreement())) {
    		return "redirect:seoplayhouse";
    	}
    	
    	ProjectUrl projectUrl = projectUrlRepository.findOne(projectUrlId);
    	
    	if (projectUrl == null) {
    		return "redirect:notAuthorized";
    	}
    	
    	if (!projectUrl.getProject().getOwner().getId().equals(user.getId())) {
    		return "redirect:notAuthorized";
    	}
    	
    	if (!Constants.PROJECT_ACTIVE.equals(projectUrl.getProject().getStatus())) {
    		return "redirect:projectUrlView?projectUrlId=" + projectUrl.getId();
    	}
    	
    	model.addAttribute("projectUrl",projectUrl);
    	
    	if (refreshUtil.isMaxedOut(user.getUserConfig())) {
    		return "redirect:maxedOutRefreshes";
    	}
    	
    	if (isEligibleForRefresh(projectUrl)) {
    		projectUrl.setReportDate(new Date());
    		projectUrlRepository.save(projectUrl);
    		urlMetricsService.saveUrlMetrics(projectUrl);
    	}
    	
        return "redirect: projectUrlView?projectUrlId=" + projectUrl.getId();
    }

    
    private boolean isEligibleForRefresh(ProjectUrl projectUrl) {    	
    	Date reportDate = projectUrl.getReportDate();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(reportDate);
    	cal.add(Calendar.DAY_OF_MONTH, 1);
    	
    	Date now = new Date();
    	
    	LOGGER.info("Comparing " + now + " to " + cal.getTime());
    	
    	return (now.after(cal.getTime())); 
    }
}
