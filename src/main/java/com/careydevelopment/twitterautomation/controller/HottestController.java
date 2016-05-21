package com.careydevelopment.twitterautomation.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.careydevelopment.twitterautomation.jpa.entity.DomainAd;
import com.careydevelopment.twitterautomation.jpa.entity.NativeAd;
import com.careydevelopment.twitterautomation.jpa.repository.CalenderUtil;
import com.careydevelopment.twitterautomation.jpa.repository.DomainAdRepository;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.NativeAdUtil;
import com.careydevelopment.twitterautomation.util.PaginationHelper;

@Controller
public class HottestController implements Constants {

	@Autowired
	DomainAdRepository domainAdRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HottestController.class);
	
    @RequestMapping("/hottestNativeAds")
    public String latest(Model model, @RequestParam(value="pageNum", required=false) String pageNum) {    	    
    	int page = PaginationHelper.getPage(pageNum);
    	
    	Pageable pageable = new PageRequest(page,RESULTS_PER_PAGE);
    	Date yesterday = CalenderUtil.getXDaysAgo(1);
    	Page<DomainAd> ads = domainAdRepository.findHottestNativeAds(pageable,yesterday);
    	
    	List<NativeAd> nads = NativeAdUtil.getNativeAdsFromDomainAds(ads);
    	
    	PaginationHelper.setPagination(ads,model,page);
    	
    	model.addAttribute("nativeAds",nads);
    	
    	LOGGER.info("There are " +nads.size() + " native ads");
    	
    	model.addAttribute("nativeAdsActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("hottestNativeAdsActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("nativeAdsArrow", Constants.TWISTIE_OPEN);
    	
        return "hottestNativeAds";
    }
    
    
}
