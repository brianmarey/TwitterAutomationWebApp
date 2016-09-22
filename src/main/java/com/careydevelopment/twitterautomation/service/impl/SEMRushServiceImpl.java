package com.careydevelopment.twitterautomation.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.careydevelopment.propertiessupport.PropertiesFactory;
import com.careydevelopment.propertiessupport.PropertiesFactoryException;
import com.careydevelopment.propertiessupport.PropertiesFile;
import com.careydevelopment.twitterautomation.jpa.entity.CompetitorSearch;
import com.careydevelopment.twitterautomation.jpa.entity.DomainRank;
import com.careydevelopment.twitterautomation.jpa.entity.DomainSearchKeyword;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.entity.SearchResult;
import com.careydevelopment.twitterautomation.service.SEMRushService;
import com.careydevelopment.twitterautomation.util.SemRushParser;
import com.careydevelopment.twitterautomation.util.UrlHelper;

@Component
public class SEMRushServiceImpl implements SEMRushService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SEMRushServiceImpl.class);
	
	private static final int SEARCH_TYPE_KEYWORDS = 1;
	private static final int SEARCH_TYPE_PAID_KEYWORDS = 2;
	private static final int SEARCH_TYPE_DOMAIN_RANK = 3;
	private static final int SEARCH_TYPE_COMPETITORS = 4;
	private static final int SEARCH_TYPE_PAID_COMPETITORS = 5;
	
	private String apiKey = "";
	
	
	public SEMRushServiceImpl() {
		try {
			Properties props = PropertiesFactory.getProperties(PropertiesFile.SEMRUSH_PROPERTIES);
			apiKey = props.getProperty("semrush.api.key");
		} catch (PropertiesFactoryException pe) {
			LOGGER.error("Problem retrieving properties for SEMRush!",pe);
		}
	}

	
	@Override
	public DomainRank getDomainRank(ProjectUrl projectUrl) {
		LOGGER.info("Getting domain rank for url " + projectUrl.getUrl());
		DomainRank domainRank = null;
		
		try {
			String response = getResponse(projectUrl, SEARCH_TYPE_DOMAIN_RANK);
			//String response = "Domain;Rank;Organic Keywords;Organic Traffic;Organic Cost;Adwords Keywords;Adwords Traffic;Adwords Cost\nseobook.com;24041;5249;37332;143496;0;0;0";			
			domainRank = SemRushParser.getDomainRank(response);
		} catch (Exception e) {
			LOGGER.error("Problem getting domain rank!",e);
		}
		
		return domainRank;
	}

	
	@Override
	public List<CompetitorSearch> getCompetitorSearch(ProjectUrl projectUrl, String type) {
		LOGGER.info("Getting competitors for url " + projectUrl.getUrl());
		List<CompetitorSearch> list = new ArrayList<CompetitorSearch>();
		
		try {
			int searchType = "organic".equalsIgnoreCase(type) ? SEARCH_TYPE_COMPETITORS : SEARCH_TYPE_PAID_COMPETITORS;

			String response = getResponse(projectUrl, searchType);
			/*String response = "Domain;Competitor Relevance;Common Keywords;Organic Keywords;Organic Traffic;Organic Cost;Adwords Keywords\n"+
					"wordtracker.com;11.70;888;2337;14424;43425;0\n"+
					"moz.com;11.08;2103;13642;118148;537444;1170\n"+
					"seocentro.com;8.99;565;1034;8269;22462;0\n"+
					"wordstream.com;8.85;1826;15264;141247;547423;1436\n"+
					"bruceclay.com;7.75;635;2942;10853;111142;0";
			*/
			list = SemRushParser.getCompetitors(response,type);
		} catch (Exception e) {
			LOGGER.error("Problem getting competitors!",e);
		}
		
		return list;
	}
	
	
	@Override
	public List<DomainSearchKeyword> getDomainSearchKeywords(ProjectUrl projectUrl, String type) {
		LOGGER.info("Getting domain searches for url " + projectUrl.getUrl());
		List<DomainSearchKeyword> list = new ArrayList<DomainSearchKeyword>();
		
		try {
			int searchType = "organic".equalsIgnoreCase(type) ? SEARCH_TYPE_KEYWORDS : SEARCH_TYPE_PAID_KEYWORDS;
			
			String response = getResponse(projectUrl, searchType);
			/*
			String response = "Keyword;Position;Previous Position;Position Difference;Search Volume;CPC;Url;Traffic (%);Traffic Cost (%);Competition;Number of Results;Trends\n"+
					"seo tools;1;1;0;4400;5.59;http://tools.seobook.com/;5.53;8.05;0.96;27100000;0.99,0.99,0.99,0.99,0.99,0.81,0.81,0.81,0.81,0.81,0.81,0.81\n"+
					"seo;12;7;-5;90500;13.01;http://www.seobook.com/;3.15;10.66;0.96;224000000;0.99,0.99,0.82,0.82,0.82,0.82,0.82,0.82,0.82,0.82,0.82,0.82\n"+
					"seo training;1;1;0;1900;11.82;http://training.seobook.com/;2.39;7.35;0.94;6990000;0.99,0.79,0.79,0.79,0.79,0.79,0.67,0.79,0.79,0.67,0.67,0.67\n"+
					"free seo tools;1;1;0;1600;4.49;http://tools.seobook.com/;2.01;2.35;0.95;21000000;0.84,0.99,0.84,0.84,0.68,0.68,0.68,0.99,0.84,0.84,0.53,0.46";
			*/
			list = SemRushParser.getDomainSearchKeywords(response, type);
		} catch (Exception e) {
			LOGGER.error("Problem getting domain searches!",e);
		}
		
		return list;
	}

	
	@Override
	public List<SearchResult> getSearchResults(ProjectUrl projectUrl, String type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private String getResponse(ProjectUrl projectUrl, int searchType) {
		String response = "";
		
		try {
			String url = buildUrl(projectUrl, searchType);
			response = UrlHelper.getUrlContents(url);
		} catch (Exception e) {
			LOGGER.error("Problem retrieving SEM response for search type " + searchType,e);
		}
		
		return response;
	}
	
	
	private String buildUrl(ProjectUrl projectUrl, int searchType) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("http://api.semrush.com/?type=");
		
		switch (searchType) {
			case SEARCH_TYPE_KEYWORDS:
				builder.append("domain_organic");
				builder.append("&key=");
				builder.append(apiKey);
				builder.append("&export_columns=Ph,Po,Pp,Pd,Nq,Cp,Ur,Tr,Tc,Co,Nr,Td&domain=");
				builder.append(projectUrl.getUrl());
				builder.append("&display_sort=po_asc&database=us&display_limit=100");

				break;
				
			case SEARCH_TYPE_PAID_KEYWORDS:
				builder.append("domain_adwords");
				builder.append("&key=");
				builder.append(apiKey);
				builder.append("&export_columns=Ph,Po,Pp,Pd,Nq,Cp,Ur,Tr,Tc,Co,Nr,Td&domain=");
				builder.append(projectUrl.getUrl());
				builder.append("&display_sort=po_asc&database=us&display_limit=100");

				break;
				
			case SEARCH_TYPE_DOMAIN_RANK:
				builder.append("domain_rank&key=");
				builder.append(apiKey);
				builder.append("&export_columns=Dn,Rk,Or,Ot,Oc,Ad,At,Ac&domain=");
				builder.append(projectUrl.getUrl());
				builder.append("&database=us");
				
				break;
				
			case SEARCH_TYPE_COMPETITORS:
				builder.append("domain_organic_organic&key=");
				builder.append(apiKey);
				builder.append("&display_limit=10&export_columns=Dn,Cr,Np,Or,Ot,Oc,Ad&domain=");
				builder.append(projectUrl.getUrl());
				builder.append("&database=us");
				
				break;
				
			case SEARCH_TYPE_PAID_COMPETITORS:
				builder.append("domain_adwords_adwords&key=");
				builder.append(apiKey);
				builder.append("&display_limit=10&export_columns=Dn,Cr,Np,Or,Ot,Oc,Ad&domain=");
				builder.append(projectUrl.getUrl());
				builder.append("&database=us");
				
				break;
		}
		
		return builder.toString();
	}

	
	public static void main(String[] args) {
		SEMRushServiceImpl s = new SEMRushServiceImpl();
		
		ProjectUrl url = new ProjectUrl();
		url.setUrl("junglescout.com");
		
		s.getResponse(url, SEARCH_TYPE_KEYWORDS);
	}
}
