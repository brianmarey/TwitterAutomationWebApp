package com.careydevelopment.twitterautomation.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.tika.io.IOUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.careydevelopment.twitterautomation.domain.MajesticInfoTable;
import com.careydevelopment.twitterautomation.domain.MajesticInfoTables;
import com.careydevelopment.twitterautomation.domain.MajesticResults;
import com.careydevelopment.twitterautomation.jpa.entity.BacklinkData;
import com.careydevelopment.twitterautomation.jpa.entity.IndexItemInfoRow;
import com.careydevelopment.twitterautomation.service.MajesticService;
import com.careydevelopment.twitterautomation.service.impl.MajesticServiceImpl;

public class DomainSearcher {
	
	MajesticService majesticService = new MajesticServiceImpl();
	
	File outputFile = null;
	FileOutputStream outputFileStream = null;
	
	public static void main(String[] args) {
		DomainSearcher ds = new DomainSearcher();
		ds.go();
	}
	
	
	private void go() {
		File file = new File("/etc/auction_end_tomorrow.xml");
		outputFile = new File("/etc/gooddomains.txt");
	
		FileInputStream stream = null;
		
		try {
			outputFileStream = new FileOutputStream(outputFile);

			SAXParserFactory parserFactor = SAXParserFactory.newInstance();
			SAXParser parser = parserFactor.newSAXParser();

			stream = new FileInputStream(file);
			AuctionXmlHandler handler = new AuctionXmlHandler();
			parser.parse(stream, handler); 
			
			List<AuctionDomain> domains = handler.getDomains();
			
			for (AuctionDomain domain : domains) {
				if (domain.getDomain().indexOf(" ") == -1) {
					fetchIndexInfo(domain.getDomain());					
					IOUtils.write(System.getProperty("line.separator"), outputFileStream);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputFileStream != null) {
				try {
					outputFileStream.close();
				} catch (Exception e) {}
			}
		}
	}
	
	
	private void fetchIndexInfo(String url) {		
		try {
			MajesticResults indexInfoResults = majesticService.getIndexItemInfoUrl(url);
			if (indexInfoResults != null && indexInfoResults.getTables() != null) {
				MajesticInfoTables tables = indexInfoResults.getTables();
				
				if (tables.getDataTable() != null) {
					MajesticInfoTable table = tables.getDataTable();
					
					if (table.getRows() != null && table.getRows().size() > 0) {
						IOUtils.write("checking " + url, outputFileStream);
						checkRow(table,0,url);
						checkRow(table,1,"www." + url);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void checkRow(MajesticInfoTable table, int index, String url) throws IOException {
		String row = table.getRows().get(index);
		IndexItemInfoRow res = IndexItemInfoRowParser.getIndexItemInfoRow(row,table.getHeaders());
		//res.setProjectUrl(url);
		//System.err.println(res.getTrustFlow() + " " + res.getCitationFlow());
		Integer trustFlow = new Integer(res.getTrustFlow());
		Integer citationFlow = new Integer(res.getCitationFlow());
		BigInteger referringDomains = res.getRefDomains();
		BigInteger totalBacklinks = res.getExtBacklinks();
		
		if (trustFlow > 15 && citationFlow > 15 && referringDomains.intValue() > 10) {
			IOUtils.write(System.getProperty("line.separator"), outputFileStream);
			IOUtils.write(url + " has TF " + trustFlow + " CF " + citationFlow + " RD " + referringDomains + " BL " + totalBacklinks, outputFileStream);
			reportBacklinkData(url);
			IOUtils.write(System.getProperty("line.separator"), outputFileStream);
			IOUtils.write(System.getProperty("line.separator"), outputFileStream);
		}
	}
	
	
	private void reportBacklinkData(String url) {
		try {
			MajesticResults backlinkDataResults = majesticService.getBacklinkData(url);
			
			if (backlinkDataResults != null && backlinkDataResults.getTables() != null) {
				MajesticInfoTables tables = backlinkDataResults.getTables();
				
				if (tables.getDataTable() != null) {
					MajesticInfoTable table = tables.getDataTable();
					
					if (table.getRows() != null && table.getRows().size() > 0) {
						int i = 0;
						for (String row : table.getRows()) {
							try {
								//System.err.println("Parsing " + row);
								BacklinkData bd = BacklinkDataParser.getBacklinkData(row,table.getHeaders());
								Integer citationFlow = bd.getSourceCitationFlow();
								Integer trustFlow = bd.getSourceTrustFlow();
								String sourceUrl = bd.getSourceUrl();
								IOUtils.write(System.getProperty("line.separator"), outputFileStream);
								IOUtils.write(sourceUrl + " TF " + trustFlow + " CF " + citationFlow, this.outputFileStream);
								i++;
								if (i>20) break;
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	class AuctionXmlHandler extends DefaultHandler {
		private String content = "";
		private List<AuctionDomain> domains = new ArrayList<AuctionDomain>();
		AuctionDomain domain = new AuctionDomain();
		
		@Override
		public void startElement(String uri, String localName, String qName, 
			Attributes attributes) throws SAXException {
			
		}
		
		
		@Override
		public void endElement(String uri, String localName, String qName) 
			throws SAXException {
		
			if (qName.equals("title") ) {
				domain.setDomain(content.toLowerCase());
			} else if (qName.equals("description")) {
				domain.setDescription(content);
			} else if (qName.equals("link")) {
				domain.setLink(content);
			} else if (qName.equals("item")) {
				domains.add(domain);
				domain = new AuctionDomain();
			}
			
			content = "";
		}
		
		
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (content == null) content = String.copyValueOf(ch, start, length).trim();
			else content +=  String.copyValueOf(ch, start, length).trim();
		}
		
		public List<AuctionDomain> getDomains() {
			return domains;
		}
	}
	
	
	class AuctionDomain {
		private String domain;
		private String description;
		private String link;
		
		public String getDomain() {
			return domain;
		}
		public void setDomain(String domain) {
			this.domain = domain;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
	}
}
