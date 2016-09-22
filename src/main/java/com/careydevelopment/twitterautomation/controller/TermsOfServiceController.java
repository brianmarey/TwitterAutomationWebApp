package com.careydevelopment.twitterautomation.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TermsOfServiceController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TermsOfServiceController.class);
	
	@RequestMapping("/termsOfService")
	public String termsOfService(HttpServletRequest request, Model model) {
        return "termsOfService";
	}
}
