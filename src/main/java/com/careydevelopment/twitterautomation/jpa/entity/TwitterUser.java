package com.careydevelopment.twitterautomation.jpa.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "twitter_user")
public class TwitterUser {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "screen_name", nullable = false)
	private String screenName;

	@Column(name = "last_login")
	private Date lastLogin;
	
	@Column(name = "ip_address")
	private String ipAddress;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public List<Role> roles;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "user")
	public UserConfig userConfig;

	@Transient
	private boolean newUser = false;
	
	@Transient
	private boolean badLogin = false;
	
	@Transient
	private String badLoginMessage;

	public TwitterUser() {
	}

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

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public boolean isNewUser() {
		return newUser;
	}

	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public UserConfig getUserConfig() {
		return userConfig;
	}

	public void setUserConfig(UserConfig userConfig) {
		this.userConfig = userConfig;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public boolean isBadLogin() {
		return badLogin;
	}

	public void setBadLogin(boolean badLogin) {
		this.badLogin = badLogin;
	}

	public String getBadLoginMessage() {
		return badLoginMessage;
	}

	public void setBadLoginMessage(String badLoginMessage) {
		this.badLoginMessage = badLoginMessage;
	}
	
}
