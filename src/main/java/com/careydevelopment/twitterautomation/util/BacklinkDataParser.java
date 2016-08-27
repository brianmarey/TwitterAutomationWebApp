package com.careydevelopment.twitterautomation.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.careydevelopment.twitterautomation.jpa.entity.BacklinkData;

public class BacklinkDataParser {

	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	
	public static BacklinkData getBacklinkData(String row) {
		BacklinkData data = new BacklinkData();
		
		if (row != null) {
			String[] parts = row.split("\\|");
			if (parts != null && parts.length > 20) {
				data.setSourceUrl(parts[0]);
				data.setAnchorText(parts[2]);
				data.setFlagNoFollow(getBoolean(parts[6]));
				data.setFlagDeleted(getBoolean(parts[8]));
				data.setFlagAltText(getBoolean(parts[9]));
				data.setTargetUrl(parts[11]);
				data.setFirstIndexedDate(getDate(parts[13]));
				data.setLastSeenDate(getDate(parts[14]));
				data.setDateLost(getDate(parts[15]));
				data.setReasonLost(parts[16]);
				data.setLinkType(parts[17]);
				data.setLinkSubtype(parts[18]);
				data.setTargetCitationFlow(new Integer(parts[19]));
				data.setTargetTrustFlow(new Integer(parts[20]));
				data.setSourceCitationFlow(new Integer(parts[23]));
				data.setSourceTrustFlow(new Integer(parts[24]));
			} else {
				throw new RuntimeException("Couldn't parse the data row for backlink!");
			}
		}
		
		return data;
	}
	
	
	private static boolean getBoolean(String s) {
		boolean b = false;
		
		if (s != null) {
			if (s.equals("1")) {
				b = true;
			}
		}
		
		return b;
	}
	
	
	private static Date getDate(String s) {
		Date d = null;
		
		if (s != null && s.trim().length() > 1) {
			try {
				d = DF.parse(s);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return d;
	}
}
