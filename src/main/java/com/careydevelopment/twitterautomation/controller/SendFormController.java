package com.careydevelopment.twitterautomation.controller;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.careydevelopment.mailsupport.MailSender;
import com.careydevelopment.mailsupport.MailSenderException;
import com.careydevelopment.twitterautomation.model.ContactForm;

@Controller
public class SendFormController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SendFormController.class);
	
	private static final String TO_ADDRESS = "careyb@mindspring.com";
	private static final String DEFAULT_SUBJECT = "Message For Carey Development LLC";
	
    @RequestMapping(value="/sendForm",method=RequestMethod.POST)
    public String sendForm(@ModelAttribute ContactForm contactForm, Model model) {    	    
    	model.addAttribute("contactActive", "active");
    	model.addAttribute("output","Thanks! Your message has been sent!");
    	
    	String from = contactForm.getEmail();
    	String subject = DEFAULT_SUBJECT + " From " + contactForm.getName();
    	String body = contactForm.getComment();
    	
    	LOGGER.info("Sending mail " + from + " " + subject + " " + body);
    	
    	try {
    		MailSender.getInstance().sendMail(TO_ADDRESS, from, subject, body);
    	} catch (AddressException ae) {
    		ae.printStackTrace();
    		model.addAttribute("output","Problem sending email!");
    		//throw new RuntimeException("Problem sending email to the specicfied address");
    	} catch (MessagingException me) {
    		me.printStackTrace();
    		model.addAttribute("output","Problem sending email!");
    		//throw new RuntimeException("Problem with email messaging");
    	} catch (MailSenderException se) {
    		se.printStackTrace();
    		model.addAttribute("output","Problem sending email!");
    		//throw new RuntimeException("Problem configuring mail system!");
    	}
    	
    	LOGGER.info("Done sending mail");
    	
        return "general-contact";
    }
        
}
