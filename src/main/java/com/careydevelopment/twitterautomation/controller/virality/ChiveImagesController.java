package com.careydevelopment.twitterautomation.controller.virality;

import java.util.ArrayList;
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

import com.careydevelopment.twitterautomation.jpa.entity.ChiveImage;
import com.careydevelopment.twitterautomation.jpa.entity.RedditImage;
import com.careydevelopment.twitterautomation.jpa.repository.ChiveImageRepository;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.PaginationHelper;

@Controller
public class ChiveImagesController implements Constants {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChiveImagesController.class);

	@Autowired
	ChiveImageRepository chiveImageRepository;
	
    @RequestMapping("/chiveImages")
    public String chiveImages(Model model, @RequestParam(value="pageNum", required=false) String pageNum) {    	    
    	model.addAttribute("chiveActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("chiveImagesActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("chiveArrow", Constants.TWISTIE_OPEN);

    	int page = PaginationHelper.getPage(pageNum);
    	
    	Pageable pageable = new PageRequest(page,RESULTS_PER_PAGE);
    	Page<ChiveImage> imageSet = chiveImageRepository.findLatest(pageable);
    	
    	List<ChiveImage> images = new ArrayList<ChiveImage>();
    	
    	for (ChiveImage image : imageSet) {
    		images.add(image);
    	}

    	PaginationHelper.setPagination(imageSet,model,page);
    	
    	model.addAttribute("images",images);
    	
        return "chiveImages";
    }
    
    
}
