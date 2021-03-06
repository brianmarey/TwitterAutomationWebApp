package com.careydevelopment.twitterautomation.util;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.jpa.entity.UserConfig;
import com.careydevelopment.twitterautomation.jpa.repository.UserConfigRepository;
import com.careydevelopment.twitterautomation.service.impl.LoginService;

@Component
public class RefreshUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RefreshUtil.class);

	@Autowired
	UserConfigRepository userConfigRepository;
	
	
	public boolean isMaxedOut(UserConfig uc) {
		boolean maxedOut = false;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(uc.getRefreshDate());
		cal.add(Calendar.DAY_OF_MONTH,1);
		
		Date now = new Date();
		
		if (now.after(cal.getTime())) {
			LOGGER.info("User " + uc.getUser().getScreenName() + " has a new batch of refreshes");
			uc.setCurrentRefreshes(1);
			uc.setRefreshDate(now);	
			userConfigRepository.save(uc);
		} else {
			LOGGER.info("User " + uc.getUser().getScreenName() + " current refreshes: " + uc.getCurrentRefreshes() + " Max refreshes " + uc.getMaxRefreshes());
			if (uc.getCurrentRefreshes() >= uc.getMaxRefreshes()) {
				LOGGER.info("User " + uc.getUser().getScreenName() + " has maxed out refreshes at " + uc.getMaxRefreshes());
				maxedOut = true;
			} else {
				LOGGER.info("User " + uc.getUser().getScreenName() + " increases refreshes to " + (uc.getCurrentRefreshes() + 1));
				uc.setCurrentRefreshes(uc.getCurrentRefreshes() + 1);
				userConfigRepository.save(uc);
			}
		}
		
		return maxedOut;
	}
	
	
    public void setEligibleForRefresh(Model model, ProjectUrl projectUrl) {    	
    	Date reportDate = projectUrl.getReportDate();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(reportDate);
    	cal.add(Calendar.DAY_OF_MONTH, 1);
    	
    	Date now = new Date();
    	
    	LOGGER.info("Comparing " + now + " to " + cal.getTime());
    	
    	if (now.after(cal.getTime())) {
    		model.addAttribute("refreshEligible",true);
    	}
    }
    
    
    public boolean isEligibleForRefresh(ProjectUrl projectUrl) {    	
    	Date reportDate = projectUrl.getReportDate();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(reportDate);
    	cal.add(Calendar.DAY_OF_MONTH, 1);
    	
    	Date now = new Date();
    	
    	LOGGER.info("Comparing " + now + " to " + cal.getTime());
    	
    	return (now.after(cal.getTime())); 
    }
}
