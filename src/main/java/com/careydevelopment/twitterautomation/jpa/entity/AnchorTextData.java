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
	private Integer referringDomains = 0;
	
	@Column(name="total_links")
	private BigInteger totalLinks = BigInteger.ZERO;
	
	@Column(name="deleted_links")
	private BigInteger deletedLinks = BigInteger.ZERO;
	
	@Column(name="nofollow_links")
	private BigInteger nofollowLinks = BigInteger.ZERO;
	
	@Column(name="citation_flow")
	private Integer citationFlow = 0;
	
	@Column(name="trust_flow")
	private Integer trustFlow = 0;

	@Column(name="anchor_text")
	private String anchorText = "";
	
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
		return (referringDomains == null) ? 0 : referringDomains;
	}

	public void setReferringDomains(Integer referringDomains) {
		this.referringDomains = referringDomains;
	}

	public BigInteger getTotalLinks() {
		return (totalLinks == null) ? BigInteger.ZERO : totalLinks;
	}

	public void setTotalLinks(BigInteger totalLinks) {
		this.totalLinks = totalLinks;
	}

	public BigInteger getDeletedLinks() {
		return (deletedLinks == null) ? BigInteger.ZERO : deletedLinks;
	}

	public void setDeletedLinks(BigInteger deletedLinks) {
		this.deletedLinks = deletedLinks;
	}

	public BigInteger getNofollowLinks() {
		return (nofollowLinks == null) ? BigInteger.ZERO : nofollowLinks;
	}

	public void setNofollowLinks(BigInteger nofollowLinks) {
		this.nofollowLinks = nofollowLinks;
	}

	public Integer getCitationFlow() {
		return (citationFlow == null) ? 0 : citationFlow;
	}

	public void setCitationFlow(Integer citationFlow) {
		this.citationFlow = citationFlow;
	}

	public Integer getTrustFlow() {
		return (trustFlow == null) ? 0 : trustFlow;
	}

	public void setTrustFlow(Integer trustFlow) {
		this.trustFlow = trustFlow;
	}

	public String getAnchorText() {
		return (anchorText == null) ? "" : anchorText;
	}

	public void setAnchorText(String anchorText) {
		this.anchorText = anchorText;
	}
	
	public String getAnchorTextDisplay() {
		String anchorTextDisplay = anchorText;
		
		if (anchorText != null) {
			if (anchorText.length() > 30) {
				anchorTextDisplay = anchorText.substring(0, 30) + "...";
			} 
		} else {
			anchorTextDisplay = "";
		}
		
		return anchorTextDisplay;
	}
}
