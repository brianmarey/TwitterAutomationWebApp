package com.careydevelopment.twitterautomation.util;

import java.util.List;

import com.careydevelopment.twitterautomation.domain.Blog;
import com.careydevelopment.twitterautomation.rss.Story;

public class StoryHelper {

	public static String getFeaturedImage(Story story, Blog blog) {
		String featuredImage = "";
		
		if (blog.isImageFromUrl()){
			featuredImage = FacebookHelper.getImageFromUrl(story.getUrl());
		} else {
			featuredImage = getFeaturedImageFromMedia(story);
		}

		return featuredImage;
	}
	
	
	public static String getFeaturedImageFromMedia(Story story) {
		String featuredImage = "";
		
		List<String> medias = story.getMediaUrls();
		for (String media : medias) {
			if (media.indexOf(".jpg") > -1 || media.indexOf(".gif") > -1 || media.indexOf(".png") > -1 || media.indexOf(".jpeg") > -1) {
				featuredImage = ImageHelper.stripParameters(media);
				break;
			}
		}
		
		return featuredImage;
	}

}
