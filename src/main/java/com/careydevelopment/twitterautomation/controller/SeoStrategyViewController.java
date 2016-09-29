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

import com.careydevelopment.twitterautomation.jpa.entity.DomainSearchKeyword;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.entity.SeoStrategy;
import com.careydevelopment.twitterautomation.jpa.entity.StrategyKeyword;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.repository.DomainSearchKeywordRepository;
import com.careydevelopment.twitterautomation.jpa.repository.ProjectUrlRepository;
import com.careydevelopment.twitterautomation.jpa.repository.SeoStrategyRepository;
import com.careydevelopment.twitterautomation.service.impl.LoginService;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.RefreshUtil;
import com.careydevelopment.twitterautomation.util.RoleHelper;

@Controller
public class SeoStrategyViewController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SeoStrategyViewController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	ProjectUrlRepository projectUrlRepository;

	@Autowired
	DomainSearchKeywordRepository domainSearchKeywordRepository;
	
	@Autowired
	SeoStrategyRepository seoStrategyRepository;
	
	@Autowired
	RefreshUtil refreshUtil;
	
	
    @RequestMapping(value="/seoStrategyView", method=RequestMethod.GET)
    public String projectView(HttpServletRequest request, Model model,
    	@CookieValue(value="accessToken" , defaultValue ="") String accessToken,
    	@CookieValue(value="accessTokenSecret" , defaultValue ="") String accessTokenSecret,
    	@RequestParam(value="strategyId", required=true) Long strategyId) { 
    	
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
    	
    	if (projectUrl == null) {
    		return "redirect:notAuthorized";
    	}
    	
    	if (!projectUrl.getProject().getOwner().getId().equals(user.getId())) {
    		return "redirect:notAuthorized";
    	}
    	
    	handleKeywords(seoStrategy,projectUrl,model);
    	
    	refreshUtil.setEligibleForRefresh(model,projectUrl);
    	    	
    	model.addAttribute("projectUrl",projectUrl);
    	model.addAttribute("seoStrategy", seoStrategy);
    	
    	model.addAttribute("projectsActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("projectsArrow", Constants.TWISTIE_OPEN);
    	
        return "seoStrategyView";
    }

    
    private void handleKeywords(SeoStrategy seoStrategy, ProjectUrl projectUrl, Model model) {
    	List<DomainSearchKeyword> domainSearchKeywords = domainSearchKeywordRepository.findLatestByType(projectUrl, DomainSearchKeyword.ORGANIC);
    	
    	List<StrategyKeyword> keywords = seoStrategy.getStrategyKeywords();
    	if (keywords != null) {
    		for (StrategyKeyword keyword : keywords) {
    			String key = keyword.getKeyword();
    			Integer currentRank = getCurrentRank(key, domainSearchKeywords);
    			keyword.setCurrentRank(currentRank);
    		}
    	}
    	
    	model.addAttribute("keywords",keywords);
    	    	
    	List<DomainSearchKeyword> domainMobileSearchKeywords = domainSearchKeywordRepository.findLatestByType(projectUrl, DomainSearchKeyword.ORGANIC_MOBILE);
    	
    	List<StrategyKeyword> mobileKeywords = seoStrategy.getStrategyKeywords();
    	if (mobileKeywords != null) {
    		for (StrategyKeyword keyword : mobileKeywords) {
    			String key = keyword.getKeyword();
    			Integer currentRank = getCurrentRank(key, domainMobileSearchKeywords);
    			keyword.setCurrentMobileRank(currentRank);
    		}
    	}
    	
    	model.addAttribute("mobileKeywords",mobileKeywords);
    }
    
    
    private Integer getCurrentRank(String key, List<DomainSearchKeyword> keywords) {
    	Integer rank = 0;
    	
    	for (DomainSearchKeyword keyword : keywords) {
    		if (keyword.getKeyword().equals(key)) {
    			rank = keyword.getPosition();
    			break;
    		}
    	}
    	
    	return rank;
    }
}
