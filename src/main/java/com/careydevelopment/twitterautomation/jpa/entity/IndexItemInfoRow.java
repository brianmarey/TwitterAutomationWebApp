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
@Table(name = "index_item_info")
public class IndexItemInfoRow {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "url_id")
	private ProjectUrl projectUrl;
	
	@Column(name="item_num")
	private Integer itemNum;
	
	@Column(name="item")
	private String item;
	
	@Column(name="result_code")
	private String resultCode;
	
	@Column(name="status")
	private String status;
	
	@Column(name="ext_backlinks")
	private Integer extBacklinks;
	
	@Column(name="ref_domains")
	private Integer refDomains;
	
	@Column(name="ref_ips")
	private Integer refIps;
	
	@Column(name="item_type")
	private Integer itemType; //1 = root domain, 2=subdomain, 3=url
	
	@Column(name="ref_domains_edu")
	private Integer refDomainsEdu;
	
	@Column(name="ext_backlinks_edu")
	private Integer extBacklinksEdu;
	
	@Column(name="ref_domains_gov")
	private Integer refDomainsGov;
	
	@Column(name="ext_backlinks_gov")
	private Integer extBacklinksGov;

	@Column(name="ref_domains_edu_exact")
	private Integer refDomainsEduExact;
	
	@Column(name="ext_backlinks_edu_exact")
	private Integer extBacklinksEduExact;
	
	@Column(name="ref_domains_gov_exact")
	private Integer refDomainsGovExact;
	
	@Column(name="ext_backlinks_gov_exact")
	private Integer extBacklinksGovExact;
	
	@Column(name="title")
	private String title;
	
	@Column(name="citation_flow")
	private Integer citationFlow;
	
	@Column(name="trust_flow")
	private Integer trustFlow;

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

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getExtBacklinks() {
		return extBacklinks;
	}

	public void setExtBacklinks(Integer extBacklinks) {
		this.extBacklinks = extBacklinks;
	}

	public Integer getRefDomains() {
		return refDomains;
	}

	public void setRefDomains(Integer refDomains) {
		this.refDomains = refDomains;
	}

	public Integer getRefIps() {
		return refIps;
	}

	public void setRefIps(Integer refIps) {
		this.refIps = refIps;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Integer getRefDomainsEdu() {
		return refDomainsEdu;
	}

	public void setRefDomainsEdu(Integer refDomainsEdu) {
		this.refDomainsEdu = refDomainsEdu;
	}

	public Integer getExtBacklinksEdu() {
		return extBacklinksEdu;
	}

	public void setExtBacklinksEdu(Integer extBacklinksEdu) {
		this.extBacklinksEdu = extBacklinksEdu;
	}

	public Integer getRefDomainsGov() {
		return refDomainsGov;
	}

	public void setRefDomainsGov(Integer refDomainsGov) {
		this.refDomainsGov = refDomainsGov;
	}

	public Integer getExtBacklinksGov() {
		return extBacklinksGov;
	}

	public void setExtBacklinksGov(Integer extBacklinksGov) {
		this.extBacklinksGov = extBacklinksGov;
	}

	public Integer getRefDomainsEduExact() {
		return refDomainsEduExact;
	}

	public void setRefDomainsEduExact(Integer refDomainsEduExact) {
		this.refDomainsEduExact = refDomainsEduExact;
	}

	public Integer getExtBacklinksEduExact() {
		return extBacklinksEduExact;
	}

	public void setExtBacklinksEduExact(Integer extBacklinksEduExact) {
		this.extBacklinksEduExact = extBacklinksEduExact;
	}

	public Integer getRefDomainsGovExact() {
		return refDomainsGovExact;
	}

	public void setRefDomainsGovExact(Integer refDomainsGovExact) {
		this.refDomainsGovExact = refDomainsGovExact;
	}

	public Integer getExtBacklinksGovExact() {
		return extBacklinksGovExact;
	}

	public void setExtBacklinksGovExact(Integer extBacklinksGovExact) {
		this.extBacklinksGovExact = extBacklinksGovExact;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	
	
}
