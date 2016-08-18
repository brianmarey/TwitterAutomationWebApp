package com.careydevelopment.twitterautomation.util;

import java.util.ArrayList;
import java.util.List;

import com.careydevelopment.twitterautomation.domain.DomainRank;

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

}
