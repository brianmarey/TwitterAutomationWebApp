package com.careydevelopment.twitterautomation.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

public class UrlHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlHelper.class);
	
	
	public static String encodeUrl(String url) {
        StringBuilder resultStr = new StringBuilder();
        for (char ch : url.toCharArray()) {
            if (isUnsafe(ch)) {
                resultStr.append('%');
                resultStr.append(toHex(ch / 16));
                resultStr.append(toHex(ch % 16));
            } else {
                resultStr.append(ch);
            }
        }
        return resultStr.toString();
	}
	
	
    private static char toHex(int ch) {
        return (char) (ch < 10 ? '0' + ch : 'A' + ch - 10);
    }

    
    private static boolean isUnsafe(char ch) {
        if (' ' == ch) return true;
        else return false;
    }
	
    
	public static String getFormattedUrl(String url) {
		if (!url.startsWith("http://") && !url.startsWith("https://")) {
			url = "http://" + url;
		}
		
		return url;
	}

	
	public static String getUnformattedUrl(String url) {
		if (url.startsWith("http://")) {
			url = url.substring(7, url.length());
		} else if (url.startsWith("https://")) {
			url = url.substring(8, url.length());
		}
		
		return url;
	}
	
	
	public static String getUrlContents(String url) {
		String urlContents = "";
		BufferedReader in = null;
		
		try {
			URL wrapper = new URL(url);
			StringBuffer imageBuffer = new StringBuffer();
			in = new BufferedReader(new InputStreamReader(wrapper.openStream(),"UTF-8"));
	
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	            imageBuffer.append(inputLine);
	            imageBuffer.append("\n");
	        }
	        
	        
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
		   
		    //reader = new InputStreamReader(is,"ISO-8859-1");
		    reader = new InputStreamReader(is,"UTF-8");
		    //reader = new InputStreamReader(is,"US-ASCII");
		    
		    ins = new InputSource(reader);
		    //ins.setEncoding("ISO-8859-1");
		    ins.setEncoding("UTF-8");
		    //ins.setEncoding("US-ASCII");
		} catch (Exception e) {
			LOGGER.error("Problem reading url " + url,e);
		} finally {
			/*if (is != null) {
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
			}*/
		}
		
		return ins;
	}
	
	
	public static String getRedirectedUrl(String url) {
		InputStream ins = null; 
		String redirectUrl = url;
		
		try {
			URLConnection con = new URL( url ).openConnection();
			con.connect();
						
			try {
				ins = con.getInputStream();
				redirectUrl = con.getURL().toExternalForm();
			} catch (Exception e) {
				//e.printStackTrace();
				if (e.getMessage().indexOf("http") > -1) {
					int start = e.getMessage().indexOf("http");
					redirectUrl = e.getMessage().substring(start,e.getMessage().length()).trim();
				}
			}
			
			redirectUrl = stripParameters(redirectUrl);
			LOGGER.info("Redirected url is " + redirectUrl);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ins != null) {
				try {
					ins.close();					
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		}

		return redirectUrl;
	}
	
	
	public static String stripParameters(String url) {
		int questionMark = url.indexOf("?");
		if (questionMark > -1) {
			url = url.substring(0, questionMark);
		}
		
		return url;
	}
	
	
	public static String getAttributeValueFromFirstElement(String html, String elementName, String attributeName) {
		String imageUrl = "";
		
		if (html != null) {
			int img = html.indexOf("<" + elementName);
			if (img > -1) {
				int src = html.indexOf(attributeName + "=",img);
				if (src > -1) {
					int quote = html.indexOf("\"",src);
					if (quote > -1) {
						int closeQuote = html.indexOf("\"",quote+1);
						if (closeQuote > -1) {
							imageUrl = html.substring(quote + 1, closeQuote);
						}
					}
				}
			}
		}
		
		return imageUrl;
	}
	
	
	public static String sendPost(String postUrl) throws MalformedURLException, IOException {
		OutputStreamWriter out = null;
		InputStream is =null;
		String response = "";
		
		try {
			URL url = new URL(postUrl);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Accept", "application/json");
			out = new OutputStreamWriter(httpCon.getOutputStream());
			
			is = httpCon.getInputStream();
		    BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    
		    String line = null;
		    StringBuffer sb = new StringBuffer();
		    while((line = br.readLine() ) != null) {
		        sb.append(line);
		    }
		    httpCon.disconnect();
		    
		    response = sb.toString();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {}
			}
			
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {}
			}
		}
	    
	    return response;
	}
	
	
	public static boolean isValidUrl(String urlString) {
		boolean isValid = true;
		
		if (urlString == null) {
			isValid = false;
		} else {
			urlString = getFormattedUrl(urlString);
			
			try {
				URL url = new URL(urlString);
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
				//connection.setRequestMethod("GET");
				
				connection.setInstanceFollowRedirects( false );
				connection.connect();
				int responseCode = connection.getResponseCode();

				System.err.println("Response code for " + url + " is " + responseCode);
				
				if (responseCode != 200) {
					isValid = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				isValid = false;
				LOGGER.warn("Bad URL: " + urlString,e);					
			}
		}
		
		return isValid;
	}
	
	
	public static String redirectUrl(String urlString) {
		String redirectUrl = null;
		
		if (urlString != null) {
			urlString = getFormattedUrl(urlString);
			
			try {
				URL url = new URL(urlString);
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
				//connection.setRequestMethod("GET");
				
				connection.setInstanceFollowRedirects( false );
				connection.connect();
				int responseCode = connection.getResponseCode();
				
				if (responseCode == 301 || responseCode == 302) {
					String location = connection.getHeaderField( "Location" ).trim();
					if (location.length() > 3) redirectUrl = location.trim();
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.warn("Bad URL: " + urlString,e);					
			}			
		}
		
		return redirectUrl;
	}
	
	
	public static void main(String[] args) {
		boolean b = isValidUrl("ignitevisibility.com");
		System.err.println(b);
		
		//String redir = redirectUrl(".com");
		//System.err.println(redir);
	}
}
