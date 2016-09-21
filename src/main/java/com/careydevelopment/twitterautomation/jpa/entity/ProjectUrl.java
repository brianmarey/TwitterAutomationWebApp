package com.careydevelopment.twitterautomation.jpa.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.careydevelopment.twitterautomation.util.UrlHelper;

@Entity
@Table(name = "project_url")
public class ProjectUrl {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(min = 5, max = 1024, message = "URL must be between 5 and 1024 characters")
	@Column(name = "url")
	private String url;

	@Column(name = "report_date")
	private Date reportDate;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "projectUrl")
	public PageSpeedInsights pageSpeedInsights;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "projectUrl")
	public DomainRank domainRank;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "projectUrl")
	public IndexItemInfoRow indexItemInfo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public PageSpeedInsights getPageSpeedInsights() {
		return pageSpeedInsights;
	}

	public void setPageSpeedInsights(PageSpeedInsights pageSpeedInsights) {
		this.pageSpeedInsights = pageSpeedInsights;
	}

	public IndexItemInfoRow getIndexItemInfo() {
		return indexItemInfo;
	}

	public void setIndexItemInfo(IndexItemInfoRow indexItemInfo) {
		this.indexItemInfo = indexItemInfo;
	}

	public DomainRank getDomainRank() {
		return domainRank;
	}

	public void setDomainRank(DomainRank domainRank) {
		this.domainRank = domainRank;
	}
	
	
	public boolean isDomain() {
		boolean isDomain = true;
		
		String formattedUrl = UrlHelper.getUnformattedUrl(url);
		
		if (formattedUrl.indexOf("/") > -1) {
			isDomain = false;
		} else {
			String[] parts = formattedUrl.split(".");
			if (parts != null && parts.length > 2) {
				isDomain = false;
			}
		}
		
		return isDomain;
	}
	
	
	public String getDisplayUrl() {
		StringBuilder sb = new StringBuilder();
		
		if (url != null) {
			if (url.length() < 40) {
				sb.append(url);
			} else {
				sb.append(url.substring(0, 38));
				sb.append("...");
			}
		}
		
		return sb.toString();
	}
	
	
	public boolean equals(Object o) {
		boolean equals = false;
		
		if (o != null) {
			if (o instanceof ProjectUrl) {
				ProjectUrl other = (ProjectUrl)o;
				
				if (other.getProject().getId() != null && id != null && other.getProject().getId().equals(id)) {
					if (other.getUrl() != null && url != null && other.getUrl().equals(url)) {
						equals = true;
					}					
				}
			}
		}
		
		return equals;
	}
}
