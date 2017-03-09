package com.careydevelopment.twitterautomation.util;

public class Pause {

	public static final void sleep(long l) {
		try {
			Thread.sleep(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
