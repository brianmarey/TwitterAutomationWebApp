package com.careydevelopment.twitterautomation.util;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FacebookHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(FacebookHelper.class);
	
	
	public static String getImageFromUrl(String url) {
		String imageUrl = "";
		String urlContents = UrlHelper.getUrlContents(url);
		
		int loc = urlContents.indexOf("og:image");
		
		if (loc > -1) {
			int content = urlContents.indexOf("content",loc);

			if (content > -1) {
				int quote = urlContents.indexOf("\"",content);
				
				if (quote > -1) {
					int secondQuote = urlContents.indexOf("\"", quote + 1);
					
					if (secondQuote > -1) {
						imageUrl = urlContents.substring(quote + 1, secondQuote);
					}
				}
			}
			
			if (imageUrl.indexOf("jpg") == -1 && imageUrl.indexOf("gif") == -1 && imageUrl.indexOf("png") == -1 
				&& imageUrl.indexOf("jpeg") == -1) {
				
				//if we get here, they put "content" before "property", let's work backwards
				
				content = urlContents.lastIndexOf("content",loc);

				if (content > -1) {
					int quote = urlContents.indexOf("\"",content);
					
					if (quote > -1) {
						int secondQuote = urlContents.indexOf("\"", quote + 1);
						
						if (secondQuote > -1) {
							imageUrl = urlContents.substring(quote + 1, secondQuote);
						}
					}
				}	
			}
			
			imageUrl = ImageHelper.stripParameters(imageUrl);
		}
		
		return imageUrl;
	}
	
	
	public static int getShares(String url) {
		int shares = 0;
		InputStream ins = null; 
		
		try {
			String redirectUrl = UrlHelper.getRedirectedUrl(url);
			
			String facebookUrl = "https://api.facebook.com/method/links.getStats?urls=";
			facebookUrl += redirectUrl;
			
			LOGGER.info("Faebook url is " + facebookUrl);
			
			URL facebook = new URL(facebookUrl);
			URLConnection connection = facebook.openConnection();

	        Document doc = XmlHelper.parseXML(connection.getInputStream());
	        NodeList descNodes = doc.getElementsByTagName("share_count");
	        Node node = descNodes.item(0);
	        String countS = descNodes.item(0).getFirstChild().getNodeValue();
	        LOGGER.info("share count is " + countS);
		    shares = new Integer(countS); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ins != null) {
				try {
					ins.close();					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return shares;
	}

}
