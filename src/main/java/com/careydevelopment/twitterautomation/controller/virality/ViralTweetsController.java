package com.careydevelopment.twitterautomation.controller.virality;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.careydevelopment.twitterautomation.jpa.entity.RedditImage;
import com.careydevelopment.twitterautomation.jpa.entity.ViralTweet;
import com.careydevelopment.twitterautomation.jpa.repository.ViralTweetRepository;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.PaginationHelper;

@Controller
public class ViralTweetsController implements Constants {
	private static final Logger LOGGER = LoggerFactory.getLogger(ViralTweetsController.class);

	@Autowired
	ViralTweetRepository viralTweetRepository;
	
    @RequestMapping("/viralTweets")
    public String viralTweets(Model model, @RequestParam(value="pageNum", required=false) String pageNum,
    		@RequestParam(value="category", required=true) String category) {    	    
    	model.addAttribute("twitterActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("twitterArrow", Constants.TWISTIE_OPEN);
    	model.addAttribute("category",category);

    	if (category.equals("photo")) {
    		model.addAttribute("twitterViralPhotosActive", Constants.MENU_CATEGORY_OPEN);
    		model.addAttribute("title","Viral Photos From Twitter");
    	} else if (category.equals("funny")) {
    		model.addAttribute("twitterViralFunnyActive", Constants.MENU_CATEGORY_OPEN);
    		model.addAttribute("title","Funny Tweets");
    	} else {
    		model.addAttribute("twitterViralVideosActive", Constants.MENU_CATEGORY_OPEN);
    		model.addAttribute("title","Viral Videos From Twitter");
    	}
    	
    	
    	int page = PaginationHelper.getPage(pageNum);
    	
    	Pageable pageable = new PageRequest(page,RESULTS_PER_PAGE);
    	
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DAY_OF_MONTH, -2);
    	Page<ViralTweet> tweetSet = viralTweetRepository.findLatestByCategory(cal.getTime(), category, pageable);
    	
    	List<ViralTweet> tweets = new ArrayList<ViralTweet>();
    	
    	for (ViralTweet tweet : tweetSet) {
    		tweets.add(tweet);
    	}

    	PaginationHelper.setPagination(tweetSet,model,page);
    	
    	model.addAttribute("tweets",tweets);
    	
    	LOGGER.info("There are " + tweets.size() + " tweets");
    	
        return "viralTweets";
    }
}
