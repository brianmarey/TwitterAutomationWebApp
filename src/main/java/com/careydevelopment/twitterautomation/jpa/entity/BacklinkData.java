package com.careydevelopment.twitterautomation.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "backlink_data")
public class BacklinkData {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "url_id")
	private ProjectUrl projectUrl;
	
	@Column(name="source_url")
	private String sourceUrl = "";
	
	@Column(name="anchor_text")
	private String anchorText = "";
	
	@Column(name="flag_no_follow")
	private Boolean flagNoFollow = false;
	
	@Column(name="flag_alt_text")
	private Boolean flagAltText = false;
	
	@Column(name="target_url")
	private String targetUrl = "";
	
	@Column(name="first_indexed_date")
	private Date firstIndexedDate = new Date();
	
	@Column(name="last_seen_date")
	private Date lastSeenDate = new Date();
	
	@Column(name="flag_deleted")
	private Boolean flagDeleted = false;
	
	@Column(name="date_lost")
	private Date dateLost = new Date();
	
	@Column(name="reason_lost")
	private String reasonLost = "";
	
	@Column(name="link_type")
	private String linkType = "";
	
	@Column(name="link_subtype")
	private String linkSubtype = "";
	
	@Column(name="target_citation_flow")
	private Integer targetCitationFlow = 0;
	
	@Column(name="target_trust_flow")
	private Integer targetTrustFlow = 0;
	
	@Column(name="source_citation_flow")
	private Integer sourceCitationFlow = 0;
	
	@Column(name="source_trust_flow")
	private Integer sourceTrustFlow = 0;

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

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		if (sourceUrl == null) sourceUrl = "";
		this.sourceUrl = sourceUrl;
	}

	public String getAnchorText() {
		return anchorText;
	}

	public void setAnchorText(String anchorText) {
		if (anchorText == null) anchorText = "";
		this.anchorText = anchorText;
	}

	public Boolean getFlagNoFollow() {
		return flagNoFollow;
	}

	public void setFlagNoFollow(Boolean flagNoFollow) {
		if (flagNoFollow == null) flagNoFollow = false;
		this.flagNoFollow = flagNoFollow;
	}

	public Boolean getFlagAltText() {
		return flagAltText;
	}

	public void setFlagAltText(Boolean flagAltText) {
		if (flagAltText == null) flagAltText = false;
		this.flagAltText = flagAltText;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		if (targetUrl == null) targetUrl = "";
		this.targetUrl = targetUrl;
	}

	public Date getFirstIndexedDate() {
		return firstIndexedDate;
	}

	public void setFirstIndexedDate(Date firstIndexedDate) {
		if (firstIndexedDate == null) firstIndexedDate = new Date();
		this.firstIndexedDate = firstIndexedDate;
	}

	public Date getLastSeenDate() {
		return lastSeenDate;
	}

	public void setLastSeenDate(Date lastSeenDate) {
		if (lastSeenDate == null) lastSeenDate = new Date();
		this.lastSeenDate = lastSeenDate;
	}

	public Boolean getFlagDeleted() {
		return flagDeleted;
	}

	public void setFlagDeleted(Boolean flagDeleted) {
		if (flagDeleted == null) flagDeleted = false;
		this.flagDeleted = flagDeleted;
	}

	public Date getDateLost() {
		return dateLost;
	}

	public void setDateLost(Date dateLost) {
		if (dateLost == null) dateLost = new Date();
		this.dateLost = dateLost;
	}

	public String getReasonLost() {
		return reasonLost;
	}

	public void setReasonLost(String reasonLost) {
		if (reasonLost == null) reasonLost = "";
		this.reasonLost = reasonLost;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		if (linkType == null) linkType = "";
		this.linkType = linkType;
	}

	public String getLinkSubtype() {
		return linkSubtype;
	}

	public void setLinkSubtype(String linkSubtype) {
		if (linkSubtype == null) linkSubtype = "";
		this.linkSubtype = linkSubtype;
	}

	public Integer getTargetCitationFlow() {
		return targetCitationFlow;
	}

	public void setTargetCitationFlow(Integer targetCitationFlow) {
		if (targetCitationFlow == null) targetCitationFlow = 0;
		this.targetCitationFlow = targetCitationFlow;
	}

	public Integer getTargetTrustFlow() {
		return targetTrustFlow;
	}

	public void setTargetTrustFlow(Integer targetTrustFlow) {
		this.targetTrustFlow = targetTrustFlow;
	}

	public Integer getSourceCitationFlow() {
		if (sourceCitationFlow == null) sourceCitationFlow = 0;
		return sourceCitationFlow;
	}

	public void setSourceCitationFlow(Integer sourceCitationFlow) {
		this.sourceCitationFlow = sourceCitationFlow;
	}

	public Integer getSourceTrustFlow() {
		return sourceTrustFlow;
	}

	public void setSourceTrustFlow(Integer sourceTrustFlow) {
		if (sourceTrustFlow == null) sourceTrustFlow = 0;
		this.sourceTrustFlow = sourceTrustFlow;
	}	

}
