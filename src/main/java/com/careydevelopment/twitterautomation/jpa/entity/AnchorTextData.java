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
@Table(name = "anchor_text_data")
public class AnchorTextData {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "url_id")
	private ProjectUrl projectUrl;
	
	@Column(name="referring_domains")
	private Integer referringDomains;
	
	@Column(name="total_links")
	private Integer totalLinks;
	
	@Column(name="deleted_links")
	private Integer deletedLinks;
	
	@Column(name="nofollow_links")
	private Integer nofollowLinks;
	
	@Column(name="citation_flow")
	private Integer citationFlow;
	
	@Column(name="trust_flow")
	private Integer trustFlow;

	@Column(name="anchor_text")
	private String anchorText;
	
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

	public Integer getReferringDomains() {
		return referringDomains;
	}

	public void setReferringDomains(Integer referringDomains) {
		this.referringDomains = referringDomains;
	}

	public Integer getTotalLinks() {
		return totalLinks;
	}

	public void setTotalLinks(Integer totalLinks) {
		this.totalLinks = totalLinks;
	}

	public Integer getDeletedLinks() {
		return deletedLinks;
	}

	public void setDeletedLinks(Integer deletedLinks) {
		this.deletedLinks = deletedLinks;
	}

	public Integer getNofollowLinks() {
		return nofollowLinks;
	}

	public void setNofollowLinks(Integer nofollowLinks) {
		this.nofollowLinks = nofollowLinks;
	}

	public Integer getCitationFlow() {
		return citationFlow;
	}

	public void setCitationFlow(Integer citationFlow) {
		this.citationFlow = citationFlow;
	}

	public Integer getTrustFlow() {
		return trustFlow;
	}

	public void setTrustFlow(Integer trustFlow) {
		this.trustFlow = trustFlow;
	}

	public String getAnchorText() {
		return anchorText;
	}

	public void setAnchorText(String anchorText) {
		this.anchorText = anchorText;
	}
	
}
