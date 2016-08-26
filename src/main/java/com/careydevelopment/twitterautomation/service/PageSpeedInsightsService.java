package com.careydevelopment.twitterautomation.service;

import com.careydevelopment.twitterautomation.jpa.entity.PageSpeedInsights;

public interface PageSpeedInsightsService {

	public static final String MOBILE_STRATEGY = "mobile";
	public static final String DESKTOP_STRATEGY = "desktop";
	
	public PageSpeedInsights getPageSpeedInsights(String url);
}
