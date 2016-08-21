package com.careydevelopment.twitterautomation.util;

import com.careydevelopment.twitterautomation.domain.IndexItemInfoRow;

public class IndexItemInfoRowParser {

	public static IndexItemInfoRow getIndexItemInfoRow(String row) {
		IndexItemInfoRow iir = new IndexItemInfoRow();
		
		if (row != null) {
			String[] parts = row.split("\\|");
			if (parts != null && parts.length > 40) {
				iir.setItemNum(new Integer(parts[0]));
				iir.setItem(parts[1]);
				iir.setResultCode(parts[2]);
				iir.setStatus(parts[3]);
				iir.setExtBacklinks(new Integer(parts[4]));
				iir.setRefDomains(new Integer(parts[5]));
				iir.setItemType(new Integer(parts[8]));
				iir.setRefIps(new Integer(parts[12]));
				iir.setRefDomainsEdu(new Integer(parts[14]));
				iir.setExtBacklinksEdu(new Integer(parts[15]));
				iir.setRefDomainsGov(new Integer(parts[16]));
				iir.setExtBacklinksGov(new Integer(parts[17]));
				iir.setRefDomainsEduExact(new Integer(parts[18]));
				iir.setExtBacklinksEduExact(new Integer(parts[19]));
				iir.setRefDomainsGovExact(new Integer(parts[20]));
				iir.setExtBacklinksGovExact(new Integer(parts[21]));
				iir.setTitle(parts[31]);
				iir.setCitationFlow(new Integer(parts[33]));
				iir.setTrustFlow(new Integer(parts[34]));
			} else {
				throw new RuntimeException("Couldn't parse the data row!");
			}
		}
		
		return iir;
	}
}
