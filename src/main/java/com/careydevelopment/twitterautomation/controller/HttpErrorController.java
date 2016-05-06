package com.careydevelopment.twitterautomation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HttpErrorController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpErrorController.class);
	
	 @RequestMapping("/404")
	 public String error404(){
		 LOGGER.info("Forwarding to 404");
		 return "404";
	 }
}
