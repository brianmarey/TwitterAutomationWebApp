package com.careydevelopment.twitterautomation.util;

import org.junit.Test;

import junit.framework.TestCase;


public class SemRushParserTest extends TestCase {

	@Test
	public void testDomainRankParse() {
//		String response = "Domain;Rank;Organic Keywords;Organic Traffic;Organic Cost;Adwords Keywords;Adwords Traffic;Adwords Cost\nseobook.com;24041;5249;37332;143496;0;0;0";
//		
//		DomainRank rank = SemRushParser.getDomainRank(response);
//		Assert.assertEquals("seobook.com", rank.getDomain());
//		Assert.assertEquals(new Integer(5249), rank.getOrganicKeywords());
//		Assert.assertEquals(new Integer(24041), rank.getRank());
	}
	
	
//	@Test
//	public void testDomainSearchKeywordParse() {
//		String response = "Keyword;Position;Previous Position;Position Difference;Search Volume;CPC;Url;Traffic (%);Traffic Cost (%);Competition;Number of Results;Trends\n"+
//			"seo tools;1;1;0;4400;5.59;http://tools.seobook.com/;5.53;8.05;0.96;27100000;0.99,0.99,0.99,0.99,0.99,0.81,0.81,0.81,0.81,0.81,0.81,0.81\n"+
//			"seo;12;7;-5;90500;13.01;http://www.seobook.com/;3.15;10.66;0.96;224000000;0.99,0.99,0.82,0.82,0.82,0.82,0.82,0.82,0.82,0.82,0.82,0.82\n"+
//			"seo training;1;1;0;1900;11.82;http://training.seobook.com/;2.39;7.35;0.94;6990000;0.99,0.79,0.79,0.79,0.79,0.79,0.67,0.79,0.79,0.67,0.67,0.67\n"+
//			"free seo tools;1;1;0;1600;4.49;http://tools.seobook.com/;2.01;2.35;0.95;21000000;0.84,0.99,0.84,0.84,0.68,0.68,0.68,0.99,0.84,0.84,0.53,0.46";
//		
//		List<DomainSearchKeyword> keys = SemRushParser.getDomainSearchKeywords(response, "organic");
//		Assert.assertNotNull(keys);
//		
//		Assert.assertEquals(4, keys.size());
//		DomainSearchKeyword key = keys.get(0);
//		Assert.assertEquals("seo tools", key.getKeyword());
//		Assert.assertEquals(new Integer(1), key.getPosition());
//	}
//
//	
//	@Test
//	public void testCompetitorSearchParse() {
//		String response = "Domain;Competitor Relevance;Common Keywords;Organic Keywords;Organic Traffic;Organic Cost;Adwords Keywords\n"+
//			"wordtracker.com;11.70;888;2337;14424;43425;0\n"+
//			"moz.com;11.08;2103;13642;118148;537444;1170\n"+
//			"seocentro.com;8.99;565;1034;8269;22462;0\n"+
//			"wordstream.com;8.85;1826;15264;141247;547423;1436\n"+
//			"bruceclay.com;7.75;635;2942;10853;111142;0";
//		
//		List<CompetitorSearch> comps = SemRushParser.getCompetitors(response, "organic");
//		Assert.assertNotNull(comps);
//		
//		Assert.assertEquals(5, comps.size());
//		CompetitorSearch comp = comps.get(0);
//		//Assert.assertEquals("wordtracker.com", comp.getDomain());
//		Assert.assertEquals(new Float(11.7), comp.getCompetitorRelevance());
//	}

}
