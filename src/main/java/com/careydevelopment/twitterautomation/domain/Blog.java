package com.careydevelopment.twitterautomation.domain;

public class Blog {

	private String rssUrl = "";
	private String blogName = "";
	private String blurbStarter = "";
	private String imageStarter="<meta property=\"og:image\" content=\"";
	private String blurbEnder = "xxxnothingtolookforatall";
	private boolean imageFromUrl = false;
	
	
	public String getBlurbEnder() {
		return blurbEnder;
	}
	public void setBlurbEnder(String blurbEnder) {
		this.blurbEnder = blurbEnder;
	}
	public String getRssUrl() {
		return rssUrl;
	}
	public void setRssUrl(String rssUrl) {
		this.rssUrl = rssUrl;
	}
	public String getBlogName() {
		return blogName;
	}
	public void setBlogName(String blogName) {
		this.blogName = blogName;
	}
	public String getBlurbStarter() {
		return blurbStarter;
	}
	public void setBlurbStarter(String blurbStarter) {
		this.blurbStarter = blurbStarter;
	}
	public String getImageStarter() {
		return imageStarter;
	}
	public void setImageStarter(String imageStarter) {
		this.imageStarter = imageStarter;
	}
	public boolean isImageFromUrl() {
		return imageFromUrl;
	}
	public void setImageFromUrl(boolean imageFromUrl) {
		this.imageFromUrl = imageFromUrl;
	}


}
