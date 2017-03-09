package com.careydevelopment.twitterautomation.util;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sdicons.json.model.JSONObject;
import com.sdicons.json.parser.JSONParser;

public class JsonParser {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonParser.class);
	
	private JSONObject json;
	
	public JsonParser(String url) {
		try {
			String formattedUrl = UrlHelper.encodeUrl(url);
			
			URL jsonUrl = new URL(formattedUrl);
			
			JSONParser parser = new JSONParser(jsonUrl.openStream());
			
			json = (JSONObject)parser.nextValue();
		} catch (Exception e) {
			LOGGER.error("Problem parsing JSON: " + e.getMessage());
		}
	}
	
	public JSONObject getJson() {
		return json;
	}
	
}
