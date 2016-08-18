package com.careydevelopment.twitterautomation.domain;

public class CompetitorSearch {

	private String domain;
	private Float competitorRelevance;
	private Integer commonKeywords;
	private Integer typeKeywords;
	private Integer typeTraffic;
	private Float typeCost;
	private String type; //paid or organic
	
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public Float getCompetitorRelevance() {
		return competitorRelevance;
	}
	public void setCompetitorRelevance(Float competitorRelevance) {
		this.competitorRelevance = competitorRelevance;
	}
	public Integer getCommonKeywords() {
		return commonKeywords;
	}
	public void setCommonKeywords(Integer commonKeywords) {
		this.commonKeywords = commonKeywords;
	}
	public Integer getTypeKeywords() {
		return typeKeywords;
	}
	public void setTypeKeywords(Integer typeKeywords) {
		this.typeKeywords = typeKeywords;
	}
	public Integer getTypeTraffic() {
		return typeTraffic;
	}
	public void setTypeTraffic(Integer typeTraffic) {
		this.typeTraffic = typeTraffic;
	}
	public Float getTypeCost() {
		return typeCost;
	}
	public void setTypeCost(Float typeCost) {
		this.typeCost = typeCost;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
