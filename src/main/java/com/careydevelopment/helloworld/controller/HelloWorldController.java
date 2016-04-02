package com.careydevelopment.helloworld.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/hello")
public class HelloWorldController {
	
	private static final Logger LOGGER = Logger.getLogger(HelloWorldController.class);
 
   @RequestMapping(method = RequestMethod.GET)
   public String printHello(ModelMap model) {
	   
	   LOGGER.debug("I'm in HELLLOWORLD!!!!!!!!!!!!!!!!!!!!!");
	   
      model.addAttribute("message", "Hello Spring MVC Framework!");

      return "hello";
   }

}
