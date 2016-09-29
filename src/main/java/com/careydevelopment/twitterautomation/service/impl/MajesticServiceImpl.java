package com.careydevelopment.twitterautomation.service.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.careydevelopment.propertiessupport.PropertiesFactory;
import com.careydevelopment.propertiessupport.PropertiesFactoryException;
import com.careydevelopment.propertiessupport.PropertiesFile;
import com.careydevelopment.twitterautomation.domain.MajesticResults;
import com.careydevelopment.twitterautomation.service.MajesticService;
import com.careydevelopment.twitterautomation.util.AnchorTextDataParser;
import com.careydevelopment.twitterautomation.util.BacklinkDataParser;
import com.careydevelopment.twitterautomation.util.IndexItemInfoRowParser;
import com.careydevelopment.twitterautomation.util.UrlHelper;

@Component
public class MajesticServiceImpl implements MajesticService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MajesticServiceImpl.class);
	
	private static final int REQUEST_INDEX_ITEM_INFO = 1;
	private static final int REQUEST_BACKLINK_DATA = 2;
	private static final int REQUEST_ANCHOR_TEXT_DATA = 3;

	
	String apiKey = "";
	
	public MajesticServiceImpl() {
		try {
			Properties props = PropertiesFactory.getProperties(PropertiesFile.MAJESTIC_PROPERTIES);
			apiKey = props.getProperty("majestic.api.key");
		} catch (PropertiesFactoryException pe) {
			LOGGER.error("Problem retrieving properties for Majestic!",pe);
		}
	}
	
	
	@Override
	public MajesticResults getIndexItemInfo(String url) {
		MajesticResults iir = null;
		
		String response = getResponse(url, REQUEST_INDEX_ITEM_INFO);
		StringReader reader = new StringReader(response);
		
		/*StringReader reader = new StringReader("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
			"<Result Code=\"OK\" ErrorMessage=\"\" FullError=\"\">"+
			"<GlobalVars FirstBackLinkDate=\"2014-01-29\" IndexBuildDate=\"2014-04-30 23:18:43\" IndexType=\"1\" MostRecentBackLinkDate=\"2014-04-29\" ServerBuild=\"2014-04-30 22:34:53\" ServerName=\"QUACKYO\" ServerVersion=\"1.0.5233.38846\" UniqueIndexID=\"20140430231843-FRESH\"/>"+
			"<DataTables Count=\"1\">"+
			"<DataTable Name=\"Results\" RowsCount=\"1\" Headers=\"ItemNum|Item|ResultCode|Status|ExtBackLinks|RefDomains|AnalysisResUnitsCost|ACRank|ItemType|IndexedURLs|GetTopBackLinksAnalysisResUnitsCost|DownloadBacklinksAnalysisResUnitsCost|RefIPs|RefSubNets|RefDomainsEDU|ExtBackLinksEDU|RefDomainsGOV|ExtBackLinksGOV|RefDomainsEDU_Exact|ExtBackLinksEDU_Exact|RefDomainsGOV_Exact|ExtBackLinksGOV_Exact|CrawledFlag|LastCrawlDate|LastCrawlResult|RedirectFlag|FinalRedirectResult|OutDomainsExternal|OutLinksExternal|OutLinksInternal|LastSeen|Title|RedirectTo|CitationFlow|TrustFlow|TrustMetric|TopicalTrustFlow_Topic_0|TopicalTrustFlow_Value_0|TopicalTrustFlow_Topic_1|TopicalTrustFlow_Value_1|TopicalTrustFlow_Topic_2|TopicalTrustFlow_Value_2\">"+
			"<Row>0|http://www.majestic.com/|OK|Found|510654|6382|510654|10|3|1|5000|510655|5354|4396|52|641|1|1|4|16|1|1|True|2014-04-28|DownloadedSuccessfully|False| |5|5|60| |Majestic : Backlink Checker Site Explorer| |91|52|52|Computers/Internet/Web Design and Development|41|Computers/Software/Online Training|39|Computers/Computer Science/Distributed Computing|39</Row>"+
			"</DataTable></DataTables></Result>");
			*/
		try {
			JAXBContext jc = JAXBContext.newInstance(MajesticResults.class);
			Unmarshaller un = jc.createUnmarshaller();
			iir = (MajesticResults)un.unmarshal(reader);
		} catch (Exception e) {
			LOGGER.error("Problem retrieving Majestic data!",e);
		}
		
		return iir;
	}

	
	@Override
	public MajesticResults getBacklinkData(String url) {
		MajesticResults iir = null;

		String response = getResponse(url, REQUEST_BACKLINK_DATA);
		StringReader reader = new StringReader(response);
		
		/*StringReader reader = new StringReader("<Result Code=\"OK\" ErrorMessage=\"\" FullError=\"\">"+
			"<GlobalVars IndexBuildDate=\"2014-06-17 14:09:10\" IndexType=\"1\" ServerBuild=\"2014-06-10 17:35:22\" ServerName=\"ECHOLIMA\" ServerVersion=\"1.0.5274.29861\" UniqueIndexID=\"20140617140910-FRESH\"/>"+
			"<DataTables Count=\"1\"><DataTable Name=\"BackLinks\" RowsCount=\"5\" Headers=\"SourceURL|ACRank|AnchorText|Date|FlagRedirect|FlagFrame|FlagNoFollow|FlagImages|FlagDeleted|FlagAltText|FlagMention|TargetURL|DomainID|FirstIndexedDate|LastSeenDate|DateLost|ReasonLost|LinkType|LinkSubType|TargetCitationFlow|TargetTrustFlow|TargetTopicalTrustFlow_Topic_0|TargetTopicalTrustFlow_Value_0|SourceCitationFlow|SourceTrustFlow|SourceTopicalTrustFlow_Topic_0|SourceTopicalTrustFlow_Value_0\" AvailableLines=\"50000\" BackLinkItem=\"majestic.com\" BackLinkType=\"BackLinks\" Count=\"5\" From=\"0\" Item=\"majestic.com\" ItemType=\"RootDomain\" MaxRefDomainTopics=\"1\" MaxSourceTopics=\"1\" MaxTargetTopics=\"10\" OrigItem=\"majestic.com\" RefDomainTopicsCount=\"1\" SourceTopicsCount=\"1\" TargetTopicsCount=\"1\" TotalBackLinks=\"7455614\" TotalLines=\"8115767\" TotalMatches=\"5\">"+
			"<Row>http://www.majestic12.co.uk/|12|majestic-seo|2014-06-15|0|0|0|0|0|0|0|http://www.majestic.com/|-1|2014-03-17|2014-06-15| | |TextLink|TextLink_Normal|61|51|Computers/Internet/Web Design and Development|42|78|61|Computers/Computer Science/Distributed Computing|55</Row>"+
			"<Row>http://www.majestic12.co.uk/|12|here|2014-06-15|0|0|0|0|0|0|0|http://blog.majestic.com/general/majestic-12-confirms-googles-milestone-more-than-1-trillion-urls-found/|-1|2014-03-16|2014-06-15| | |TextLink|TextLink_Normal|57|37|Computers/Computer Science/Distributed Computing|34|78|61|Computers/Computer Science/Distributed Computing|55</Row>"+
			"<Row>http://blog.majestic12.co.uk/|10|http://blog.majestic.com|2014-06-15|0|0|0|0|0|0|0|http://blog.majestic.com/|-1|2014-03-17|2014-06-15| | |TextLink|TextLink_Normal|53|37|Computers/Computer Science/Distributed Computing|34|69|55|Computers/Computer Science/Distributed Computing|54</Row>"+
			"<Row>http://blog.majestic12.co.uk/|10|to here|2014-06-15|0|0|0|0|0|0|0|http://blog.majestic.com/|-1|2014-03-17|2014-06-15| | |TextLink|TextLink_Normal|53|37|Computers/Computer Science/Distributed Computing|34|69|55|Computers/Computer Science/Distributed Computing|54</Row>"+
			"<Row>http://halls.md/|8|view ranking information about halls.md in the majestic million. click here to view ranking information for halls.md in the majestic...|2014-06-15|0|0|1|1|0|0|0|http://www.majestic.com/stats/majestic-million?domain=halls.md|-1|2014-05-31|2014-06-15| | |ImageLink|ImageLink_Normal|0|0| | |52|63|Health/Medicine|55</Row>"+
			"</DataTable></DataTables></Result>");
			*/
		try {
			JAXBContext jc = JAXBContext.newInstance(MajesticResults.class);
			Unmarshaller un = jc.createUnmarshaller();
			iir = (MajesticResults)un.unmarshal(reader);	
		} catch (Exception e) {
			LOGGER.error("Problem retrieving Majestic data!",e);
		}
		
		return iir;
	}
	
	
	@Override
	public MajesticResults getAnchorTextData(String url) {
		MajesticResults iir = null;

		String response = getResponse(url, REQUEST_ANCHOR_TEXT_DATA);
		StringReader reader = new StringReader(response);
		
		/*StringReader reader = new StringReader("<Result Code=\"OK\" ErrorMessage=\"\" FullError=\"\">"+
			"<GlobalVars IndexBuildDate=\"2014-06-17 14:09:10\" IndexType=\"1\" ServerBuild=\"2014-06-10 17:35:22\" ServerName=\"ECHOLIMA\" ServerVersion=\"1.0.5274.29861\" UniqueIndexID=\"20140617140910-FRESH\"/>"+
			"<DataTables Count=\"1\"><DataTable Name=\"AnchorText\" RowsCount=\"10\" Headers=\"AnchorText|RefDomains|TotalLinks|DeletedLinks|NoFollowLinks|EstimatedLinkCitationFlow|EstimatedLinkTrustFlow\" AvailableLines=\"50000\" BackLinkItem=\"majestic.com\" BackLinkType=\"BackLinks\" Count=\"5\" From=\"0\" Item=\"majestic.com\" ItemType=\"RootDomain\" MaxRefDomainTopics=\"1\" MaxSourceTopics=\"1\" MaxTargetTopics=\"10\" OrigItem=\"majestic.com\" RefDomainTopicsCount=\"1\" SourceTopicsCount=\"1\" TargetTopicsCount=\"1\" TotalBackLinks=\"7455614\" TotalLines=\"8115767\" TotalMatches=\"5\">"+
			"<Row>Majestic|2032|21035|8575|1979|72|72</Row>"+
			"<Row>majestic|596|3525|1228|512|62|59</Row>"+
			"<Row>www.majestic.com|488|2342|1285|556|48|42</Row>"+
			"<Row>http://www.majestic.com/|408|3581|973|1068|54|55</Row>"+
			"<Row>majestic.com|337|1218|412|177|48|39</Row>"+
			"<Row>http://www.majestic.com|252|1438|297|713|51|38</Row>"+
			"<Row>majestic|243|865|339|140|52|49</Row>"+
			"<Row>majestic-seo|203|16420|647|123|63|36</Row>"+
			"<Row>majestic-seo : competitive link intelligence|103|402|143|127|32|30</Row>"+
			"<Row>Majestic website|61|168|93|59|35|34</Row>"+
			"</DataTable></DataTables></Result>");
			*/
		try {
			JAXBContext jc = JAXBContext.newInstance(MajesticResults.class);
			Unmarshaller un = jc.createUnmarshaller();
			iir = (MajesticResults)un.unmarshal(reader);	
		} catch (Exception e) {
			LOGGER.error("Problem retrieving Majestic data!",e);
		}
		
		return iir;
	}
	
	
	private String getResponse (String url, int type) {
		String response = "";

		StringBuilder sb = new StringBuilder();
		sb.append("http://api.majestic.com/api/xml?app_api_key=");
		sb.append(apiKey);
		sb.append("&cmd=");
		
		switch (type) {
			case REQUEST_INDEX_ITEM_INFO:
				sb.append("GetIndexItemInfo&items=1&item0=");
				sb.append(url);
				sb.append("&datasource=fresh");
				break;
			case REQUEST_BACKLINK_DATA:
				sb.append("GetBackLinkData&item=");
				sb.append(url);
				sb.append("&Count=100&MaxSameSourceURLs=1&Mode=1&datasource=fresh");
				break;
			case REQUEST_ANCHOR_TEXT_DATA:
				sb.append("GetAnchorText&item=");
				sb.append(url);
				sb.append("&Count=20&datasource=fresh");
				break;
		}

		System.err.println("Majestic url is " + sb.toString());
		
		response = UrlHelper.getUrlContents(sb.toString());
		response = response.trim().replaceFirst("^([\\W]+)<","<");
		//response = response.replaceAll( "([\\ud800-\\udbff\\udc00-\\udfff])", "");
		
		System.err.println("Response is " + response);
		
		return response;
	}
	
	
	public static void main(String[] args) {
		MajesticServiceImpl ii = new MajesticServiceImpl();
		MajesticResults iir = ii.getIndexItemInfo("huffingtonpost.com"); //ii.getAnchorTextData("amztracker.com"); //ii.getBacklinkData("amztracker.com");   //
		System.err.println(iir.getTables().getDataTable().getRows().get(0));
		System.err.println(iir.getTables().getDataTable().getHeaders());
		
		//BacklinkDataParser.getBacklinkData(iir.getTables().getDataTable().getRows().get(0),iir.getTables().getDataTable().getHeaders());
		IndexItemInfoRowParser.getIndexItemInfoRow(iir.getTables().getDataTable().getRows().get(0),iir.getTables().getDataTable().getHeaders());
		//AnchorTextDataParser.getAnchorTextData(iir.getTables().getDataTable().getRows().get(0), iir.getTables().getDataTable().getHeaders());
	}
}
