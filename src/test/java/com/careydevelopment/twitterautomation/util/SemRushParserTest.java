package com.careydevelopment.twitterautomation.util;

import org.junit.Assert;
import org.junit.Test;

import com.careydevelopment.twitterautomation.domain.DomainRank;

import junit.framework.TestCase;


public class SemRushParserTest extends TestCase {

	@Test
	public void testDomainRankParse() {
		String response = "Domain;Rank;Organic Keywords;Organic Traffic;Organic Cost;Adwords Keywords;Adwords Traffic;Adwords Cost\nseobook.com;24041;5249;37332;143496;0;0;0";
		
		DomainRank rank = SemRushParser.getDomainRank(response);
		Assert.assertEquals("seobook.com", rank.getDomain());
		Assert.assertEquals(new Integer(5249), rank.getOrganicKeywords());
		Assert.assertEquals(new Integer(24041), rank.getRank());
	}
}
