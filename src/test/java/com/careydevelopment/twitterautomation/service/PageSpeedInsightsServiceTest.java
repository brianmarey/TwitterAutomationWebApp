package com.careydevelopment.twitterautomation.service;

import org.junit.Test;

import com.careydevelopment.twitterautomation.domain.PageSpeedInsights;
import com.careydevelopment.twitterautomation.service.impl.PageSpeedInsightsServiceImpl;

import org.junit.Assert;
import junit.framework.TestCase;

public class PageSpeedInsightsServiceTest extends TestCase {

	@Test
	public void testGetPageSpeedInsights() {
		PageSpeedInsightsService service = new PageSpeedInsightsServiceImpl();
		PageSpeedInsights insights = service.getPageSpeedInsights("brianmcarey.com");
		
		Assert.assertNotNull(insights);
		Assert.assertNotNull(insights.getDesktopSpeed());
		Assert.assertNotNull(insights.getMobileSpeed());
		Assert.assertNotNull(insights.getMobileUsability());
	}
}
