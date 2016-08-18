package com.careydevelopment.twitterautomation.domain;

public class DomainSearchKeyword {

	private String keyword;
	private Integer position;
	private Integer previousPosition;
	private Integer positionDifference;
	private Integer searchVolume;
	private Float cpc;
	private String url;
	private Float trafficPercent;
	private Float trafficCostPercent;
	private Float competition;
	private Integer numberOfResults;
	private String type; //paid or organic
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public Integer getPreviousPosition() {
		return previousPosition;
	}
	public void setPreviousPosition(Integer previousPosition) {
		this.previousPosition = previousPosition;
	}
	public Integer getPositionDifference() {
		return positionDifference;
	}
	public void setPositionDifference(Integer positionDifference) {
		this.positionDifference = positionDifference;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Float getTrafficPercent() {
		return trafficPercent;
	}
	public void setTrafficPercent(Float trafficPercent) {
		this.trafficPercent = trafficPercent;
	}
	public Float getTrafficCostPercent() {
		return trafficCostPercent;
	}
	public void setTrafficCostPercent(Float trafficCostPercent) {
		this.trafficCostPercent = trafficCostPercent;
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


