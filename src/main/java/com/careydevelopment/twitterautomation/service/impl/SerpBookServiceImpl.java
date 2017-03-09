package com.careydevelopment.twitterautomation.service.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.careydevelopment.propertiessupport.PropertiesFactory;
import com.careydevelopment.propertiessupport.PropertiesFactoryException;
import com.careydevelopment.propertiessupport.PropertiesFile;
import com.careydevelopment.twitterautomation.jpa.entity.ProjectUrl;
import com.careydevelopment.twitterautomation.model.SerpBookKeyword;
import com.careydevelopment.twitterautomation.service.SerpBookService;
import com.careydevelopment.twitterautomation.util.JsonParser;
import com.careydevelopment.twitterautomation.util.UrlHelper;
import com.sdicons.json.model.JSONArray;
import com.sdicons.json.model.JSONInteger;
import com.sdicons.json.model.JSONObject;
import com.sdicons.json.model.JSONString;
import com.sdicons.json.parser.JSONParser;

@Component
public class SerpBookServiceImpl implements SerpBookService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SerpBookServiceImpl.class);
	
	public static final String RESPONSE_SUCCESS = "SUCCESS";
	
	private String apiKey = "";
	
	public SerpBookServiceImpl() {
		try {
			Properties props = PropertiesFactory.getProperties(PropertiesFile.SERPBOOK_PROPERTIES);
			apiKey = props.getProperty("serpbook.api.key");
		} catch (PropertiesFactoryException pe) {
			LOGGER.error("Problem retrieving properties for SEMRush!",pe);
		}
	}

	
	@Override
	public List<SerpBookKeyword> fetchKeywordsByCategory(ProjectUrl url) {
		List<SerpBookKeyword> keywords = new ArrayList<SerpBookKeyword>();
		
		StringReader reader = null;
		
		try {
			String viewKey = fetchCategoryViewKey(url);
			String restUrl = UrlHelper.encodeUrl(buildFetchKeywordsUrl(viewKey));
			
			System.err.println("resturl is " + restUrl);
			
			String json = UrlHelper.getUrlContents(restUrl);			
			
			//serpbook api just returns an array with no object, so we'll fix that
			json = "{\"result\" : " + json + "}";
			System.err.println("json is " + json);
			reader = new StringReader(json);
			JSONParser parser = new JSONParser(reader);
			JSONObject object = (JSONObject)parser.nextValue();
			System.err.println("object is " + object);
			
			JSONArray list = (JSONArray)object.get("result");
			
			int size = list.size();
			
			for (int i=0;i<size;i++) {
				JSONObject obj = (JSONObject)list.get(i); 
				String category = ((JSONString)obj.get("category")).getValue();
				String kurl = ((JSONString)obj.get("url")).getValue();		
				String keyword = ((JSONString)obj.get("kw")).getValue();		
				String region = ((JSONString)obj.get("region")).getValue();		
				String language = ((JSONString)obj.get("language")).getValue();		
				//String brank = ((JSONString)obj.get("brank")).getValue();		
				//String yrank = ((JSONString)obj.get("yrank")).getValue();	

				SerpBookKeyword key = new SerpBookKeyword();
				
				if (obj.get("start").isInteger()) {
					key.setStart(((JSONInteger)obj.get("start")).getValue().intValue());
				} else {
					String start = ((JSONString)obj.get("start")).getValue();		
					if (start.indexOf("?") == -1) key.setStart(new Integer(start));	
				}
				
				if (obj.get("grank").isInteger()) {
					key.setGrank(((JSONInteger)obj.get("grank")).getValue().intValue());					
				} else {
					String grank = ((JSONString)obj.get("grank")).getValue();		
					if (grank.indexOf("?") == -1) key.setGrank(new Integer(grank));					
				}
				
				key.setCategory(category);
				key.setUrl(kurl);
				key.setKeyword(keyword);
				key.setRegion(region);
				key.setLanguage(language);
				
				//if (brank.indexOf("?") == -1) key.setBrank(new Integer(brank));
				//if (yrank.indexOf("?") == -1) key.setYrank(new Integer(yrank));
				
				keywords.add(key);
			}
		} catch (Exception e) {
			LOGGER.error("Problem retrieving SERP Book info " + url.getDisplayUrl(),e);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		
		return keywords;
	}
	
	
	@Override
	public String fetchCategoryViewKey(ProjectUrl url) {
		String viewKey = "";
		String category = buildCategory(url.getId());

		try {
			String restUrl = buildCategoryViewUrl(category);
			
			System.err.println("\n\nurl is " + restUrl);
			
			JsonParser parser = new JsonParser(restUrl);
			JSONObject object = parser.getJson();
			JSONString responseVal = (JSONString)object.get("RESPONSE");
			System.err.println("responseVal is " + responseVal);
			
			if (!RESPONSE_SUCCESS.equals(responseVal.getValue())) {
				LOGGER.error("Failed response from " + restUrl + " " + object);
			} else {
				JSONString urlVal = (JSONString)object.get(category);
				String fullUrl = urlVal.getValue();
				int equals = fullUrl.indexOf("?");
				if (equals == -1) {
					LOGGER.error("can't parse category view url: " + fullUrl);
				} else {
					viewKey = fullUrl.substring(equals + 1, fullUrl.length());	
				}
			}
		} catch (Exception e) {
			LOGGER.error("Problem retrieving SERP Book info " + url.getDisplayUrl(),e);
		}
		
		System.err.println("returning " + viewKey);
		return viewKey;
	}
	
	
	@Override
	public void deleteCategory(ProjectUrl url) {
		String category = buildCategory(url.getId());
				
		try {
			String restUrl = buildDeleteUrl(category);
			
			System.err.println("\n\nurl is " + restUrl);
			
			JsonParser parser = new JsonParser(restUrl);
			JSONObject object = parser.getJson();
			JSONString responseVal = (JSONString)object.get("RESPONSE");
			System.err.println("responseVal is " + responseVal);
			
			if (!RESPONSE_SUCCESS.equals(responseVal.getValue())) {
				LOGGER.error("Failed response from " + restUrl + " " + object);
			}
		} catch (Exception e) {
			LOGGER.error("Problem retrieving SERP Book info " + url.getDisplayUrl(),e);
		}		
	}
	
	
	@Override
	public void addKeyword(ProjectUrl url, String keyword) {
		String category = buildCategory(url.getId());
		
		System.err.println("category is " + category);
		
		try {
			String restUrl = buildUrl(category, url.getUrl(), keyword);
			
			System.err.println("\n\nurl is " + restUrl);
			
			JsonParser parser = new JsonParser(restUrl);
			JSONObject object = parser.getJson();
			JSONString responseVal = (JSONString)object.get("RESPONSE");
			System.err.println("responseVal is " + responseVal);
			
			if (!RESPONSE_SUCCESS.equals(responseVal.getValue())) {
				LOGGER.error("Failed response from " + restUrl + " " + object);
			}
		} catch (Exception e) {
			LOGGER.error("Problem retrieving SERP Book info " + url.getDisplayUrl(),e);
		}
	}

		
	private String buildCategory(Long id) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("cat");
		builder.append(id);
		
		return builder.toString();
	}
	
	
	private String buildDeleteUrl(String category) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("https://serpbook.com/serp/api/?viewkey=delcategory&auth=");
		builder.append(apiKey);
		builder.append("&category=");
		builder.append(category);
		
		return builder.toString();
	}
	
	
	private String buildUrl(String category, String url, String keyword) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("https://serpbook.com/serp/api/?viewkey=addkeyword&auth=");
		builder.append(apiKey);
		builder.append("&url=");
		builder.append(url);
		builder.append("&kw=");
		builder.append(keyword);
		builder.append("&region=google.com&language=en&category=");
		builder.append(category);
		
		return builder.toString();
	}
	
	
	private String buildCategoryViewUrl(String category) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("https://serpbook.com/serp/api/?viewkey=getsinglecategory&auth=");
		builder.append(apiKey);
		builder.append("&category=");
		builder.append(category);
		
		return builder.toString();
	}
	
	
	private String buildFetchKeywordsUrl(String viewKey) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("https://serpbook.com/serp/api/?");
		builder.append(viewKey);
		
		return builder.toString();
	}
}
