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
@Table(name = "user_config")
public class UserConfig {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "max_projects")
	private Integer maxProjects;

	@Column(name = "max_urls")
	private Integer maxUrlsPerProject;

	//a config belongs to one and only one user
	//and a user has only one config
	@OneToOne
	@JoinColumn(name = "user_id")
	private TwitterUser user;

	public UserConfig() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMaxProjects() {
		return maxProjects;
	}

	public void setMaxProjects(Integer maxProjects) {
		this.maxProjects = maxProjects;
	}

	public TwitterUser getUser() {
		return user;
	}

	public void setUser(TwitterUser user) {
		this.user = user;
	}

	public Integer getMaxUrlsPerProject() {
		return maxUrlsPerProject;
	}

	public void setMaxUrlsPerProject(Integer maxUrlsPerProject) {
		this.maxUrlsPerProject = maxUrlsPerProject;
	}
	
}
