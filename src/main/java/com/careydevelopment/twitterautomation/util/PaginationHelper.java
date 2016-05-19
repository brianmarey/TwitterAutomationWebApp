package com.careydevelopment.twitterautomation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import com.careydevelopment.twitterautomation.jpa.entity.DomainAd;

public class PaginationHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaginationHelper.class);
	
    public static void setPagination (Page<DomainAd> ads, Model model, int page) {
    	model.addAttribute("isFirst", ads.isFirst());
    	model.addAttribute("isLast", ads.isLast());
    	
    	if (!ads.isLast()) {
    		model.addAttribute("nextPageNum", page+1);
    	}
    	
    	if (!ads.isFirst()) {
    		model.addAttribute("previousPageNum", page-1);
    	}	
    	
    	model.addAttribute("showingResults",getShowingResults(ads,page));
    }
    
    
    private static String getShowingResults(Page<DomainAd> ads,int page) {
    	StringBuilder builder = new StringBuilder();
    	
    	int start = (page * Constants.RESULTS_PER_PAGE) + 1;
    	int end = start + ads.getSize() - 1;
    	
    	builder.append("Showing Results ");    	    	
    	builder.append(start);
    	builder.append(" - ");
    	builder.append(end);
    	
    	return builder.toString();
    }
    
    
    public static int getPage(String pageNum) {
    	int page = 0;
    	
    	if (pageNum != null) {
    		try {
    			page = new Integer(pageNum);
    		} catch (NumberFormatException ne) {
    			LOGGER.warn("Page number isn't a number! " +pageNum);
    		}
    	}
    	
    	return page;
    }


}
