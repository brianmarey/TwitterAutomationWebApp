package com.careydevelopment.twitterautomation.service.impl;

import org.springframework.stereotype.Component;

import com.careydevelopment.twitterautomation.jpa.entity.UrlValue;
import com.careydevelopment.twitterautomation.service.UrlValueService;
import com.careydevelopment.twitterautomation.util.UrlHelper;

@Component
public class UrlValueServiceImpl implements UrlValueService {

	@Override
	public UrlValue getUrlValue(String url) {
		String estibotUrl = getEstibotUrl(url);
		String contents = UrlHelper.getUrlContents(estibotUrl);
		return null;
	}
	
	
	private String getEstibotUrl(String url) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("http://www.estibot.com/appraise.php?a=appraise&data=");
		
		
		return sb.toString();
	}

}
