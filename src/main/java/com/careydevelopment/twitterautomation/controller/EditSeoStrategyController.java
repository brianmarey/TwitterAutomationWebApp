package com.careydevelopment.twitterautomation.controller;

import java.util.ArrayList;
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
import com.careydevelopment.twitterautomation.jpa.entity.StrategyKeyword;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.repository.DomainSearchKeywordRepository;
import com.careydevelopment.twitterautomation.jpa.repository.ProjectRepository;
import com.careydevelopment.twitterautomation.jpa.repository.ProjectUrlRepository;
import com.careydevelopment.twitterautomation.jpa.repository.SeoStrategyRepository;
import com.careydevelopment.twitterautomation.jpa.repository.StrategyKeywordRepository;
import com.careydevelopment.twitterautomation.service.impl.LoginService;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.RecaptchaHelper;
import com.careydevelopment.twitterautomation.util.RoleHelper;

@Controller
public class EditSeoStrategyController {
	private static final Logger LOGGER = LoggerFactory.getLogger(EditSeoStrategyController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	ProjectUrlRepository projectUrlRepository;
		
	@Autowired
	DomainSearchKeywordRepository domainSearchKeywordRepository;
	
	@Autowired
	SeoStrategyRepository seoStrategyRepository;
	
	@Autowired
	StrategyKeywordRepository strategyKeywordRepository;
	
    @RequestMapping(value="/editSeoStrategy", method=RequestMethod.GET)
    public String createProjectUrl(HttpServletRequest request, Model model,
    	@RequestParam(value="strategyId", required=true) Long strategyId,
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
    	
    	SeoStrategy seoStrategy = seoStrategyRepository.findOne(strategyId);
    	if (seoStrategy == null) {
    		return "redirect:notAuthorized";
    	}
    	
    	ProjectUrl projectUrl = seoStrategy.getProjectUrl();    	
    	Project project = projectUrl.getProject();
    	
    	if (!project.getOwner().getId().equals(user.getId())) {
    		return "redirect:notAuthorized";
    	}
    	
    	if (!Constants.PROJECT_ACTIVE.equals(project.getStatus())) {
    		return "redirect:seoStrategyView?strategyId=" + seoStrategy.getId();
    	}
    	
    	if (!SeoStrategy.STATUS_OPEN.equals(seoStrategy.getStrategyStatus())) {
    		return "redirect:seoStrategyView?strategyId=" + seoStrategy.getId();
    	}
    	
    	List<DomainSearchKeyword> keywords = domainSearchKeywordRepository.findLatestByType(projectUrl, DomainSearchKeyword.ORGANIC);
    	model.addAttribute("keywords", keywords);
    	
    	if (keywords != null && keywords.size() > 0) {
    		model.addAttribute("hasKeywords",true);
    	}
    	
    	List<StrategyKeyword> strategyKeywords = seoStrategy.getStrategyKeywords();
    	model.addAttribute("strategyKeywords",strategyKeywords);
    	
    	model.addAttribute("project",project);
    	model.addAttribute("projectUrl",projectUrl);    	
    	model.addAttribute("seoStrategy",seoStrategy);
    	
    	model.addAttribute("projectsActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("projectsArrow", Constants.TWISTIE_OPEN);
    	
        return "editSeoStrategy";
    }
    
    
    @RequestMapping(value="/editSeoStrategy", method=RequestMethod.POST)
    public String createProjectSubmit(@Valid SeoStrategy seoStrategy, BindingResult bindingResult,
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

        String projectUrlIdS = request.getParameter("projectUrlId");
    	ProjectUrl projectUrl = projectUrlRepository.findOne(new Long(projectUrlIdS));
    	model.addAttribute("projectUrl",projectUrl);
        
    	String seoStrategyIdS = request.getParameter("strategyId");
    	SeoStrategy savedSeoStrategy = seoStrategyRepository.findOne(new Long(seoStrategyIdS));
    	
    	if (savedSeoStrategy == null) {
    		return "redirect:notAuthorized";
    	}
    	
    	boolean passedCaptcha = RecaptchaHelper.passedRecaptcha(request);
    	if (!passedCaptcha) model.addAttribute("captchaFail", true);
    	
        if (bindingResult.hasErrors() || !passedCaptcha) {
            return "editSeoStrategy";
        }
        
        persist(seoStrategy,projectUrl,savedSeoStrategy);
        
        return "redirect:/seoStrategyView?strategyId=" + savedSeoStrategy.getId();
    }
    
    
    private void persist(SeoStrategy seoStrategy, ProjectUrl projectUrl, SeoStrategy savedSeoStrategy) {
        savedSeoStrategy.setName(seoStrategy.getName());
        savedSeoStrategy.setPurchasedName(seoStrategy.getPurchasedName());
        savedSeoStrategy.setPurchasedUrl(seoStrategy.getPurchasedUrl());
        savedSeoStrategy.setStrategyDescription(seoStrategy.getStrategyDescription());
        savedSeoStrategy.setStrategySource(seoStrategy.getStrategySource());
        savedSeoStrategy.setStrategyStatus(seoStrategy.getStrategyStatus());
        savedSeoStrategy.setStrategyType(seoStrategy.getStrategyType());
        savedSeoStrategy.setStrategySuccess(seoStrategy.getStrategySuccess());
        
        seoStrategyRepository.save(savedSeoStrategy);
    }
    
}
