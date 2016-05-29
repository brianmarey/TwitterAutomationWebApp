package com.careydevelopment.twitterautomation.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

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
	
	
	public static InputSource getInputSourceFromUrl(String url) {
		InputSource ins = new InputSource();
		InputStream is = null;
		Reader reader = null;
		
		try {
			URL urlConn = new URL(url);
			HttpURLConnection httpcon = (HttpURLConnection) urlConn.openConnection();
		    httpcon.addRequestProperty("User-Agent", "Mozilla/4.76");
		    
		    is = httpcon.getInputStream();
		   
		    //Reader reader = new InputStreamReader(is,"ISO-8859-1");
		    //Reader reader = new InputStreamReader(is,"UTF-8");
		    reader = new InputStreamReader(is,"US-ASCII");
		    
		    ins = new InputSource(reader);
		    //ins.setEncoding("ISO-8859-1");
		    //ins.setEncoding("UTF-8");
		    ins.setEncoding("US-ASCII");
		} catch (Exception e) {
			LOGGER.error("Problem reading url " + url,e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					
				}
			}
			
			if (reader != null) {
				try {
					reader.close();
				}catch (Exception e) {
					
				}
			}
		}
		
		return ins;
	}
}
