package com.careydevelopment.twitterautomation.service;

import java.util.List;

import com.careydevelopment.twitterautomation.jpa.entity.CompetitorSearch;
import com.careydevelopment.twitterautomation.jpa.entity.DomainRank;
import com.careydevelopment.twitterautomation.jpa.entity.DomainSearchKeyword;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.entity.SearchResult;

public interface SEMRushService {
	
	public static final String PAID = "paid";
	public static final String ORGANIC = "organic";
	public static final String ORGANIC_MOBILE = "organic-mobile";

	public DomainRank getDomainRank(ProjectUrl projectUrl);
	public List<CompetitorSearch> getCompetitorSearch(ProjectUrl projectUrl, String type);
	public List<DomainSearchKeyword> getDomainSearchKeywords(ProjectUrl projectUrl, String type);
	public List<SearchResult> getSearchResults(ProjectUrl projectUrl, String type);
}
