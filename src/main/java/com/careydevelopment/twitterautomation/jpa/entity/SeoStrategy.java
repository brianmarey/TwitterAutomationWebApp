package com.careydevelopment.twitterautomation.jpa.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "seo_strategy")
public class SeoStrategy {

	public static final String STATUS_OPEN = "Open";
	public static final String STATUS_CLOSED = "Closed";
	
	public static final String SUCCESS = "Success";
	public static final String FAILURE = "Failure";

	@Transient
	private DateFormat df = new SimpleDateFormat("MM/dd/YYYY");
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "project_url_id")
	private ProjectUrl projectUrl;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Size(min = 3, max = 32, message = "Name must be between 3 and 32 characters")
	@Column(name = "name")
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "seoStrategy")
	private List<StrategyKeyword> strategyKeywords;

	@Column(name = "strategy_type")
	private String strategyType;
	
	//purchased or self
	@Column(name = "strategy_source")
	private String strategySource;

	@Column(name = "purchased_name")
	private String purchasedName;
	
	@Column(name = "purchased_url")
	private String purchasedUrl;

	@Column(name = "strategy_description")
	private String strategyDescription;

	@Column(name = "strategy_status")
	private String strategyStatus;
	
	@Column(name = "strategy_success")
	private String strategySuccess;

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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<StrategyKeyword> getStrategyKeywords() {
		return strategyKeywords;
	}

	public void setStrategyKeywords(List<StrategyKeyword> strategyKeywords) {
		this.strategyKeywords = strategyKeywords;
	}

	public String getStrategyType() {
		return strategyType;
	}

	public void setStrategyType(String strategyType) {
		this.strategyType = strategyType;
	}

	public String getStrategySource() {
		return strategySource;
	}

	public void setStrategySource(String strategySource) {
		this.strategySource = strategySource;
	}

	public String getPurchasedName() {
		return purchasedName;
	}

	public void setPurchasedName(String purchasedName) {
		this.purchasedName = purchasedName;
	}

	public String getPurchasedUrl() {
		return purchasedUrl;
	}

	public void setPurchasedUrl(String purchasedUrl) {
		this.purchasedUrl = purchasedUrl;
	}

	public String getStrategyDescription() {
		return strategyDescription;
	}

	public void setStrategyDescription(String strategyDescription) {
		this.strategyDescription = strategyDescription;
	}

	public String getStrategyStatus() {
		return strategyStatus;
	}

	public void setStrategyStatus(String strategyStatus) {
		this.strategyStatus = strategyStatus;
	}

	public Integer getNumberOfKeywords() {
		Integer keywords = 0;
		
		if (strategyKeywords != null) {
			keywords = strategyKeywords.size();
		}
		
		return keywords;
	}
	
	public String getStartDateDisplay() {
		String startDateDisplay = "";
		
		if (startDate != null) {
			startDateDisplay = df.format(startDate);
		}
		
		return startDateDisplay;
	}

	public String getStrategySuccess() {
		return strategySuccess;
	}

	public void setStrategySuccess(String strategySuccess) {
		this.strategySuccess = strategySuccess;
	}
}
