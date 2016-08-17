package com.careydevelopment.twitterautomation.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	
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
		model.addAttribute("hideTopMenu",true);
		
        return "home";
	}
}
