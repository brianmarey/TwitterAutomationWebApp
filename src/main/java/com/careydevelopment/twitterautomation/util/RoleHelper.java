package com.careydevelopment.twitterautomation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.twitterautomation.jpa.entity.Role;
import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;

public class RoleHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleHelper.class);
	
	public static boolean isAuthorized(TwitterUser user, String roleCheck) {
		boolean authorized = false;
		
		for (Role role : user.getRoles()) {
			if (role.getRoleName().toLowerCase().equals(roleCheck.toLowerCase())) {
				authorized = true;
				break;
			}
		}
		
		return authorized;
	}
}
