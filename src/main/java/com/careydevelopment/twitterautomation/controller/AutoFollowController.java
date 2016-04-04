package com.careydevelopment.twitterautomation.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.careydevelopment.helloworld.controller.HelloWorldController;

@Controller
public class AutoFollowController {
	private static final Logger LOGGER = Logger.getLogger(AutoFollowController.class);
	 
    @RequestMapping("/autofollow")
    public String hello(Model model) {
        return "autofollow";
    }
}
