package com.careydevelopment.twitterautomation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.careydevelopment.twitterautomation.jpa.entity.Project;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.repository.ProjectRepository;
import com.careydevelopment.twitterautomation.service.LoginService;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.RecaptchaHelper;
import com.careydevelopment.twitterautomation.util.RoleHelper;

@Controller
public class CreateProjectUrlController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateProjectUrlController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	ProjectRepository projectRepository;
	
	
    @RequestMapping(value="/createProjectUrl", method=RequestMethod.GET)
    public String createProjectUrl(HttpServletRequest request, Model model,
    	@RequestParam(value="projectId", required=true) Long projectId,
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
    	
    	Project project = projectRepository.findOne(projectId);
    	
    	ProjectUrl projectUrl = new ProjectUrl();
    	projectUrl.setProject(project);
    	model.addAttribute("projectUrl",projectUrl);
    	
        return "createProjectUrl";
    }
    
    
    @RequestMapping(value="/createProjectUrl", method=RequestMethod.POST)
    public String createProjectSubmit(@Valid Project project, BindingResult bindingResult,
    	HttpServletRequest request, Model model) { 
    	
    	TwitterUser user = (TwitterUser)request.getSession().getAttribute(Constants.TWITTER_ENTITY);
    	
    	if (user == null) {
    		return "redirect:notLoggedIn";		
    	}

    	if (!RoleHelper.isAuthorized(user, Constants.AUTHORIZATION_BASIC)) {
    		return "redirect:notAuthorized";
    	}
    	
    	boolean passedCaptcha = RecaptchaHelper.passedRecaptcha(request);
    	if (!passedCaptcha) model.addAttribute("captchaFail", true);
    	
        if (bindingResult.hasErrors() || !passedCaptcha) {
            return "createaproject";
        }
    	
        project.setOwner(user);
        project.setStatus(Constants.PROJECT_ACTIVE);
        projectRepository.save(project);
        
        return "redirect:/projectView?projectId=" + project.getId();
    }
}
