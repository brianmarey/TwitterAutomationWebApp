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
import javax.persistence.Table;

@Entity
@Table(name = "follow_run")
public class FollowRun {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "run_id",nullable=false)
	private Long id;
	
	//a run belongs to one and only one user
	//but a user can have many runs
	@ManyToOne
	@JoinColumn(name = "username")
	private User user;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "followRun")
	public List<Followee> followees;
	
	@Column(name="run_start", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date runStart;
	
	@Column(name="run_complete")
	private Date runComplete;

	public FollowRun(){}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getRunStart() {
		return runStart;
	}

	public void setRunStart(Date runStart) {
		this.runStart = runStart;
	}

	public Date getRunComplete() {
		return runComplete;
	}

	public void setRunComplete(Date runComplete) {
		this.runComplete = runComplete;
	}

	public List<Followee> getFollowees() {
		return followees;
	}

	public void setFollowees(List<Followee> followees) {
		this.followees = followees;
	}
	
	
}
