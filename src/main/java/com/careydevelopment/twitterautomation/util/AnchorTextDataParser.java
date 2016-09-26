package com.careydevelopment.twitterautomation.util;

import java.math.BigInteger;

import com.careydevelopment.twitterautomation.jpa.entity.AnchorTextData;

public class AnchorTextDataParser {
	
	private static int getIndex(String name, String[] heads) {
		int val = 0;
		
		for (int i=0;i<heads.length;i++) {
			if (name.equalsIgnoreCase(heads[i])) {
				val = i;
				break;
			}
		}
		
		return val;
	}
	
	public static AnchorTextData getAnchorTextData(String row, String headers) {
		AnchorTextData data = new AnchorTextData();
		
		String[] heads = headers.split("\\|");
		
		if (row != null) {
			String[] parts = row.split("\\|");
			if (parts != null && parts.length > 4) {
				data.setCitationFlow(new Integer(parts[getIndex("EstimatedLinkCitationFlow",heads)]));
				data.setTrustFlow(new Integer(parts[getIndex("EstimatedLinkTrustFlow",heads)]));
				data.setReferringDomains(new Integer(parts[getIndex("RefDomains",heads)]));
				data.setTotalLinks(new BigInteger(parts[getIndex("TotalLinks",heads)]));
				data.setDeletedLinks(new BigInteger(parts[getIndex("DeletedLinks",heads)]));
				data.setNofollowLinks(new BigInteger(parts[getIndex("NoFollowLinks",heads)]));
				data.setAnchorText(parts[getIndex("AnchorText",heads)]);
			} else {
				throw new RuntimeException("Couldn't parse the data row for anchor text!");
			}
		}
		
		return data;
	}
}
