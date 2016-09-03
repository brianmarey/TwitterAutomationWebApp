package com.careydevelopment.twitterautomation.util;

import com.careydevelopment.twitterautomation.jpa.entity.AnchorTextData;

public class AnchorTextDataParser {
	
	public static AnchorTextData getAnchorTextData(String row) {
		AnchorTextData data = new AnchorTextData();
		
		if (row != null) {
			String[] parts = row.split("\\|");
			if (parts != null && parts.length > 4) {
				data.setCitationFlow(new Integer(parts[5]));
				data.setTrustFlow(new Integer(parts[6]));
				data.setReferringDomains(new Integer(parts[1]));
				data.setTotalLinks(new Integer(parts[2]));
				data.setDeletedLinks(new Integer(parts[3]));
				data.setNofollowLinks(new Integer(parts[4]));
				data.setAnchorText(parts[0]);
			} else {
				throw new RuntimeException("Couldn't parse the data row for anchor text!");
			}
		}
		
		return data;
	}
}
