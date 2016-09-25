package com.careydevelopment.twitterautomation.util;

import java.math.BigInteger;

import com.careydevelopment.twitterautomation.jpa.entity.IndexItemInfoRow;

public class IndexItemInfoRowParser {

	public static IndexItemInfoRow getIndexItemInfoRow(String row) {
		IndexItemInfoRow iir = new IndexItemInfoRow();
		
		if (row != null) {
			String[] parts = row.split("\\|");
			if (parts != null && parts.length > 40) {
				String dude = "ItemNum|Item|ResultCode|Status|ExtBackLinks|RefDomains|AnalysisResUnitsCost|ACRank|ItemType|IndexedURLs|GetTopBackLinksAnalysisResUnitsCost|DownloadBacklinksAnalysisResUnitsCost|DownloadRefDomainBacklinksAnalysisResUnitsCost|RefIPs|RefSubNets|RefDomainsEDU|ExtBackLinksEDU|RefDomainsGOV|ExtBackLinksGOV|RefDomainsEDU_Exact|ExtBackLinksEDU_Exact|RefDomainsGOV_Exact|ExtBackLinksGOV_Exact|CrawledFlag|LastCrawlDate|LastCrawlResult|RedirectFlag|FinalRedirectResult|OutDomainsExternal|OutLinksExternal|OutLinksInternal|LastSeen|Title|RedirectTo|CitationFlow|TrustFlow|TrustMetric|TopicalTrustFlow_Topic_0|TopicalTrustFlow_Value_0|TopicalTrustFlow_Topic_1|TopicalTrustFlow_Value_1|TopicalTrustFlow_Topic_2|TopicalTrustFlow_Value_2";
				String[] dudes = dude.split("\\|");
				
				for (int i=0;i<parts.length;i++) {
					System.err.println("" + i + " " + parts[i] + " " + dudes[i]);
				}
				iir.setItemNum(new Integer(parts[0]));
				iir.setItem(parts[1]);
				iir.setResultCode(parts[2]);
				iir.setStatus(parts[3]);
				iir.setExtBacklinks(new BigInteger(parts[4]));
				iir.setRefDomains(new BigInteger(parts[5]));
				iir.setItemType(new Integer(parts[8]));
				iir.setRefIps(new BigInteger(parts[13]));
				iir.setRefDomainsEdu(new BigInteger(parts[15]));
				iir.setExtBacklinksEdu(new BigInteger(parts[16]));
				iir.setRefDomainsGov(new BigInteger(parts[17]));
				iir.setExtBacklinksGov(new BigInteger(parts[18]));
				iir.setRefDomainsEduExact(new BigInteger(parts[19]));
				iir.setExtBacklinksEduExact(new BigInteger(parts[20]));
				iir.setRefDomainsGovExact(new BigInteger(parts[21]));
				iir.setExtBacklinksGovExact(new BigInteger(parts[22]));
				iir.setTitle(parts[32]);
				iir.setCitationFlow(new Integer(parts[34]));
				iir.setTrustFlow(new Integer(parts[35]));
			} else {
				throw new RuntimeException("Couldn't parse the data row!");
			}
		}
		
		return iir;
	}
}
