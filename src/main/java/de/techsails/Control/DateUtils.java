package de.techsails.Control;

import java.util.Date;

public class DateUtils {
	private DateUtils() {}
	
	public static Date addToDate(Date date, int days) {
		return new Date(date.getTime() + days * 24 * 3600 * 1000); // 24 hours * 3600 Seconds * 1000 Milliseconds
	}
}
