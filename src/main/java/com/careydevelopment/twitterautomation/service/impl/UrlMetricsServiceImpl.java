package com.careydevelopment.twitterautomation.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.careydevelopment.twitterautomation.controller.CreateProjectUrlController;
import com.careydevelopment.twitterautomation.jpa.entity.PageSpeedInsights;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.repository.PageSpeedInsightsRepository;
import com.careydevelopment.twitterautomation.service.PageSpeedInsightsService;
import com.careydevelopment.twitterautomation.service.UrlMetricsService;

@Component
public class UrlMetricsServiceImpl implements UrlMetricsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UrlMetricsServiceImpl.class);

	@Autowired
	PageSpeedInsightsService pageSpeedInsightsService;
	
	@Autowired
	PageSpeedInsightsRepository pageSpeedInsightsRepository;
	
	
	@Override
	public void saveUrlMetrics(ProjectUrl url) {
		savePageSpeedInsights(url);
		//saveUrlValue(url);
	}
	
	
	private void savePageSpeedInsights(ProjectUrl url) {
		try {
			LOGGER.info("Retrieving insights for url " + url.getUrl());
			
			PageSpeedInsights insights = pageSpeedInsightsService.getPageSpeedInsights(url.getUrl());
			insights.setProjectUrl(url);
			
			pageSpeedInsightsRepository.save(insights);
		} catch (Exception e) {
			LOGGER.error("Problem saving page speed insights!",e);
		}
	}

}
