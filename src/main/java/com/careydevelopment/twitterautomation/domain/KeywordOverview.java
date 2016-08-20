package com.careydevelopment.twitterautomation.domain;

public class KeywordOverview {

	private String keyword;
	private Integer searchVolume;
	private Float cpc;
	private Float competition;
	private Integer numberOfResults;
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getSearchVolume() {
		return searchVolume;
	}
	public void setSearchVolume(Integer searchVolume) {
		this.searchVolume = searchVolume;
	}
	public Float getCpc() {
		return cpc;
	}
	public void setCpc(Float cpc) {
		this.cpc = cpc;
	}
	public Float getCompetition() {
		return competition;
	}
	public void setCompetition(Float competition) {
		this.competition = competition;
	}
	public Integer getNumberOfResults() {
		return numberOfResults;
	}
	public void setNumberOfResults(Integer numberOfResults) {
		this.numberOfResults = numberOfResults;
	}
	
	
}
