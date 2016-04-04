package com.careydevelopment.twitterautomation.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.careydevelopment.helloworld.controller.HelloWorldController;

@Controller
public class LoginController {
	private static final Logger LOGGER = Logger.getLogger(LoginController.class);
	 
    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
