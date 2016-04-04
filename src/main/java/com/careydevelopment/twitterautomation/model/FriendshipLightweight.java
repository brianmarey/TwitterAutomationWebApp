package com.careydevelopment.twitterautomation.model;

import java.io.Serializable;

public class FriendshipLightweight implements Serializable {

	private static final long serialVersionUID = -5565133562135696950L;
	
	private boolean following;
	private Long id;
	private String screenName;
	
	public boolean isFollowing() {
		return following;
	}
	public void setFollowing(boolean following) {
		this.following = following;
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
}

