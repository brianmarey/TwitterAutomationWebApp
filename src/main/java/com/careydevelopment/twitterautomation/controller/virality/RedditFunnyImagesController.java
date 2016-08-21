package com.careydevelopment.twitterautomation.controller.virality;

import java.util.ArrayList;
import java.util.Date;
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
import com.careydevelopment.twitterautomation.jpa.repository.RedditImageRepository;
import com.careydevelopment.twitterautomation.util.CalenderUtil;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.PaginationHelper;

@Controller
public class RedditFunnyImagesController implements Constants {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedditFunnyImagesController.class);

	@Autowired
	RedditImageRepository redditImageRepository;
	
    @RequestMapping("/redditFunnyImages")
    public String redditFunnyImages(Model model, @RequestParam(value="pageNum", required=false) String pageNum) {    	    
    	model.addAttribute("redditActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("redditFunnyImagesActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("redditArrow", Constants.TWISTIE_OPEN);

    	int page = PaginationHelper.getPage(pageNum);
    	
    	Pageable pageable = new PageRequest(page,RESULTS_PER_PAGE);
    	Page<RedditImage> imageSet = redditImageRepository.findBySubredditOrderByIdDesc("funny",pageable);
    	
    	List<RedditImage> images = new ArrayList<RedditImage>();
    	
    	for (RedditImage image : imageSet) {
    		images.add(image);
    	}

    	PaginationHelper.setPagination(imageSet,model,page);
    	
    	model.addAttribute("images",images);
    	
    	LOGGER.info("There are " + images.size() + " images");
    	
        return "redditFunnyImages";
    }
    
    
}
