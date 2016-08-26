package com.careydevelopment.twitterautomation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.twitterautomation.jpa.entity.CompetitorSearch;
import com.careydevelopment.twitterautomation.jpa.entity.DomainRank;
import com.careydevelopment.twitterautomation.jpa.entity.DomainSearchKeyword;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.entity.SearchResult;
import com.careydevelopment.twitterautomation.service.SEMRushService;
import com.careydevelopment.twitterautomation.util.SemRushParser;

public class SEMRushServiceImpl implements SEMRushService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SEMRushServiceImpl.class);

	@Override
	public DomainRank getDomainRank(ProjectUrl projectUrl) {
		LOGGER.info("Getting domain rank for url " + projectUrl.getUrl());
		DomainRank domainRank = null;
		
		try {
			String response = "Domain;Rank;Organic Keywords;Organic Traffic;Organic Cost;Adwords Keywords;Adwords Traffic;Adwords Cost\nseobook.com;24041;5249;37332;143496;0;0;0";			
			domainRank = SemRushParser.getDomainRank(response);
		} catch (Exception e) {
			LOGGER.error("Problem getting domain rank!");
		}
		
		return domainRank;
	}

	
	@Override
	public List<CompetitorSearch> getCompetitorSearch(ProjectUrl projectUrl, String type) {
		LOGGER.info("Getting competitors for url " + projectUrl.getUrl());
		List<CompetitorSearch> list = new ArrayList<CompetitorSearch>();
		
		try {
			String response = "Domain;Competitor Relevance;Common Keywords;Organic Keywords;Organic Traffic;Organic Cost;Adwords Keywords\n"+
					"wordtracker.com;11.70;888;2337;14424;43425;0\n"+
					"moz.com;11.08;2103;13642;118148;537444;1170\n"+
					"seocentro.com;8.99;565;1034;8269;22462;0\n"+
					"wordstream.com;8.85;1826;15264;141247;547423;1436\n"+
					"bruceclay.com;7.75;635;2942;10853;111142;0";
			
			list = SemRushParser.getCompetitors(response,type);
		} catch (Exception e) {
			LOGGER.error("Problem getting competitors!");
		}
		
		return list;
	}
	
	
	@Override
	public List<DomainSearchKeyword> getDomainSearchKeywords(ProjectUrl projectUrl, String type) {
		LOGGER.info("Getting domain searches for url " + projectUrl.getUrl());
		List<DomainSearchKeyword> list = new ArrayList<DomainSearchKeyword>();
		
		try {
			String response = "Keyword;Position;Previous Position;Position Difference;Search Volume;CPC;Url;Traffic (%);Traffic Cost (%);Competition;Number of Results;Trends\n"+
					"seo tools;1;1;0;4400;5.59;http://tools.seobook.com/;5.53;8.05;0.96;27100000;0.99,0.99,0.99,0.99,0.99,0.81,0.81,0.81,0.81,0.81,0.81,0.81\n"+
					"seo;12;7;-5;90500;13.01;http://www.seobook.com/;3.15;10.66;0.96;224000000;0.99,0.99,0.82,0.82,0.82,0.82,0.82,0.82,0.82,0.82,0.82,0.82\n"+
					"seo training;1;1;0;1900;11.82;http://training.seobook.com/;2.39;7.35;0.94;6990000;0.99,0.79,0.79,0.79,0.79,0.79,0.67,0.79,0.79,0.67,0.67,0.67\n"+
					"free seo tools;1;1;0;1600;4.49;http://tools.seobook.com/;2.01;2.35;0.95;21000000;0.84,0.99,0.84,0.84,0.68,0.68,0.68,0.99,0.84,0.84,0.53,0.46";
			
			list = SemRushParser.getDomainSearchKeywords(response, type);
		} catch (Exception e) {
			LOGGER.error("Problem getting competitors!");
		}
		
		return list;
	}

	
	@Override
	public List<SearchResult> getSearchResults(ProjectUrl projectUrl, String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
