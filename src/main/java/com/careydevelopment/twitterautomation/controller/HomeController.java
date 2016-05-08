package com.careydevelopment.twitterautomation.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.careydevelopment.twitterautomation.jpa.entity.TwitterUser;
import com.careydevelopment.twitterautomation.util.Constants;

import twitter4j.Twitter;
import twitter4j.User;

@Controller
public class HomeController {

	
	/*@RequestMapping("/")
	public String home(HttpServletRequest request, Model model) {
		Twitter twitter = (Twitter) request.getSession().getAttribute(Constants.TWITTER);
		
		if (twitter == null) {
			return "redirect:notLoggedIn";
		}

        try {
            TwitterUser u = twitterUserRepository.findByScreenName(twitter.getScreenName());
            model.addAttribute("twitterUser",u);
            
            User user = twitter.showUser(twitter.getId());
            request.getSession().setAttribute(Constants.TWITTER_USER, user);
            
            setDisplayAttributes(model,user);
        } catch (Exception e) {
            LOGGER.error("Problem getting token!",e);
            return "redirect:notLoggedIn";
        }
        
        return "dashboard";
	}*/
	
	@RequestMapping("/")
	public String home(HttpServletRequest request, Model model) {
		        
        return "home";
	}
}
