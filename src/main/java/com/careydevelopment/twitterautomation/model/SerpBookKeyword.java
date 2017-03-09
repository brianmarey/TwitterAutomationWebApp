package com.careydevelopment.twitterautomation.model;

public class SerpBookKeyword {
	private String category;
	private String url;
	private String keyword = "";
	private String region;
	private String language;
	private Integer start = 0;
	private Integer grank = 0;
	private Integer brank = 0;
	private Integer yrank = 0;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getGrank() {
		return grank;
	}
	public void setGrank(Integer grank) {
		this.grank = grank;
	}
	public Integer getBrank() {
		return brank;
	}
	public void setBrank(Integer brank) {
		this.brank = brank;
	}
	public Integer getYrank() {
		return yrank;
	}
	public void setYrank(Integer yrank) {
		this.yrank = yrank;
	}
	
	

}
