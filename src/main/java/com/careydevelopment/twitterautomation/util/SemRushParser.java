package com.careydevelopment.twitterautomation.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.careydevelopment.twitterautomation.domain.KeywordOverview;
import com.careydevelopment.twitterautomation.jpa.entity.CompetitorSearch;
import com.careydevelopment.twitterautomation.jpa.entity.DomainRank;
import com.careydevelopment.twitterautomation.jpa.entity.DomainSearchKeyword;
import com.careydevelopment.twitterautomation.jpa.entity.SearchResult;

public class SemRushParser {
	
	private static List<List<String>> getList(String response) {
		List<List<String>> list = new ArrayList<List<String>>();
		
		if (response != null) {
			String[] lines = response.split("\\n");
			
			if (lines != null && lines.length > 1) {
				for (int i=1;i<lines.length;i++) {
					String row = lines[i];
					String[] parts = row.split(";");
					
					List<String> vals = new ArrayList<String>();
					for (String part : parts) {
						vals.add(part);
					}
					
					list.add(vals);
				}
			}
		}
		
		return list;
	}
	
	
	public static DomainRank getDomainRank(String response) {
		DomainRank rank = new DomainRank();
		
		List<List<String>> vals = getList(response);
		
		if (vals.size() > 0) {
			List<String> list = vals.get(0);
			
			if (list.size() > 7) {
				rank.setAdwordsCost(new Integer(list.get(7)));
				rank.setAdwordsKeywords(new Integer(list.get(5)));
				rank.setAdwordsTraffic(new Integer(list.get(6)));
				rank.setDomain(list.get(0));
				rank.setOrganicCost(new Integer(list.get(4)));
				rank.setOrganicKeywords(new Integer(list.get(2)));
				rank.setOrganicTraffic(new Integer(list.get(3)));
				rank.setRank(new Integer(list.get(1)));
			}
		}
		
		return rank;
	}
	
	
	public static List<DomainSearchKeyword> getDomainSearchKeywords(String response, String type) {
		List<DomainSearchKeyword> keywords = new ArrayList<DomainSearchKeyword>();
		
		List<List<String>> vals = getList(response);
		
		if (vals.size() > 0) {
			for (List<String> list : vals) {
				if (list.size() > 10) {
					DomainSearchKeyword keyword = new DomainSearchKeyword();
					keyword.setType(type);
					keyword.setCompetition(new Float(list.get(9)));
					keyword.setCpc(new Float(list.get(5)));
					keyword.setKeyword(list.get(0));
					keyword.setNumberOfResults(new BigInteger(list.get(10)));
					keyword.setPosition(new Integer(list.get(1)));
					keyword.setPositionDifference(new Integer(list.get(3)));
					keyword.setPreviousPosition(new Integer(list.get(2)));
					keyword.setSearchVolume(new Integer(list.get(4)));
					keyword.setTrafficCostPercent(new Float(list.get(8)));
					keyword.setTrafficPercent(new Float(list.get(7)));
					keyword.setUrl(list.get(6));
					
					keywords.add(keyword);
				}				
			}
		}
		
		return keywords;
	}

	
	public static List<CompetitorSearch> getCompetitors(String response, String type) {
		List<CompetitorSearch> comps = new ArrayList<CompetitorSearch>();
		
		List<List<String>> vals = getList(response);
		
		if (vals.size() > 0) {
			for (List<String> list : vals) {
				if (list.size() > 5) {
					CompetitorSearch comp = new CompetitorSearch();
					comp.setType(type);
					comp.setCommonKeywords(new Integer(list.get(2)));
					comp.setCompetitorRelevance(new Float(list.get(1)));
					comp.setDomain(list.get(0));
					comp.setTypeCost(new Float(list.get(5)));
					comp.setTypeKeywords(new Integer(list.get(3)));
					comp.setTypeTraffic(new Integer(list.get(4)));
					
					comps.add(comp);
				}				
			}
		}
		
		return comps;
	}
	
	
	public static List<KeywordOverview> getKeywordOverview(String response) {
		List<KeywordOverview> keys = new ArrayList<KeywordOverview>();
		
		List<List<String>> vals = getList(response);
		
		if (vals.size() > 0) {
			for (List<String> list : vals) {
				if (list.size() > 4) {
					KeywordOverview key = new KeywordOverview();
					key.setCompetition(new Float(list.get(3)));
					key.setCpc(new Float(list.get(2)));
					key.setKeyword(list.get(0));
					key.setNumberOfResults(new Integer(list.get(4)));
					key.setSearchVolume(new Integer(list.get(1)));
					
					keys.add(key);
				}				
			}
		}
		
		return keys;
	}
	
	
	public static List<SearchResult> getSearchResults(String response, String type) {
		List<SearchResult> searches = new ArrayList<SearchResult>();
		
		List<List<String>> vals = getList(response);
		
		if (vals.size() > 0) {
			for (List<String> list : vals) {
				if (list.size() > 1) {
					SearchResult search = new SearchResult();
					search.setType(type);
					search.setDomain(list.get(0));
					search.setUrl(list.get(1));
					
					searches.add(search);
				}				
			}
		}
		
		return searches;
	}
	
}
