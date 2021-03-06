package com.careydevelopment.twitterautomation.controller;

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

import com.careydevelopment.twitterautomation.jpa.entity.Project;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.repository.ProjectUrlRepository;
import com.careydevelopment.twitterautomation.service.SerpBookService;
import com.careydevelopment.twitterautomation.service.impl.LoginService;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.RoleHelper;

@Controller
public class DeleteProjectUrlController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteProjectUrlController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	ProjectUrlRepository projectUrlRepository;
	
	@Autowired
	SerpBookService serpBookService;
	
    @RequestMapping(value="/deleteProjectUrl", method=RequestMethod.GET)
    public String createProjectUrl(HttpServletRequest request, Model model,
    	@RequestParam(value="projectUrlId", required=true) Long projectUrlId,
    	@CookieValue(value="accessToken" , defaultValue ="") String accessToken,
    	@CookieValue(value="accessTokenSecret" , defaultValue ="") String accessTokenSecret) { 
    	
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
    	
    	Project project = projectUrl.getProject();
    	
    	if (!project.getOwner().getId().equals(user.getId())) {
    		return "redirect:notAuthorized";
    	}
    	
    	serpBookService.deleteCategory(projectUrl);
    	projectUrlRepository.delete(projectUrl);
    	
        return "redirect:projectView?projectId=" + project.getId();
    }
}
