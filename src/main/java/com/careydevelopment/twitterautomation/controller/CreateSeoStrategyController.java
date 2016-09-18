package com.careydevelopment.twitterautomation.controller;

import java.util.Date;
import java.util.List;

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

import com.careydevelopment.twitterautomation.jpa.entity.DomainSearchKeyword;
import com.careydevelopment.twitterautomation.jpa.entity.Project;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.entity.SeoStrategy;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.repository.DomainSearchKeywordRepository;
import com.careydevelopment.twitterautomation.jpa.repository.ProjectRepository;
import com.careydevelopment.twitterautomation.jpa.repository.ProjectUrlRepository;
import com.careydevelopment.twitterautomation.service.UrlMetricsService;
import com.careydevelopment.twitterautomation.service.impl.LoginService;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.RecaptchaHelper;
import com.careydevelopment.twitterautomation.util.RefreshUtil;
import com.careydevelopment.twitterautomation.util.RoleHelper;
import com.careydevelopment.twitterautomation.util.UrlHelper;

@Controller
public class CreateSeoStrategyController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateSeoStrategyController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	ProjectUrlRepository projectUrlRepository;
	
	@Autowired
	UrlMetricsService urlMetricsService;
	
	@Autowired
	RefreshUtil refreshUtil;
	
	@Autowired
	DomainSearchKeywordRepository domainSearchKeywordRepository;
	
    @RequestMapping(value="/createSeoStrategy", method=RequestMethod.GET)
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
    	
    	ProjectUrl projectUrl = projectUrlRepository.findOne(projectUrlId);
    	
    	Project project = projectUrl.getProject();
    	
    	if (!project.getOwner().getId().equals(user.getId())) {
    		return "redirect:notAuthorized";
    	}
    	
    	List<DomainSearchKeyword> keywords = domainSearchKeywordRepository.findLatestByType(projectUrl, DomainSearchKeyword.ORGANIC);
    	model.addAttribute("keywords", keywords);
    	
    	if (keywords != null && keywords.size() > 0) {
    		model.addAttribute("hasKeywords",true);
    	}
    	
    	model.addAttribute("project",project);
    	model.addAttribute("projectUrl",projectUrl);
    	
    	SeoStrategy seoStrategy = new SeoStrategy();
    	model.addAttribute("seoStrategy",seoStrategy);
    	
    	model.addAttribute("projectsActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("projectsArrow", Constants.TWISTIE_OPEN);
    	
        return "createSeoStrategy";
    }
    
    
    @RequestMapping(value="/createSeoStrategy", method=RequestMethod.POST)
    public String createProjectSubmit(@Valid ProjectUrl projectUrl, BindingResult bindingResult,
    	HttpServletRequest request, Model model) { 
    	
    	TwitterUser user = (TwitterUser)request.getSession().getAttribute(Constants.TWITTER_ENTITY);
    	
    	if (user == null) {
    		return "redirect:notLoggedIn";		
    	}
    	
    	if (!RoleHelper.isAuthorized(user, Constants.AUTHORIZATION_BASIC)) {
    		return "redirect:notAuthorized";
    	}
    	
        String projectIdS = request.getParameter("projectId");
    	Project project = projectRepository.findOne(new Long(projectIdS));
    	model.addAttribute("project",project);
    	
    	if (!project.getOwner().getId().equals(user.getId())) {
    		return "redirect:notAuthorized";
    	}
    	
    	if (refreshUtil.isMaxedOut(user.getUserConfig())) {
    		return "redirect:maxedOutRefreshes";
    	}
    	
        List<ProjectUrl> urls = projectUrlRepository.findByProject(project);
    
        if (urls != null) {
        	if (urls.size() >= user.getUserConfig().getMaxUrlsPerProject()) {
	        	model.addAttribute("maxUrlsExceeded",true);
	        	model.addAttribute("maxUrls",user.getUserConfig().getMaxUrlsPerProject());
	        	return "createProjectUrl";
        	}
        	
        	if (urls.contains(projectUrl)) {
        		LOGGER.warn("User trying to create a project url that already exists");
        		return "createProjectUrl";
        	}
        }
       
    	
    	boolean passedCaptcha = RecaptchaHelper.passedRecaptcha(request);
    	if (!passedCaptcha) model.addAttribute("captchaFail", true);
    	
        if (bindingResult.hasErrors() || !passedCaptcha) {
            return "createProjectUrl";
        }

        if (!UrlHelper.isValidUrl(projectUrl.getUrl())) {
        	model.addAttribute("invalidUrl",true);
        	return "createProjectUrl";
        }                
        
        projectUrl.setProject(project);
        projectUrl.setReportDate(new Date());
        projectUrl = projectUrlRepository.save(projectUrl);
        
        urlMetricsService.saveUrlMetrics(projectUrl);
        
        return "redirect:/projectView?projectId=" + projectUrl.getProject().getId();
    }
}
