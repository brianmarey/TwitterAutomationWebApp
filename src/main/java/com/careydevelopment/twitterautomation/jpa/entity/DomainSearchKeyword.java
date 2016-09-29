package com.careydevelopment.twitterautomation.jpa.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "domain_search_keyword")
public class DomainSearchKeyword {
	
	public static final String ORGANIC = "organic";
	public static final String PAID = "paid";
	public static final String ORGANIC_MOBILE = "organic-mobile";

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "url_id")
	private ProjectUrl projectUrl;
	
	@Column(name="keyword")
	private String keyword = "";
	
	@Column(name="position")
	private Integer position = 0;
	
	@Column(name="previous_position")
	private Integer previousPosition = 0;
	
	@Column(name="position_difference")
	private Integer positionDifference = 0;
	
	@Column(name="search_volume")
	private BigInteger searchVolume = BigInteger.ZERO;
	
	@Column(name="cpc")
	private Float cpc = 0f;
	
	@Column(name="traffic_percent")
	private Float trafficPercent = 0f;
	
	@Column(name="traffic_cost_percent")
	private Float trafficCostPercent = 0f;
	
	@Column(name="competition")
	private Float competition = 0f;
	
	@Column(name="number_of_results")
	private BigInteger numberOfResults = BigInteger.ZERO;
	
	@Column(name="type")
	private String type = "organic"; //paid or organic
	
	@Column(name="url")
	private String url = "";
	
	
	public String getType() {
		return (type == null) ? "organic" : type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKeyword() {
		return (keyword == null) ? "" : keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getPosition() {
		return (position == null) ? 0 : position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public Integer getPreviousPosition() {
		return (previousPosition == null) ? 0 : previousPosition;
	}
	public void setPreviousPosition(Integer previousPosition) {
		this.previousPosition = previousPosition;
	}
	public Integer getPositionDifference() {
		return (positionDifference == null) ? 0 : positionDifference;
	}
	public void setPositionDifference(Integer positionDifference) {
		this.positionDifference = positionDifference;
	}
	public BigInteger getSearchVolume() {
		return (searchVolume == null) ? BigInteger.ZERO : searchVolume;
	}
	public void setSearchVolume(BigInteger searchVolume) {
		this.searchVolume = searchVolume;
	}
	public Float getCpc() {
		return (cpc == null) ? 0f : cpc;
	}
	public void setCpc(Float cpc) {
		this.cpc = cpc;
	}
	public Float getTrafficPercent() {
		return (trafficPercent == null) ? 0f : trafficPercent;
	}
	public void setTrafficPercent(Float trafficPercent) {
		this.trafficPercent = trafficPercent;
	}
	public Float getTrafficCostPercent() {
		return (trafficCostPercent == null) ? 0f : trafficCostPercent;
	}
	public void setTrafficCostPercent(Float trafficCostPercent) {
		this.trafficCostPercent = trafficCostPercent;
	}
	public Float getCompetition() {
		return (competition == null) ? 0f : competition;
	}
	public void setCompetition(Float competition) {
		this.competition = competition;
	}
	public BigInteger getNumberOfResults() {
		return (numberOfResults == null) ? BigInteger.ZERO : numberOfResults;
	}
	public void setNumberOfResults(BigInteger numberOfResults) {
		this.numberOfResults = numberOfResults;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ProjectUrl getProjectUrl() {
		return projectUrl;
	}
	public void setProjectUrl(ProjectUrl projectUrl) {
		this.projectUrl = projectUrl;
	}	

	public String getUrl() {
		return (url == null) ? "" : url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getKeywordDisplay() {
		String keywordDisplay = keyword;
		
		if (keyword != null) {
			if (keyword.length() > 30) {
				keywordDisplay = keyword.substring(0, 30) + "...";
			} 
		} else {
			keywordDisplay = "";
		}
		
		return keywordDisplay;
	}
	
	
	public String getDisplayUrl() {
		StringBuilder sb = new StringBuilder();
		
		if (url != null) {
			if (url.length() < 40) {
				sb.append(url);
			} else {
				sb.append(url.substring(0, 38));
				sb.append("...");
			}
		} else {
			sb.append("");
		}
		
		return sb.toString();
	}
	
	
	public boolean equals(Object other) {
		boolean equals = false;
		
		if (other instanceof DomainSearchKeyword) {
			DomainSearchKeyword key = (DomainSearchKeyword)other;
			if (key.getKeyword() != null && keyword != null && key.getKeyword().equals(keyword)) {
				equals = true;
			}
		}
		
		return equals;
	}
}


