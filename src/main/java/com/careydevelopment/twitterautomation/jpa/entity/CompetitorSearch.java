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
@Table(name = "competitor_search")
public class CompetitorSearch {
	
	public static final String PAID = "paid";
	public static final String ORGANIC = "organic";

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "url_id")
	private ProjectUrl projectUrl;
	
	@Column(name="domain")
	private String domain;
	
	@Column(name="competitor_relevance")
	private Float competitorRelevance;
	
	@Column(name="common_keywords")
	private BigInteger commonKeywords;
	
	@Column(name="type_keywords")
	private Integer typeKeywords;
	
	@Column(name="type_traffic")
	private Integer typeTraffic;
	
	@Column(name="type_cost")
	private Float typeCost;
	
	@Column(name="type")
	private String type; //paid or organic
	
	
	public Float getCompetitorRelevance() {
		return competitorRelevance;
	}
	public void setCompetitorRelevance(Float competitorRelevance) {
		this.competitorRelevance = competitorRelevance;
	}
	public BigInteger getCommonKeywords() {
		return commonKeywords;
	}
	public void setCommonKeywords(BigInteger commonKeywords) {
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
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}	
	
}
