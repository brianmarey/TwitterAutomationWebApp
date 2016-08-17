package com.careydevelopment.twitterautomation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.careydevelopment.twitterautomation.model.ContactForm;

@Controller
public class ContactController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);
	
    @RequestMapping("/contact")
    public String contact(Model model) {    	    
    	model.addAttribute("contactForm",new ContactForm());
        return "general-contact";
    }
        
}
