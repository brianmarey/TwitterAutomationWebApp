package com.careydevelopment.twitterautomation.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PrivacyController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PrivacyController.class);


	
	@RequestMapping("/privacy")
	public String home(HttpServletRequest request, Model model) {
        return "privacy";
	}
}
