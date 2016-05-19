package com.careydevelopment.twitterautomation.jpa.repository;

import java.util.Calendar;
import java.util.Date;

public class CalenderUtil {

	public static Date getXDaysAgo(int x) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, (x * -1));
		
		Date date = cal.getTime();
		
		return date;
	}

}
