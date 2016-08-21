package com.careydevelopment.twitterautomation.util;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.careydevelopment.jsonparser.JSONObject;
import com.careydevelopment.propertiessupport.PropertiesFactory;
import com.careydevelopment.propertiessupport.PropertiesFactoryException;
import com.careydevelopment.propertiessupport.PropertiesFile;

public class RecaptchaHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RecaptchaHelper.class);

	public static boolean passedRecaptcha(String response) {
		
		boolean passed = true;
		
		if (Constants.USE_CAPTCHA) {
			try {
				String captchaUrl = getCaptchaUrl(response);
				
				String json = UrlHelper.sendPost(captchaUrl);
			    
			    JSONObject jo = new JSONObject(json);
			    String success = jo.getString("success");
			    
			    if (!"true".equals(success)) {
			    	passed = false;
			    }
			} catch (Exception e) {
				LOGGER.error("Problem retrieving captcha info!",e);
			}
		}
		
		return passed;
	}
	
	
	private static String getCaptchaUrl(String response) throws PropertiesFactoryException {
		StringBuffer sb = new StringBuffer();
		
		Properties props = PropertiesFactory.getProperties(PropertiesFile.GOOGLE_PROPERTIES);
		String recaptchaSecret = props.getProperty("recaptcha.secret");
		
		sb.append("https://www.google.com/recaptcha/api/siteverify?secret=");
		sb.append(recaptchaSecret);
		sb.append("&response=");
		sb.append(response);
		
		return sb.toString();
	}
}
