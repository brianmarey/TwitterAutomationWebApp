package com.careydevelopment.twitterautomation.controller;

import java.util.ArrayList;
import java.util.List;

import com.careydevelopment.twitterautomation.jpa.entity.Role;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;

public class ControllerHelper {

	public static TwitterUser getBasicUser() {
		TwitterUser owner = new TwitterUser();
		owner.setScreenName("joebob");
		owner.setId(1l);

		Role role = new Role();
		role.setRoleName("basic");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		owner.setRoles(roles);
		
		return owner;
	}
	
	
	public static TwitterUser getNoRolesUser() {
		TwitterUser owner = new TwitterUser();
		owner.setScreenName("darth");
		return owner;
	}
}
