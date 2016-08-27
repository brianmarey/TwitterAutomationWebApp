package com.careydevelopment.twitterautomation.domain;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Assert;
import org.junit.Test;

import com.careydevelopment.twitterautomation.jpa.entity.BacklinkData;
import com.careydevelopment.twitterautomation.jpa.entity.IndexItemInfoRow;
import com.careydevelopment.twitterautomation.util.BacklinkDataParser;
import com.careydevelopment.twitterautomation.util.IndexItemInfoRowParser;

import junit.framework.TestCase;

public class MajesticResultsTest extends TestCase {

	@Test
	public void testUnmarshallIndexItemInfo() {
		
		StringReader reader = new StringReader("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
			"<Result Code=\"OK\" ErrorMessage=\"\" FullError=\"\">"+
			"<GlobalVars FirstBackLinkDate=\"2014-01-29\" IndexBuildDate=\"2014-04-30 23:18:43\" IndexType=\"1\" MostRecentBackLinkDate=\"2014-04-29\" ServerBuild=\"2014-04-30 22:34:53\" ServerName=\"QUACKYO\" ServerVersion=\"1.0.5233.38846\" UniqueIndexID=\"20140430231843-FRESH\"/>"+
			"<DataTables Count=\"1\">"+
			"<DataTable Name=\"Results\" RowsCount=\"1\" Headers=\"ItemNum|Item|ResultCode|Status|ExtBackLinks|RefDomains|AnalysisResUnitsCost|ACRank|ItemType|IndexedURLs|GetTopBackLinksAnalysisResUnitsCost|DownloadBacklinksAnalysisResUnitsCost|RefIPs|RefSubNets|RefDomainsEDU|ExtBackLinksEDU|RefDomainsGOV|ExtBackLinksGOV|RefDomainsEDU_Exact|ExtBackLinksEDU_Exact|RefDomainsGOV_Exact|ExtBackLinksGOV_Exact|CrawledFlag|LastCrawlDate|LastCrawlResult|RedirectFlag|FinalRedirectResult|OutDomainsExternal|OutLinksExternal|OutLinksInternal|LastSeen|Title|RedirectTo|CitationFlow|TrustFlow|TrustMetric|TopicalTrustFlow_Topic_0|TopicalTrustFlow_Value_0|TopicalTrustFlow_Topic_1|TopicalTrustFlow_Value_1|TopicalTrustFlow_Topic_2|TopicalTrustFlow_Value_2\">"+
			"<Row>0|http://www.majestic.com/|OK|Found|510654|6382|510654|10|3|1|5000|510655|5354|4396|52|641|1|1|4|16|1|1|True|2014-04-28|DownloadedSuccessfully|False| |5|5|60| |Majestic : Backlink Checker Site Explorer| |91|52|52|Computers/Internet/Web Design and Development|41|Computers/Software/Online Training|39|Computers/Computer Science/Distributed Computing|39</Row>"+
			"</DataTable></DataTables></Result>");
		
		try {
			JAXBContext jc = JAXBContext.newInstance(MajesticResults.class);
			Unmarshaller un = jc.createUnmarshaller();
			MajesticResults iir = (MajesticResults)un.unmarshal(reader);
			Assert.assertNotNull(iir);
			
			MajesticInfoTables iit = iir.getTables();
			Assert.assertNotNull(iit);
			
			MajesticInfoTable ii = iit.getDataTable();
			Assert.assertNotNull(ii);
			
			List<String> rows = ii.getRows();
			Assert.assertNotNull(rows);
			
			//0|http://www.majestic.com/|OK|Found|510654|6382|510654|10|3|1|5000|510655|5354|4396|52|641|1|1|4|16|1|1|True|2014-04-28|DownloadedSuccessfully|False| |5|5|60| |Majestic : Backlink Checker Site Explorer| |91|52|52|Computers/Internet/Web Design and Development|41|Computers/Software/Online Training|39|Computers/Computer Science/Distributed Computing|39

			Assert.assertEquals(1, rows.size());
			String row = rows.get(0);
			
			IndexItemInfoRow res = IndexItemInfoRowParser.getIndexItemInfoRow(row);
			Assert.assertEquals(new Integer(0), res.getItemNum());
			Assert.assertEquals("http://www.majestic.com/", res.getItem());
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}		
	}
	
	
	@Test
	public void testUnmarshallBacklinkData() {
		
		StringReader reader = new StringReader("<Result Code=\"OK\" ErrorMessage=\"\" FullError=\"\">"+
			"<GlobalVars IndexBuildDate=\"2014-06-17 14:09:10\" IndexType=\"1\" ServerBuild=\"2014-06-10 17:35:22\" ServerName=\"ECHOLIMA\" ServerVersion=\"1.0.5274.29861\" UniqueIndexID=\"20140617140910-FRESH\"/>"+
			"<DataTables Count=\"1\"><DataTable Name=\"BackLinks\" RowsCount=\"5\" Headers=\"SourceURL|ACRank|AnchorText|Date|FlagRedirect|FlagFrame|FlagNoFollow|FlagImages|FlagDeleted|FlagAltText|FlagMention|TargetURL|DomainID|FirstIndexedDate|LastSeenDate|DateLost|ReasonLost|LinkType|LinkSubType|TargetCitationFlow|TargetTrustFlow|TargetTopicalTrustFlow_Topic_0|TargetTopicalTrustFlow_Value_0|SourceCitationFlow|SourceTrustFlow|SourceTopicalTrustFlow_Topic_0|SourceTopicalTrustFlow_Value_0\" AvailableLines=\"50000\" BackLinkItem=\"majestic.com\" BackLinkType=\"BackLinks\" Count=\"5\" From=\"0\" Item=\"majestic.com\" ItemType=\"RootDomain\" MaxRefDomainTopics=\"1\" MaxSourceTopics=\"1\" MaxTargetTopics=\"10\" OrigItem=\"majestic.com\" RefDomainTopicsCount=\"1\" SourceTopicsCount=\"1\" TargetTopicsCount=\"1\" TotalBackLinks=\"7455614\" TotalLines=\"8115767\" TotalMatches=\"5\">"+
			"<Row>http://www.majestic12.co.uk/|12|majestic-seo|2014-06-15|0|0|0|0|0|0|0|http://www.majestic.com/|-1|2014-03-17|2014-06-15| | |TextLink|TextLink_Normal|61|51|Computers/Internet/Web Design and Development|42|78|61|Computers/Computer Science/Distributed Computing|55</Row>"+
			"<Row>http://www.majestic12.co.uk/|12|here|2014-06-15|0|0|0|0|0|0|0|http://blog.majestic.com/general/majestic-12-confirms-googles-milestone-more-than-1-trillion-urls-found/|-1|2014-03-16|2014-06-15| | |TextLink|TextLink_Normal|57|37|Computers/Computer Science/Distributed Computing|34|78|61|Computers/Computer Science/Distributed Computing|55</Row>"+
			"<Row>http://blog.majestic12.co.uk/|10|http://blog.majestic.com|2014-06-15|0|0|0|0|0|0|0|http://blog.majestic.com/|-1|2014-03-17|2014-06-15| | |TextLink|TextLink_Normal|53|37|Computers/Computer Science/Distributed Computing|34|69|55|Computers/Computer Science/Distributed Computing|54</Row>"+
			"<Row>http://blog.majestic12.co.uk/|10|to here|2014-06-15|0|0|0|0|0|0|0|http://blog.majestic.com/|-1|2014-03-17|2014-06-15| | |TextLink|TextLink_Normal|53|37|Computers/Computer Science/Distributed Computing|34|69|55|Computers/Computer Science/Distributed Computing|54</Row>"+
			"<Row>http://halls.md/|8|view ranking information about halls.md in the majestic million. click here to view ranking information for halls.md in the majestic...|2014-06-15|0|0|1|1|0|0|0|http://www.majestic.com/stats/majestic-million?domain=halls.md|-1|2014-05-31|2014-06-15| | |ImageLink|ImageLink_Normal|0|0| | |52|63|Health/Medicine|55</Row>"+
			"</DataTable></DataTables></Result>");
		
		try {
			JAXBContext jc = JAXBContext.newInstance(MajesticResults.class);
			Unmarshaller un = jc.createUnmarshaller();
			MajesticResults iir = (MajesticResults)un.unmarshal(reader);
			Assert.assertNotNull(iir);
			
			MajesticInfoTables iit = iir.getTables();
			Assert.assertNotNull(iit);
			
			MajesticInfoTable ii = iit.getDataTable();
			Assert.assertNotNull(ii);
			
			List<String> rows = ii.getRows();
			Assert.assertNotNull(rows);
			
			Assert.assertEquals(5, rows.size());
			String row = rows.get(0);
			
			BacklinkData data = BacklinkDataParser.getBacklinkData(row);
			Assert.assertEquals("http://www.majestic12.co.uk/", data.getSourceUrl());
			Assert.assertEquals("majestic-seo", data.getAnchorText());
			Assert.assertFalse(data.getFlagAltText());
			Assert.assertEquals("http://www.majestic.com/", data.getTargetUrl());
			Assert.assertNull(data.getDateLost());
			Assert.assertEquals(new Integer(61), data.getTargetCitationFlow());
			Assert.assertEquals(new Integer(51), data.getTargetTrustFlow());
			Assert.assertEquals(new Integer(78), data.getSourceCitationFlow());
			Assert.assertEquals(new Integer(61), data.getSourceTrustFlow());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}		
	}
}
