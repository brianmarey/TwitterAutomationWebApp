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
	private BigInteger extBacklinks;
	
	@Column(name="ref_domains")
	private BigInteger refDomains;
	
	@Column(name="ref_ips")
	private BigInteger refIps;
	
	@Column(name="item_type")
	private Integer itemType; //1 = root domain, 2=subdomain, 3=url
	
	@Column(name="ref_domains_edu")
	private BigInteger refDomainsEdu;
	
	@Column(name="ext_backlinks_edu")
	private BigInteger extBacklinksEdu;
	
	@Column(name="ref_domains_gov")
	private BigInteger refDomainsGov;
	
	@Column(name="ext_backlinks_gov")
	private BigInteger extBacklinksGov;

	@Column(name="ref_domains_edu_exact")
	private BigInteger refDomainsEduExact;
	
	@Column(name="ext_backlinks_edu_exact")
	private BigInteger extBacklinksEduExact;
	
	@Column(name="ref_domains_gov_exact")
	private BigInteger refDomainsGovExact;
	
	@Column(name="ext_backlinks_gov_exact")
	private BigInteger extBacklinksGovExact;
	
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

	public BigInteger getExtBacklinks() {
		return extBacklinks;
	}

	public void setExtBacklinks(BigInteger extBacklinks) {
		this.extBacklinks = extBacklinks;
	}

	public BigInteger getRefDomains() {
		return refDomains;
	}

	public void setRefDomains(BigInteger refDomains) {
		this.refDomains = refDomains;
	}

	public BigInteger getRefIps() {
		return refIps;
	}

	public void setRefIps(BigInteger refIps) {
		this.refIps = refIps;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public BigInteger getRefDomainsEdu() {
		return refDomainsEdu;
	}

	public void setRefDomainsEdu(BigInteger refDomainsEdu) {
		this.refDomainsEdu = refDomainsEdu;
	}

	public BigInteger getExtBacklinksEdu() {
		return extBacklinksEdu;
	}

	public void setExtBacklinksEdu(BigInteger extBacklinksEdu) {
		this.extBacklinksEdu = extBacklinksEdu;
	}

	public BigInteger getRefDomainsGov() {
		return refDomainsGov;
	}

	public void setRefDomainsGov(BigInteger refDomainsGov) {
		this.refDomainsGov = refDomainsGov;
	}

	public BigInteger getExtBacklinksGov() {
		return extBacklinksGov;
	}

	public void setExtBacklinksGov(BigInteger extBacklinksGov) {
		this.extBacklinksGov = extBacklinksGov;
	}

	public BigInteger getRefDomainsEduExact() {
		return refDomainsEduExact;
	}

	public void setRefDomainsEduExact(BigInteger refDomainsEduExact) {
		this.refDomainsEduExact = refDomainsEduExact;
	}

	public BigInteger getExtBacklinksEduExact() {
		return extBacklinksEduExact;
	}

	public void setExtBacklinksEduExact(BigInteger extBacklinksEduExact) {
		this.extBacklinksEduExact = extBacklinksEduExact;
	}

	public BigInteger getRefDomainsGovExact() {
		return refDomainsGovExact;
	}

	public void setRefDomainsGovExact(BigInteger refDomainsGovExact) {
		this.refDomainsGovExact = refDomainsGovExact;
	}

	public BigInteger getExtBacklinksGovExact() {
		return extBacklinksGovExact;
	}

	public void setExtBacklinksGovExact(BigInteger extBacklinksGovExact) {
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
