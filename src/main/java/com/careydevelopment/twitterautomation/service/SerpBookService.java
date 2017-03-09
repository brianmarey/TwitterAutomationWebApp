package com.careydevelopment.twitterautomation.service;

import java.util.List;

import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.model.SerpBookKeyword;

public interface SerpBookService {
	
	public void addKeyword(ProjectUrl url, String keyword);
	
	public String fetchCategoryViewKey(ProjectUrl url);
	
	public List<SerpBookKeyword> fetchKeywordsByCategory(ProjectUrl url);
	
	public void deleteCategory(ProjectUrl url);

}
