package com.careydevelopment.twitterautomation.rss;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



public class RssHandler extends DefaultHandler {

	private List<Story> stories = new ArrayList<Story>();
	private Story story;
	private String content;
	private int maxStories = 5;
	private boolean handleMedia = false;
	
	public RssHandler(int maxStories) {
		this.maxStories = maxStories;
	}
	
	public void setHandleMedia(boolean b) {
		handleMedia = b;
	}
	
	public void startElement(String uri, String localName, String qName, 
		Attributes attributes) throws SAXException {
		
		if (stories.size() < maxStories) {
			//System.err.println(qName);

			if ("item".equals(qName)) {
				//System.err.println("we have the item ");
				story = new Story();
			} else if (handleMedia && "media:content".equals(qName)) {
				String url = attributes.getValue("url");
				if (story != null) {
					//System.err.println("adding " + url);
					story.addMediaUrl(url);
				}
			}
		}
	}
	
	
	private String sanitizeContent(String orig) {
		StringBuilder sanitized = new StringBuilder("");
		
		char[] chars = orig.toCharArray();
		for (int i=0;i<chars.length;i++) {
			int val = (int)chars[i];
			
			switch (val) {
				case 8216 :
					sanitized.append(" '");
					break;
				case 8217:
					sanitized.append("'");
					break;
				default:
					sanitized.append(chars[i]);
					break;
			}
		}
		
		
		return sanitized.toString();
	}
	
	
	public void endElement(String uri, String localName, String qName) 
		throws SAXException {
		if (stories.size() < maxStories) {			
			//System.err.println("ending " + qName);
			if ("title".equals(qName)) {
				if (story != null) {
					story.setTitle(sanitizeContent(content));
				}
			} else if ("link".equals(qName)) {
				if (story != null) {
					story.setUrl(content);
					//System.err.println("setting content to " + story.getTitle());
				}
			} else if ("item".equals(qName)) {
				//System.err.println("adding story");
				stories.add(story);
			} else if ("description".equals(qName)) {
				if (story != null) {
					story.setDescription(content);
				}
			}
		}
		content = null;
	}
	
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (content == null) content = String.copyValueOf(ch, start, length).trim();
		else content +=  String.copyValueOf(ch, start, length).trim();
	}
	
	
	public List<Story> getStories() {
		return stories;
	}
}
