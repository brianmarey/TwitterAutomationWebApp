package com.careydevelopment.twitterautomation.domain;

import java.util.Date;

public class BacklinkData {

	private String sourceUrl;
	private String anchorText;
	private Boolean flagNoFollow;
	private Boolean flagAltText;
	private String targetUrl;
	private Date firstIndexedDate;
	private Date lastSeenDate;
	private Boolean flagDeleted;
	private Date dateLost;
	private String reasonLost;
	private String linkType;
	private String linkSubtype;
	private Integer targetCitationFlow;
	private Integer targetTrustFlow;
	private Integer sourceCitationFlow;
	private Integer sourceTrustFlow;
	
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getAnchorText() {
		return anchorText;
	}
	public void setAnchorText(String anchorText) {
		this.anchorText = anchorText;
	}
	public Boolean getFlagNoFollow() {
		return flagNoFollow;
	}
	public void setFlagNoFollow(Boolean flagNoFollow) {
		this.flagNoFollow = flagNoFollow;
	}
	public Boolean getFlagAltText() {
		return flagAltText;
	}
	public void setFlagAltText(Boolean flagAltText) {
		this.flagAltText = flagAltText;
	}
	public String getTargetUrl() {
		return targetUrl;
	}
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	public Date getFirstIndexedDate() {
		return firstIndexedDate;
	}
	public void setFirstIndexedDate(Date firstIndexedDate) {
		this.firstIndexedDate = firstIndexedDate;
	}
	public Date getLastSeenDate() {
		return lastSeenDate;
	}
	public void setLastSeenDate(Date lastSeenDate) {
		this.lastSeenDate = lastSeenDate;
	}
	public Boolean getFlagDeleted() {
		return flagDeleted;
	}
	public void setFlagDeleted(Boolean flagDeleted) {
		this.flagDeleted = flagDeleted;
	}
	public Date getDateLost() {
		return dateLost;
	}
	public void setDateLost(Date dateLost) {
		this.dateLost = dateLost;
	}
	public String getReasonLost() {
		return reasonLost;
	}
	public void setReasonLost(String reasonLost) {
		this.reasonLost = reasonLost;
	}
	public String getLinkType() {
		return linkType;
	}
	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	public String getLinkSubtype() {
		return linkSubtype;
	}
	public void setLinkSubtype(String linkSubtype) {
		this.linkSubtype = linkSubtype;
	}
	public Integer getTargetCitationFlow() {
		return targetCitationFlow;
	}
	public void setTargetCitationFlow(Integer targetCitationFlow) {
		this.targetCitationFlow = targetCitationFlow;
	}
	public Integer getTargetTrustFlow() {
		return targetTrustFlow;
	}
	public void setTargetTrustFlow(Integer targetTrustFlow) {
		this.targetTrustFlow = targetTrustFlow;
	}
	public Integer getSourceCitationFlow() {
		return sourceCitationFlow;
	}
	public void setSourceCitationFlow(Integer sourceCitationFlow) {
		this.sourceCitationFlow = sourceCitationFlow;
	}
	public Integer getSourceTrustFlow() {
		return sourceTrustFlow;
	}
	public void setSourceTrustFlow(Integer sourceTrustFlow) {
		this.sourceTrustFlow = sourceTrustFlow;
	}
}
