package org.puzzlenode.puzzle07.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Acts as a factory for custom creation of instances of the Date class 
 * @author pek
 */
public final class DateFactory {
	
	/**
	 * Creates a date according to the properties of a given Date
	 * and essentially according to year-value pf the given year parameter 
	 * @param date The given date
	 * @param year The given year
	 * @return A new Date instance
	 */
	public Date createDateOfYear(Date date, int year) {
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, year);
		return calendar.getTime();
	}
	
	/**
	 * Creates a date for the first day of the given year 
	 * @param year The given year
	 * @return A new Date instance
	 */
	public Date createFirstDateOfYear(int year) {
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(year, Calendar.JANUARY, 1);
		return calendar.getTime();
	}

	/**
	 * Creates a date for the last day of the given year 
	 * @param year The given year
	 * @return A new Date instance
	 */	
	public Date createLastDateOfYear(int year) {
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(year, Calendar.DECEMBER, 31);
		return calendar.getTime();
	}
	
}
