package com.careydevelopment.twitterautomation.jpa.entity;

import java.math.BigInteger;

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
	private BigInteger organicKeywords;
	
	@Column(name="organic_traffic")
	private BigInteger organicTraffic;
	
	@Column(name="organic_cost")
	private BigInteger organicCost;
	
	@Column(name="adwords_keywords")
	private BigInteger adwordsKeywords;
	
	@Column(name="adwords_traffic")
	private BigInteger adwordsTraffic;
	
	@Column(name="adwords_cost")
	private BigInteger adwordsCost;
	
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
	public BigInteger getOrganicKeywords() {
		return organicKeywords;
	}
	public void setOrganicKeywords(BigInteger organicKeywords) {
		this.organicKeywords = organicKeywords;
	}
	public BigInteger getOrganicTraffic() {
		return organicTraffic;
	}
	public void setOrganicTraffic(BigInteger organicTraffic) {
		this.organicTraffic = organicTraffic;
	}
	public BigInteger getOrganicCost() {
		return organicCost;
	}
	public void setOrganicCost(BigInteger organicCost) {
		this.organicCost = organicCost;
	}
	public BigInteger getAdwordsKeywords() {
		return adwordsKeywords;
	}
	public void setAdwordsKeywords(BigInteger adwordsKeywords) {
		this.adwordsKeywords = adwordsKeywords;
	}
	public BigInteger getAdwordsTraffic() {
		return adwordsTraffic;
	}
	public void setAdwordsTraffic(BigInteger adwordsTraffic) {
		this.adwordsTraffic = adwordsTraffic;
	}
	public BigInteger getAdwordsCost() {
		return adwordsCost;
	}
	public void setAdwordsCost(BigInteger adwordsCost) {
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
