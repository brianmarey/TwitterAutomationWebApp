package com.careydevelopment.twitterautomation.util;

public class ImageHelper {

	public static String stripParameters(String url) {
		return UrlHelper.stripParameters(url);
	}

	public static String getImageUrlFromRawHtml(String html) {
		return UrlHelper.getAttributeValueFromFirstElement(html, "img", "src");
	}
}
