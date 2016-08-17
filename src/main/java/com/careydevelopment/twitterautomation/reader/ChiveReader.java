package com.careydevelopment.twitterautomation.reader;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.twitterautomation.rss.Parser;
import com.careydevelopment.twitterautomation.rss.Story;
import com.careydevelopment.twitterautomation.util.ImageHelper;

public class ChiveReader {
	private static Logger LOGGER = LoggerFactory.getLogger(ChiveReader.class);
	
	List<String> allUrls = new ArrayList<String>();
	
	public static void main(String[] args) {
		ChiveReader cr = new ChiveReader();
		List<String> urls = cr.getAllUrls();
		
		for (String s : urls) {
			LOGGER.info("I got image " + s);
		}
	}
	
	public ChiveReader() {
		Parser parser = new Parser(40);
		String url = "http://feeds.feedburner.com/thechive";
		List<Story> stories = parser.getMediaAwareStories(url);
		for (Story story : stories) {
			//String title = story.getTitle();
			//System.err.println(title);
			//if (title.indexOf("Morning") > -1) {
				//System.err.println("adding images " + story.getMediaUrls().size());
				List<String> mediaUrls = story.getMediaUrls();
				
				for (String mediaUrl : mediaUrls) {
					if (mediaUrl.indexOf("gravatar") == -1) {
						allUrls.add(ImageHelper.stripParameters(mediaUrl));
					}
				}
				
			//} else if (title.indexOf("Afternoon") > -1) {
				//allUrls.addAll(story.getMediaUrls());
			//}
		}
	}
	
	public List<String> getAllUrls() {
		return allUrls;
	}
}
