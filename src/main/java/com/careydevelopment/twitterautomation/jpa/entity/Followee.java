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
@Table(name = "followees")
public class Followee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "followee_id",nullable=false)
	private Long id;
	
	@Column(name = "screen_name")
	private String screenName;
	
	@Column(name = "twitter_id")
	private Long twitterId;
	
	//a followee belongs to one and only one run
	//but a run can have many followees
	@ManyToOne
	@JoinColumn(name = "run_id")
	private FollowRun followRun;
	
	@Column(name="status")
	private int status;
	
	public Followee(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public Long getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(Long twitterId) {
		this.twitterId = twitterId;
	}

	public FollowRun getFollowRun() {
		return followRun;
	}

	public void setFollowRun(FollowRun followRun) {
		this.followRun = followRun;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
