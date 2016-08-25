package com.careydevelopment.twitterautomation.service.impl;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.careydevelopment.propertiessupport.PropertiesFactory;
import com.careydevelopment.propertiessupport.PropertiesFile;
import com.careydevelopment.twitterautomation.domain.PageSpeedInsights;
import com.careydevelopment.twitterautomation.service.PageSpeedInsightsService;
import com.careydevelopment.twitterautomation.util.JsonParser;
import com.careydevelopment.twitterautomation.util.UrlHelper;
import com.sdicons.json.model.JSONInteger;
import com.sdicons.json.model.JSONObject;

@Component
public class PageSpeedInsightsServiceImpl implements PageSpeedInsightsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PageSpeedInsightsServiceImpl.class);
	
	@Override
	public PageSpeedInsights getPageSpeedInsights(String url) {
		PageSpeedInsights insights = new PageSpeedInsights();
		
		try {
			setInsights(url,PageSpeedInsightsService.MOBILE_STRATEGY,insights);
			setInsights(url,PageSpeedInsightsService.DESKTOP_STRATEGY,insights);			
		} catch (Exception e) {
			LOGGER.error("Problem retrieving page speed insights for " + url,e);
		}
		
		return insights;
	}
	
	
	private void setInsights(String url, String strategy, PageSpeedInsights insights) throws Exception {
		String insightsUrl = getInsightsUrl(url, strategy);			
		JsonParser reader = new JsonParser(insightsUrl);
			
		JSONObject json = reader.getJson();
		JSONObject ruleGroups =(JSONObject)json.get("ruleGroups");
		
		JSONObject speed = (JSONObject)ruleGroups.get("SPEED");
		JSONInteger speedScore = (JSONInteger)speed.get("score");
		
		if (PageSpeedInsightsService.MOBILE_STRATEGY.equals(strategy)) {
			JSONObject usability = (JSONObject)ruleGroups.get("USABILITY");
			JSONInteger usabilityScore = (JSONInteger)usability.get("score");
			
			insights.setMobileSpeed(speedScore.getValue().intValue());
			insights.setMobileUsability(usabilityScore.getValue().intValue());			
		} else {
			insights.setDesktopSpeed(speedScore.getValue().intValue());
		}
	}
	
	
	private String getInsightsUrl(String url, String strategy) throws Exception {
		Properties props = PropertiesFactory.getProperties(PropertiesFile.GOOGLE_PROPERTIES);
		String apiKey = props.getProperty("api.key");

		StringBuilder sb = new StringBuilder();
		sb.append("https://www.googleapis.com/pagespeedonline/v2/runPagespeed?url=");
		sb.append(UrlHelper.getFormattedUrl(url));
		sb.append("&strategy=");
		sb.append(strategy);
		sb.append("&key=");
		sb.append(apiKey);
		
		return sb.toString();
	}

}
