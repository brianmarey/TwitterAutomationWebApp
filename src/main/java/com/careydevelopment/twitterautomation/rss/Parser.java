package com.careydevelopment.twitterautomation.rss;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import com.careydevelopment.twitterautomation.util.UrlHelper;

public class Parser {

	private int maxStories = 10;
	
	public Parser() {
		
	}
	
	
	public Parser(int maxStories) {
		this.maxStories = maxStories;
	}
	
	
	public List<Story> getStories(String url) {
		List<Story> stories = new ArrayList<Story>();
		
		try {
			SAXParserFactory parserFactor = SAXParserFactory.newInstance();
			SAXParser parser = parserFactor.newSAXParser();
			RssHandler handler = new RssHandler(maxStories);
				
			InputSource ins = UrlHelper.getInputSourceFromUrl(url);
				
			parser.parse(ins, handler); 
			
			for (Story story : handler.getStories()){
				stories.add(story);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
