package com.careydevelopment.twitterautomation.service;

import com.careydevelopment.twitterautomation.jpa.entity.PageSpeedInsights;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;

public interface PageSpeedInsightsService {

	public static final String MOBILE_STRATEGY = "mobile";
	public static final String DESKTOP_STRATEGY = "desktop";
	
	public PageSpeedInsights getPageSpeedInsights(ProjectUrl url);
}
