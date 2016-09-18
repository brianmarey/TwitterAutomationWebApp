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
@Table(name = "strategy_keyword")
public class StrategyKeyword {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "strategy_id")
	private SeoStrategy seoStrategy;
	
	@Column(name = "keyword")
	private String keyword;
	
	@Column(name="original_rank")
	private Integer originalRank;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SeoStrategy getSeoStrategy() {
		return seoStrategy;
	}

	public void setSeoStrategy(SeoStrategy seoStrategy) {
		this.seoStrategy = seoStrategy;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getOriginalRank() {
		return originalRank;
	}

	public void setOriginalRank(Integer originalRank) {
		this.originalRank = originalRank;
	}
	
}
