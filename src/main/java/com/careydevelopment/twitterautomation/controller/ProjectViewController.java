package com.careydevelopment.twitterautomation.controller;

import java.util.List;

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
import com.careydevelopment.twitterautomation.jpa.repository.ProjectRepository;
import com.careydevelopment.twitterautomation.jpa.repository.ProjectUrlRepository;
import com.careydevelopment.twitterautomation.service.impl.LoginService;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.RoleHelper;

@Controller
public class ProjectViewController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectViewController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	ProjectUrlRepository projectUrlRepository;
	
    @RequestMapping(value="/projectView", method=RequestMethod.GET)
    public String projectView(HttpServletRequest request, Model model,
    	@CookieValue(value="accessToken" , defaultValue ="") String accessToken,
    	@CookieValue(value="accessTokenSecret" , defaultValue ="") String accessTokenSecret,
    	@RequestParam(value="projectId", required=true) Long projectId) { 
    	
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
    	
    	Project project = projectRepository.findOne(projectId);
    	
    	if (!project.getOwner().getId().equals(user.getId())) {
    		return "redirect:notAuthorized";
    	}
    	
    	model.addAttribute("project",project);
    	
    	List<ProjectUrl> projectUrls = projectUrlRepository.findByProject(project);
    	if (projectUrls.size() == 0) {
    		model.addAttribute("noUrls",true);
    	}
    	
    	model.addAttribute("urls",projectUrls);
    	
        return "projectView";
    }
}
