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

import com.careydevelopment.twitterautomation.jpa.entity.ChiveImage;
import com.careydevelopment.twitterautomation.jpa.entity.ViralContent;
import com.careydevelopment.twitterautomation.jpa.repository.ViralContentRepository;
import com.careydevelopment.twitterautomation.util.Constants;
import com.careydevelopment.twitterautomation.util.PaginationHelper;

@Controller
public class ViralContentController implements Constants {
	private static final Logger LOGGER = LoggerFactory.getLogger(ViralContentController.class);

	@Autowired
	ViralContentRepository viralContentRepository;
	
    @RequestMapping("/viralContentFromBlogs")
    public String chiveImages(Model model, @RequestParam(value="pageNum", required=false) String pageNum) {    	    
    	model.addAttribute("blogsActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("viralContentFromBlogsActive", Constants.MENU_CATEGORY_OPEN);
    	model.addAttribute("blogsArrow", Constants.TWISTIE_OPEN);

    	int page = PaginationHelper.getPage(pageNum);
    	
    	Pageable pageable = new PageRequest(page,RESULTS_PER_PAGE);
    	
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DAY_OF_MONTH, -1);
    	Page<ViralContent> vs = viralContentRepository.findLatest(cal.getTime(),pageable);
    	
    	List<ViralContent> articles = new ArrayList<ViralContent>();
    	
    	for (ViralContent v : vs) {
    		articles.add(v);
    	}

    	PaginationHelper.setPagination(vs,model,page);
    	
    	model.addAttribute("articles",articles);
    	
        return "viralContentFromBlogs";
    }
    
    
}
