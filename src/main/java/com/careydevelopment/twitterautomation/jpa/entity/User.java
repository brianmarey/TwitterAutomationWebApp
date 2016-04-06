package com.careydevelopment.twitterautomation.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "username",nullable=false)
    private String id;

	@Column(name = "password",nullable=false)
    private String password;

	@Transient
	private String username;
	
	public User() {}

	public String getUsername() {
		return id;
	}

	public void setUsername(String username) {
		this.id = username;
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		this.username = id;
	}
	
	
}
