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

import com.careydevelopment.twitterautomation.jpa.entity.ViralFacebook;
import com.careydevelopment.twitterautomation.jpa.entity.ViralTweet;
import com.careydevelopment.twitterautomation.jpa.repository.ViralFacebookRepository;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.PaginationHelper;

@Controller
public class ViralFacebookController implements Constants {
	private static final Logger LOGGER = LoggerFactory.getLogger(ViralFacebookController.class);

	@Autowired
	ViralFacebookRepository viralFacebookRepository;
	
    @RequestMapping("/viralFacebook")
    public String viralTweets(Model model, @RequestParam(value="pageNum", required=false) String pageNum,
    		@RequestParam(value="category", required=true) String category) {    	    
    	model.addAttribute("facebookActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("facebookArrow", Constants.TWISTIE_OPEN);
    	model.addAttribute("category",category);

    	if (category.equals("added_photos")) {
    		model.addAttribute("facebookViralPhotosActive", Constants.MENU_CATEGORY_OPEN);
    		model.addAttribute("title","Viral Photos From Facebook");
    	} else {
    		model.addAttribute("facebookViralVideosActive", Constants.MENU_CATEGORY_OPEN);
    		model.addAttribute("title","Viral Videos From Facebook");
    	}
    	
    	
    	int page = PaginationHelper.getPage(pageNum);
    	
    	Pageable pageable = new PageRequest(page,RESULTS_PER_PAGE);
    	
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DAY_OF_MONTH, -2);
    	Page<ViralFacebook> postSet = viralFacebookRepository.findLatestByCategory(cal.getTime(), category, pageable);
    	
    	List<ViralFacebook> posts = new ArrayList<ViralFacebook>();
    	
    	for (ViralFacebook post : postSet) {
    		posts.add(post);
    	}

    	PaginationHelper.setPagination(postSet,model,page);
    	
    	model.addAttribute("posts",posts);
    	
        return "viralFacebook";
    }
}
