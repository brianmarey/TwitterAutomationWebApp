package com.careydevelopment.twitterautomation.jpa.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class SearchResult {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="keyword")
	private String keyword = "";
	
	@Column(name="type")
	private String type = "";
	
	@Column(name="domain")
	private String domain = "";
	
	@Column(name="url")
	private String url = "";
	
	public String getKeyword() {
		return (keyword == null) ? "" : keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getType() {
		return (type == null) ? "" : type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDomain() {
		return (domain == null) ? "" : domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getUrl() {
		return (url == null) ? "" : url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
