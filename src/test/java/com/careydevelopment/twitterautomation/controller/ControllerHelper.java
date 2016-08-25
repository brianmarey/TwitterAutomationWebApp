package com.careydevelopment.twitterautomation.controller;

import java.util.ArrayList;
import java.util.List;

import com.careydevelopment.twitterautomation.jpa.entity.Role;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.jpa.entity.UserConfig;

public class ControllerHelper {
	
	public static String NOT_AUTHORIZED = "redirect:notAuthorized";
	public static String NOT_LOGGED_IN = "redirect:notLoggedIn";
	
	
	private static UserConfig getUserConfig() {
		UserConfig userConfig = new UserConfig();
		userConfig.setMaxProjects(5);
		userConfig.setMaxUrlsPerProject(5);

		return userConfig;
	}
 
	
	public static TwitterUser getBasicUser() {
		TwitterUser owner = new TwitterUser();
		owner.setScreenName("joebob");
		owner.setId(1l);
		owner.setUserConfig(getUserConfig());
		
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
		owner.setId(2l);
		return owner;
	}
}
