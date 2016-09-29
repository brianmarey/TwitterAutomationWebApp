package com.careydevelopment.twitterautomation.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


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
	private String keyword = "";
	
	@Column(name="original_rank")
	private Integer originalRank = 0;

	@Column(name="original_mobile_rank")
	private Integer originalMobileRank = 0;
	
	@Transient
	private Integer currentRank = 0;

	@Transient
	private Integer currentMobileRank = 0;
	
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
		return (keyword == null) ? "" : keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getOriginalRank() {
		return (originalRank == null) ? 0 : originalRank;
	}

	public void setOriginalRank(Integer originalRank) {
		this.originalRank = originalRank;
	}

	public Integer getCurrentRank() {
		return currentRank;
	}

	public void setCurrentMobileRank(Integer currentMobileRank) {
		this.currentMobileRank = currentMobileRank;
	}

	public Integer getCurrentMobileRank() {
		return currentMobileRank;
	}

	public void setCurrentRank(Integer currentRank) {
		this.currentRank = currentRank;
	}
	
	public Integer getOriginalMobileRank() {
		return originalMobileRank;
	}

	public void setOriginalMobileRank(Integer originalMobileRank) {
		this.originalMobileRank = originalMobileRank;
	}

	public String getOriginalRankDisplay() {
		String display = "N/A";
		
		if (originalRank != null && originalRank > 0) {
			display = originalRank.toString();
		}
		
		return display;
	}
	
	public String getOriginalMobileRankDisplay() {
		String display = "N/A";
		
		if (originalMobileRank != null && originalMobileRank > 0) {
			display = originalMobileRank.toString();
		}
		
		return display;
	}
	
	public String getCurrentRankDisplay() {
		String display = "N/A";
		
		if (currentRank != null && currentRank > 0) {
			display = currentRank.toString();
		}
		
		return display;
	}
	
	
	public String getCurrentMobileRankDisplay() {
		String display = "N/A";
		
		if (currentMobileRank != null && currentMobileRank > 0) {
			display = currentMobileRank.toString();
		}
		
		return display;
	}
	
	
	public boolean getImprovement() {
		boolean improvement = false;
		
		if (originalRank != null && currentRank != null) {
			if (originalRank == 0 && currentRank > 0) {
				improvement = true;
			} else if (originalRank > currentRank) {
				improvement = true;
			}
		}
		
		return improvement;
	}
	
	
	public boolean getMobileImprovement() {
		boolean improvement = false;
		
		if (originalMobileRank != null && currentMobileRank != null) {
			if (originalMobileRank == 0 && currentMobileRank > 0) {
				improvement = true;
			} else if (originalMobileRank > currentMobileRank) {
				improvement = true;
			}
		}
		
		return improvement;
	}
	
	
	public boolean getSame() {
		boolean same = true;
		
		if (originalRank != null && currentRank != null) {
			if (originalRank != currentRank) {
				same = false;
			}
		}
		
		return same;
	}
	

	public boolean getMobileSame() {
		boolean same = true;
		
		if (originalMobileRank != null && currentMobileRank != null) {
			if (originalMobileRank != currentMobileRank) {
				same = false;
			}
		}
		
		return same;
	}

	
	
	public String getDifference() {
		String difference = "";
		String orig = getOriginalRankDisplay();
		
		if (!getSame() && !"N/A".equals(orig)) {
			if (originalRank > currentRank) {
				difference = "+" + (originalRank - currentRank);
			} else {
				difference = "" + (originalRank - currentRank);
			}
		}
		
		return difference;
	}
	
	
	public String getMobileDifference() {
		String difference = "";
		String orig = getOriginalMobileRankDisplay();
		
		if (!getMobileSame() && !"N/A".equals(orig)) {
			if (originalMobileRank > currentMobileRank) {
				difference = "+" + (originalMobileRank - currentMobileRank);
			} else {
				difference = "" + (originalMobileRank - currentMobileRank);
			}
		}
		
		return difference;
	}
}
