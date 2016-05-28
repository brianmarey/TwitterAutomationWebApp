package com.careydevelopment.twitterautomation.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlHelper.class);
	
	public static String getUrlContents(String url) {
		String urlContents = "";
		BufferedReader in = null;
		
		try {
			URL wrapper = new URL(url);
			StringBuffer imageBuffer = new StringBuffer();
			in = new BufferedReader(new InputStreamReader(wrapper.openStream()));
	
	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	            imageBuffer.append(inputLine);
	        
	        urlContents = imageBuffer.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					//nothing
				}
			}
		}
        
        return urlContents;
	}
}
