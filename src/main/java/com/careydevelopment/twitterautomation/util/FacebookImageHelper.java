package com.careydevelopment.twitterautomation.util;

public class FacebookImageHelper {

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

}
