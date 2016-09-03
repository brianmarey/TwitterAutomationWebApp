package com.careydevelopment.twitterautomation.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "domain_rank")
public class DomainRank {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "url_id")
	private ProjectUrl projectUrl;
	
	@Column(name="domain")
	private String domain;
	
	@Column(name="organic_keywords")
	private Integer organicKeywords;
	
	@Column(name="organic_traffic")
	private Integer organicTraffic;
	
	@Column(name="organic_cost")
	private Integer organicCost;
	
	@Column(name="adwords_keywords")
	private Integer adwordsKeywords;
	
	@Column(name="adwords_traffic")
	private Integer adwordsTraffic;
	
	@Column(name="adwords_cost")
	private Integer adwordsCost;
	
	@Column(name="rank")
	private Integer rank;
	
	
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public Integer getOrganicKeywords() {
		return organicKeywords;
	}
	public void setOrganicKeywords(Integer organicKeywords) {
		this.organicKeywords = organicKeywords;
	}
	public Integer getOrganicTraffic() {
		return organicTraffic;
	}
	public void setOrganicTraffic(Integer organicTraffic) {
		this.organicTraffic = organicTraffic;
	}
	public Integer getOrganicCost() {
		return organicCost;
	}
	public void setOrganicCost(Integer organicCost) {
		this.organicCost = organicCost;
	}
	public Integer getAdwordsKeywords() {
		return adwordsKeywords;
	}
	public void setAdwordsKeywords(Integer adwordsKeywords) {
		this.adwordsKeywords = adwordsKeywords;
	}
	public Integer getAdwordsTraffic() {
		return adwordsTraffic;
	}
	public void setAdwordsTraffic(Integer adwordsTraffic) {
		this.adwordsTraffic = adwordsTraffic;
	}
	public Integer getAdwordsCost() {
		return adwordsCost;
	}
	public void setAdwordsCost(Integer adwordsCost) {
		this.adwordsCost = adwordsCost;
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
	
	public String getAdwordsCostDisplay() {
		StringBuilder sb = new StringBuilder();
		sb.append("$");
		sb.append(adwordsCost);
		
		return sb.toString();
	}
	
	public String getOrganicCostDisplay() {
		StringBuilder sb = new StringBuilder();
		sb.append("$");
		sb.append(organicCost);
		
		return sb.toString();
	}
}
