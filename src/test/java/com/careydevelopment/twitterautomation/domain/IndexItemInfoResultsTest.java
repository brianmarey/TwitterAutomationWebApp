package com.careydevelopment.twitterautomation.domain;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class IndexItemInfoResultsTest extends TestCase {

	@Test
	public void testUnmarshall() {
		
		StringReader reader = new StringReader("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
			"<Result Code=\"OK\" ErrorMessage=\"\" FullError=\"\">"+
			"<GlobalVars FirstBackLinkDate=\"2014-01-29\" IndexBuildDate=\"2014-04-30 23:18:43\" IndexType=\"1\" MostRecentBackLinkDate=\"2014-04-29\" ServerBuild=\"2014-04-30 22:34:53\" ServerName=\"QUACKYO\" ServerVersion=\"1.0.5233.38846\" UniqueIndexID=\"20140430231843-FRESH\"/>"+
			"<DataTables Count=\"1\">"+
			"<DataTable Name=\"Results\" RowsCount=\"1\" Headers=\"ItemNum|Item|ResultCode|Status|ExtBackLinks|RefDomains|AnalysisResUnitsCost|ACRank|ItemType|IndexedURLs|GetTopBackLinksAnalysisResUnitsCost|DownloadBacklinksAnalysisResUnitsCost|RefIPs|RefSubNets|RefDomainsEDU|ExtBackLinksEDU|RefDomainsGOV|ExtBackLinksGOV|RefDomainsEDU_Exact|ExtBackLinksEDU_Exact|RefDomainsGOV_Exact|ExtBackLinksGOV_Exact|CrawledFlag|LastCrawlDate|LastCrawlResult|RedirectFlag|FinalRedirectResult|OutDomainsExternal|OutLinksExternal|OutLinksInternal|LastSeen|Title|RedirectTo|CitationFlow|TrustFlow|TrustMetric|TopicalTrustFlow_Topic_0|TopicalTrustFlow_Value_0|TopicalTrustFlow_Topic_1|TopicalTrustFlow_Value_1|TopicalTrustFlow_Topic_2|TopicalTrustFlow_Value_2\">"+
			"<Row>0|http://www.majestic.com/|OK|Found|510654|6382|510654|10|3|1|5000|510655|5354|4396|52|641|1|1|4|16|1|1|True|2014-04-28|DownloadedSuccessfully|False| |5|5|60| |Majestic : Backlink Checker Site Explorer| |91|52|52|Computers/Internet/Web Design and Development|41|Computers/Software/Online Training|39|Computers/Computer Science/Distributed Computing|39</Row>"+
			"</DataTable></DataTables></Result>");
		
		try {
			JAXBContext jc = JAXBContext.newInstance(IndexItemInfoResults.class);
			Unmarshaller un = jc.createUnmarshaller();
			IndexItemInfoResults iir = (IndexItemInfoResults)un.unmarshal(reader);
			Assert.assertNotNull(iir);
			
			IndexItemInfoTables iit = iir.getTables();
			Assert.assertNotNull(iit);
			
			IndexItemInfoTable ii = iit.getDataTable();
			Assert.assertNotNull(ii);
			
			List<String> rows = ii.getRows();
			Assert.assertNotNull(rows);
			
			for (String row : rows) {
				System.err.println(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		
	}
}
