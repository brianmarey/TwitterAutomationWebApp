package com.careydevelopment.twitterautomation.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class GetToken {

	public static void main(String[] args) {
		try {
			Twitter twitter = TwitterFactory.getSingleton();
			twitter.setOAuthConsumer("hP24s2NeWKEaWDZhHxsUQ", "ZdwkYshYIUAFi2BYcGMigx6gVqE6mExJWYXkAZrU4");
			RequestToken requestToken = twitter.getOAuthRequestToken("http://atrillionhits.com");
		    AccessToken accessToken = null;
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		    while (null == accessToken) {
		      System.out.println("Open the following URL and grant access to your account:");
		      System.out.println(requestToken.getAuthorizationURL());
		      System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
		      String pin = br.readLine();
		      try{
		         if(pin.length() > 0){
		           accessToken = twitter.getOAuthAccessToken(requestToken, pin);
		         }else{
		           accessToken = twitter.getOAuthAccessToken();
		         }
		      } catch (TwitterException te) {
		        if(401 == te.getStatusCode()){
		          System.out.println("Unable to get the access token.");
		        }else{
		          te.printStackTrace();
		        }
		      }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
