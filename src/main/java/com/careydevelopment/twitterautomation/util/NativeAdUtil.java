package com.careydevelopment.twitterautomation.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import com.careydevelopment.twitterautomation.jpa.entity.DomainAd;
import com.careydevelopment.twitterautomation.jpa.entity.NativeAd;

public class NativeAdUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(NativeAdUtil.class);

	private static final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	
	public static List<NativeAd> getNativeAdsFromDomainAds(Page<DomainAd> ads) {
		List<NativeAd> nads = new ArrayList<NativeAd>();
    	for (DomainAd ad : ads) {
    		NativeAd nad = ad.getNativeAd();
    		Date lastSeen = ad.getLastSeen();
    		String lastSeenStr = df.format(lastSeen);
    		nad.setLastSeenStr(lastSeenStr);
    		
    		nads.add(nad);
    	}
    	
    	return nads;
	}

}
