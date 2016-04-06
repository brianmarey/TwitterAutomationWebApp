package com.careydevelopment.twitterautomation.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "username",nullable=false)
    private String id;

	@Column(name = "password",nullable=false)
    private String password;
	
	public User() {}

	public String getUsername() {
		return id;
	}

	public void setUsername(String username) {
		this.id = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
