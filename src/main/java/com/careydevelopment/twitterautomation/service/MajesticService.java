package com.careydevelopment.twitterautomation.service;

import com.careydevelopment.twitterautomation.domain.MajesticResults;

public interface MajesticService {

	public MajesticResults getIndexItemInfo(String url);
	
	public MajesticResults getBacklinkData(String url);
}
