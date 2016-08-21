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
import javax.validation.constraints.Size;

@Entity
@Table(name = "project")
public class Project {
	
	@Id
	@Column(name="project_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Size(min=3, max=32, message="Project name must be between 3 and 32 characters")
	@Column(name="project_name")
	private String name;
	
	@Column(name="project_status")
	private String status;
	
	@ManyToOne
	@JoinColumn(name="owner_id")
	private TwitterUser owner;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TwitterUser getOwner() {
		return owner;
	}

	public void setOwner(TwitterUser owner) {
		this.owner = owner;
	}
}
