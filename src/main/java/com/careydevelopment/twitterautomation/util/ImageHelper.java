package com.careydevelopment.twitterautomation.util;

public class ImageHelper {

	public static String stripParameters(String url) {
		int questionMark = url.indexOf("?");
		if (questionMark > -1) {
			url = url.substring(0, questionMark);
		}
		
		return url;
	}

}
