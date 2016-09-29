package com.careydevelopment.twitterautomation.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.careydevelopment.twitterautomation.jpa.entity.BacklinkData;

public class BacklinkDataParser {

	private static final DateFormat DF = new SimpleDateFormat("yyyyMMdd");
	
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

	
	public static BacklinkData getBacklinkData(String row, String headers) {
		BacklinkData data = new BacklinkData();
		
		String[] heads = headers.split("\\|");
		
		if (row != null) {
			String[] parts = row.split("\\|");
			if (parts != null && parts.length > 20) {
				data.setSourceUrl(parts[getIndex("SourceURL",heads)]);
				data.setAnchorText(parts[getIndex("AnchorText",heads)]);
				data.setFlagNoFollow(getBoolean(parts[getIndex("FlagNoFollow",heads)]));
				data.setFlagDeleted(getBoolean(parts[getIndex("FlagDeleted",heads)]));
				data.setFlagAltText(getBoolean(parts[getIndex("FlagAltText",heads)]));
				data.setTargetUrl(parts[getIndex("TargetURL",heads)]);
				data.setFirstIndexedDate(getDate(parts[getIndex("FirstIndexedDate",heads)]));
				data.setLastSeenDate(getDate(parts[getIndex("LastSeenDate",heads)]));
				data.setDateLost(getDate(parts[getIndex("DateLost",heads)]));
				data.setReasonLost(parts[getIndex("ReasonLost",heads)]);
				data.setLinkType(parts[getIndex("LinkType",heads)]);
				data.setLinkSubtype(parts[getIndex("LinkSubType",heads)]);
				data.setTargetCitationFlow(new Integer(parts[getIndex("TargetCitationFlow",heads)]));
				data.setTargetTrustFlow(new Integer(parts[getIndex("TargetTrustFlow",heads)]));
				data.setSourceCitationFlow(new Integer(parts[getIndex("SourceCitationFlow",heads)]));
				data.setSourceTrustFlow(new Integer(parts[getIndex("SourceTrustFlow",heads)]));
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
