package com.careydevelopment.twitterautomation.jpa.entity;

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

	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "url_id")
	private ProjectUrl projectUrl;
	
	@Column(name="keyword")
	private String keyword;
	
	@Column(name="position")
	private Integer position;
	
	@Column(name="previous_position")
	private Integer previousPosition;
	
	@Column(name="position_difference")
	private Integer positionDifference;
	
	@Column(name="search_volume")
	private Integer searchVolume;
	
	@Column(name="cpc")
	private Float cpc;
	
	@Column(name="traffic_percent")
	private Float trafficPercent;
	
	@Column(name="traffic_cost_percent")
	private Float trafficCostPercent;
	
	@Column(name="competition")
	private Float competition;
	
	@Column(name="number_of_results")
	private Integer numberOfResults;
	
	@Column(name="type")
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
	
	public String getKeywordDisplay() {
		String keywordDisplay = keyword;
		
		if (keyword != null) {
			if (keyword.length() > 30) {
				keywordDisplay = keyword.substring(0, 30) + "...";
			} 
		}
		
		return keywordDisplay;
	}
}


