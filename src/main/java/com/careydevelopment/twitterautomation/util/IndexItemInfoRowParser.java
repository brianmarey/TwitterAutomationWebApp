package com.careydevelopment.twitterautomation.util;

import java.math.BigInteger;

import com.careydevelopment.twitterautomation.jpa.entity.IndexItemInfoRow;

public class IndexItemInfoRowParser {

	private static int getIndex(String name, String[] heads) {
		int val = 0;
		
		for (int i=0;i<heads.length;i++) {
			if (name.equalsIgnoreCase(heads[i])) {
				val = i;
				
				break;
			}
		}
		
		System.err.println("for " + name + " returning " + val);
		
		return val;
	}
	
	public static IndexItemInfoRow getIndexItemInfoRow(String row, String headers) {
		IndexItemInfoRow iir = new IndexItemInfoRow();
		
		if (row != null) {
			String[] parts = row.split("\\|");
			if (parts != null && parts.length > 40) {
				String heads[] = headers.split("\\|");
				for (int i=0;i<heads.length;i++) {
					System.err.println("" + i + " " + heads[i] + " " + parts[i]);
				}
				
				iir.setItemNum(new Integer(parts[0]));
				iir.setItem(parts[getIndex("Item",heads)]);
				iir.setResultCode(parts[getIndex("ResultCode",heads)]);
				iir.setStatus(parts[getIndex("Status",heads)]);
				iir.setExtBacklinks(new BigInteger(parts[getIndex("ExtBackLinks",heads)]));
				iir.setRefDomains(new BigInteger(parts[getIndex("RefDomains",heads)]));
				iir.setItemType(new Integer(parts[getIndex("ItemType",heads)]));
				iir.setRefIps(new BigInteger(parts[getIndex("RefIPs",heads)]));
				iir.setRefDomainsEdu(new BigInteger(parts[getIndex("RefDomainsEDU",heads)]));
				iir.setExtBacklinksEdu(new BigInteger(parts[getIndex("ExtBacklinksEDU",heads)]));
				iir.setRefDomainsGov(new BigInteger(parts[getIndex("RefDomainsGOV",heads)]));
				iir.setExtBacklinksGov(new BigInteger(parts[getIndex("ExtBacklinksGOV",heads)]));
				iir.setRefDomainsEduExact(new BigInteger(parts[getIndex("RefDomainsEDU",heads)]));
				iir.setExtBacklinksEduExact(new BigInteger(parts[getIndex("ExtBacklinksEDU_Exact",heads)]));
				iir.setRefDomainsGovExact(new BigInteger(parts[getIndex("RefDomainsGOV_Exact",heads)]));
				iir.setExtBacklinksGovExact(new BigInteger(parts[getIndex("ExtBacklinksGOV_Exact",heads)]));
				iir.setTitle(parts[getIndex("Title",heads)]);
				
				try {
					iir.setCitationFlow(new Integer(parts[getIndex("CitationFlow",heads)]));
				} catch (NumberFormatException ne) {
					int loc = getIndex("Title",heads);
					boolean foundNumber = false;
					while (!foundNumber) {
						loc++;
						try {
							Integer i = new Integer(parts[loc]);
							iir.setCitationFlow(i);
							foundNumber = true;
						} catch (NumberFormatException e) {
							
						}
						if (loc >= parts.length) break;
					}
				}
				
				try {
					iir.setTrustFlow(new Integer(parts[getIndex("TrustFlow",heads)]));
				} catch (NumberFormatException ne) {
					int loc = getIndex("Title",heads);
					boolean foundNumber = false;
					while (!foundNumber) {
						loc++;
						try {
							Integer i = new Integer(parts[loc]);
							iir.setTrustFlow(new Integer(parts[loc+1]));
							foundNumber = true;
						} catch (NumberFormatException e) {						
						}
						if (loc >= parts.length) break;
					}
				}
			} else {
				throw new RuntimeException("Couldn't parse the data row!");
			}
		}
		
		return iir;
	}
}
