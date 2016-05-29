package com.careydevelopment.twitterautomation.rss;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.careydevelopment.twitterautomation.util.UrlHelper;

public class Parser {
	private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);

	private int maxStories = 10;
	private boolean mediaAware = false;
	
	public Parser() {
		
	}
	
	
	public Parser(int maxStories) {
		this.maxStories = maxStories;
	}
	
	public Parser(int maxStories, boolean mediaAware) {
		this.maxStories = maxStories;
		this.mediaAware = mediaAware;
	}
	
	public List<Story> getStories(String url) {
		List<Story> stories = new ArrayList<Story>();
		
		InputSource ins = null;
		
		try {
			SAXParserFactory parserFactor = SAXParserFactory.newInstance();
			SAXParser parser = parserFactor.newSAXParser();
			RssHandler handler = new RssHandler(maxStories,mediaAware);
				
			ins = UrlHelper.getInputSourceFromUrl(url);
				
			parser.parse(ins, handler); 
			
			for (Story story : handler.getStories()){
				stories.add(story);
			}
		} catch (Exception e) {
			LOGGER.error("Problem reading url " + url,e);
		} finally {
			if (ins != null && ins.getCharacterStream() != null) {
				try {
					ins.getCharacterStream().close();
				} catch (Exception e) {
					
				}
			}
		}
		
		return stories;
	}
	
	
	public List<Story> getMediaAwareStories(String url) {
		List<Story> stories = new ArrayList<Story>();
		
		try {
			SAXParserFactory parserFactor = SAXParserFactory.newInstance();
				SAXParser parser = parserFactor.newSAXParser();
				RssHandler handler = new RssHandler(maxStories);
				handler.setHandleMedia(true);
				parser.parse(url, handler); 
			
				//Printing the list of employees obtained from XML
				for (Story story : handler.getStories()){
				      //System.out.println(story.getTitle());
				      stories.add(story);
			    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return stories;
	}
	
}
