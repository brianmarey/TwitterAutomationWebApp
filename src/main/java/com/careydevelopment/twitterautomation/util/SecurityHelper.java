package com.careydevelopment.twitterautomation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityHelper.class);
	
	public static String getUsername() {
		String username = "";
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			//LOGGER.info("user name is from UserDetails " + username);
		} else {
			username = principal.toString();
			//LOGGER.info("user name is " + username);
		}	
		
		return username;
	}
}
