package com.careydevelopment.twitterautomation.reader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.twitterautomation.domain.Blog;
import com.careydevelopment.twitterautomation.jpa.entity.ViralContent;
import com.careydevelopment.twitterautomation.rss.Parser;
import com.careydevelopment.twitterautomation.rss.Story;
import com.careydevelopment.twitterautomation.util.FacebookHelper;
import com.careydevelopment.twitterautomation.util.StoryHelper;
import com.careydevelopment.twitterautomation.util.UrlHelper;

public class BlogReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(BlogReader.class);
	
	private Blog blog;
	
	public static void main(String[] args) {
		Blog b = new Blog();
		b.setBlogName("BuzzFeed");
		b.setBlurbStarter("class=\"post-body\">");
		b.setRssUrl("https://www.buzzfeed.com/index.xml");
		
		BlogReader gb = new BlogReader(b);
		List<ViralContent> vs = gb.getStories();
		
		for (ViralContent v : vs) {
			LOGGER.info(v.getBlog());
			LOGGER.info(v.getHeadline());
			LOGGER.info(v.getImageUrl());
			LOGGER.info(v.getUrl());
			LOGGER.info("" + v.getDateSeen());
			LOGGER.info(""+v.getShareCount());
			LOGGER.info("\n\nn");
		}
		
	}
	
	public BlogReader(Blog b) {
		blog=b;
	}

	
	public List<ViralContent> getStories() {
		Parser parser = new Parser(40,true);
		//LOGGER.info("RSS URL is " + blog.getRssUrl());
			
		List<Story> stories = parser.getStories(blog.getRssUrl());
		List<ViralContent> vs = new ArrayList<ViralContent>();				
		
		for (Story story : stories) {
			ViralContent v = new ViralContent();
			
			String url = UrlHelper.stripParameters(story.getUrl());
			v.setUrl(url);
			
			int shares = FacebookHelper.getShares(url);
			v.setShareCount(shares);

			if (shares > 500) {
				v.setHeadline(story.getTitle());
				v.setBlog(blog.getBlogName());
				v.setDateSeen(new Date());
				
				String featuredImage = StoryHelper.getFeaturedImage(story,blog);			
				v.setImageUrl(featuredImage);
				
				vs.add(v);
			}			
		}
		
		return vs;
	}	
}
