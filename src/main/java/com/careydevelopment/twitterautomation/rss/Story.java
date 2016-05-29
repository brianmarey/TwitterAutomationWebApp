package com.careydevelopment.twitterautomation.rss;

import java.util.ArrayList;
import java.util.List;

public class Story {

	private String title;
	private String url;
	private List<String> mediaUrls = new ArrayList<String>();
	private String description;
	private boolean removeSecure = false;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		//System.err.println("here remove sec is " + removeSecure);
		if (!removeSecure) {
			return url;
		} else {
			//System.err.println("adjudsting url");
			if (url.startsWith("https")) {
				System.err.println("it starts");
				url = "http" + url.substring(5,url.length());
				//System.err.println("Now url is " + url);
			}
			
			return url;
		}
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getMediaUrls() {
		return mediaUrls;
	}

	public void setMediaUrls(List<String> mediaUrls) {
		this.mediaUrls = mediaUrls;
	}
	
	public void addMediaUrl(String url) {
		if (mediaUrls == null) {
			mediaUrls = new ArrayList<String>();
		}
		mediaUrls.add(url);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isRemoveSecure() {
		return removeSecure;
	}

	public void setRemoveSecure(boolean removeSecure) {
		this.removeSecure = removeSecure;
	}
}
