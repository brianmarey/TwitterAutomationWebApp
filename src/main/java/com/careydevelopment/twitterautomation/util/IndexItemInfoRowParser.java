package com.careydevelopment.twitterautomation.util;

import java.math.BigInteger;

import com.careydevelopment.twitterautomation.jpa.entity.IndexItemInfoRow;

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
				iir.setExtBacklinks(new BigInteger(parts[4]));
				iir.setRefDomains(new BigInteger(parts[5]));
				iir.setItemType(new Integer(parts[8]));
				iir.setRefIps(new BigInteger(parts[12]));
				iir.setRefDomainsEdu(new BigInteger(parts[14]));
				iir.setExtBacklinksEdu(new BigInteger(parts[15]));
				iir.setRefDomainsGov(new BigInteger(parts[16]));
				iir.setExtBacklinksGov(new BigInteger(parts[17]));
				iir.setRefDomainsEduExact(new BigInteger(parts[18]));
				iir.setExtBacklinksEduExact(new BigInteger(parts[19]));
				iir.setRefDomainsGovExact(new BigInteger(parts[20]));
				iir.setExtBacklinksGovExact(new BigInteger(parts[21]));
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
