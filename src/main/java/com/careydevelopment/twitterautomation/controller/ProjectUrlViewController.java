package com.careydevelopment.twitterautomation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.careydevelopment.twitterautomation.jpa.entity.AnchorTextData;
import com.careydevelopment.twitterautomation.jpa.entity.BacklinkData;
import com.careydevelopment.twitterautomation.jpa.entity.CompetitorSearch;
import com.careydevelopment.twitterautomation.jpa.entity.DomainSearchKeyword;
import com.careydevelopment.twitterautomation.jpa.entity.LinkType;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.repository.AnchorTextDataRepository;
import com.careydevelopment.twitterautomation.jpa.repository.BacklinkDataRepository;
import com.careydevelopment.twitterautomation.jpa.repository.CompetitorSearchRepository;
import com.careydevelopment.twitterautomation.jpa.repository.DomainSearchKeywordRepository;
import com.careydevelopment.twitterautomation.jpa.repository.ProjectUrlRepository;
import com.careydevelopment.twitterautomation.service.impl.LoginService;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.RoleHelper;

@Controller
public class ProjectUrlViewController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectUrlViewController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	ProjectUrlRepository projectUrlRepository;
	
	@Autowired
	BacklinkDataRepository backlinkDataRepository;

	@Autowired
	DomainSearchKeywordRepository domainSearchKeywordRepository;
	
	@Autowired
	CompetitorSearchRepository competitorSearchRepository;
	
	@Autowired
	AnchorTextDataRepository anchorTextDataRepository;
	
    @RequestMapping(value="/projectUrlView", method=RequestMethod.GET)
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
    	
    	ProjectUrl projectUrl = projectUrlRepository.findOne(projectUrlId);
    	
    	if (projectUrl == null) {
    		return "redirect:notAuthorized";
    	}
    	
    	if (!projectUrl.getProject().getOwner().getId().equals(user.getId())) {
    		return "redirect:notAuthorized";
    	}
    	
    	model.addAttribute("projectUrl",projectUrl);
    	
    	List<BacklinkData> backlinks = backlinkDataRepository.findByUrl(projectUrl);
    	
    	if (backlinks != null) {
        	List<BacklinkData> top20Backlinks = (backlinks.size() > 19) ? backlinks.subList(0, 19) : backlinks;
        	model.addAttribute("backlinks", top20Backlinks);    		
    	}
    	
    	setBacklinkStats(model, backlinks);
    	    	    	    	
    	Pageable topTen = new PageRequest(0, 10);
    	List<DomainSearchKeyword> organicKeywords = domainSearchKeywordRepository.findLatestByType(projectUrl, DomainSearchKeyword.ORGANIC, topTen);
    	List<DomainSearchKeyword> paidKeywords = domainSearchKeywordRepository.findLatestByType(projectUrl, DomainSearchKeyword.PAID, topTen);
    	
    	model.addAttribute("organicKeywords",organicKeywords);
    	model.addAttribute("paidKeywords",paidKeywords);

    	List<CompetitorSearch> organicCompetitors = competitorSearchRepository.findLatestByType(projectUrl, CompetitorSearch.ORGANIC, topTen);
    	List<CompetitorSearch> paidCompetitors = competitorSearchRepository.findLatestByType(projectUrl, CompetitorSearch.PAID, topTen);
    	
    	model.addAttribute("organicCompetitors",organicCompetitors);
    	model.addAttribute("paidCompetitors",paidCompetitors);
    	
    	List<AnchorTextData> anchorTextData = anchorTextDataRepository.findTop20ByProjectUrlOrderByIdAsc(projectUrl);
    	model.addAttribute("anchorTextData", anchorTextData);
    	
    	model.addAttribute("projectsActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("projectsArrow", Constants.TWISTIE_OPEN);

        return "projectUrlView";
    }
    
    
    private void setBacklinkStats (Model model, List<BacklinkData> backlinks) {
    	Integer noFollowLinks = 0;
    	Integer doFollowLinks = 0;
    	
    	Integer textLinks = 0;
    	Integer imageLinks = 0;
    	Integer redirects = 0;
    	Integer frames = 0;
    	Integer mentions = 0;
    	    	
    	for (BacklinkData backlink : backlinks) {
    		if (backlink.getFlagNoFollow()) {
    			noFollowLinks++;
    		} else {
    			doFollowLinks++;
    		}
    		
    		if (LinkType.FRAME.equals(backlink.getLinkType())) {
    			frames++;
    		} else if (LinkType.MENTION.equals(backlink.getLinkType())) {
    			mentions++;
    		} else if (LinkType.REDIRECT.equals(backlink.getLinkType())) {
    			redirects++;
    		} else if (LinkType.IMAGE_LINK.equals(backlink.getLinkType())) {
    			imageLinks++;
    		} else if (LinkType.TEXT_LINK.equals(backlink.getLinkType())) {
    			textLinks++;
    		}
    	}
    	
    	model.addAttribute("noFollowLinks",noFollowLinks);
    	model.addAttribute("doFollowLinks",doFollowLinks);
    	model.addAttribute("textLinks",textLinks);
    	model.addAttribute("imageLinks",imageLinks);
    	model.addAttribute("redirects",redirects);
    	model.addAttribute("frames",frames);
    	model.addAttribute("mentions",mentions);
    }
}
