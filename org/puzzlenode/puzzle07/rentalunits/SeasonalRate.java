package org.puzzlenode.puzzle07.rentalunits;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;

/** 
 * The seasonal rate described in the JSON input files
 * @author pek
 */

public final class SeasonalRate {
	// a suitable Date Formatter instance
	private static SimpleDateFormat FORMATTER = new SimpleDateFormat("MM-dd");
	// cached values for the first and last days in the seasonal rate format
	private static Date FIRST_DAY_OF_YEAR = null; 
	private static Date LAST_DAY_OF_YEAR = null;
	
	private final Date start;  // The starting date of the seasonal rate
	private final Date end;    // The ending date of the seasonal rate
	private final double rate; // The rate of the seasonal rate
	
	/**
	 * Constructor
	 * @param start The starting date of the seasonal rate
	 * @param end The ending date of the seasonal rate
	 * @param rate The rate of the seasonal rate
	 */
	private SeasonalRate (final Date start, final Date end, final double rate) {
		this.start = start;
		this.end = end;
		this.rate = rate;
	}
	
	/**
	 * Constructor; the starting and ending dates are 
	 * the first and last days in the required format
	 * @param rate The rate of the seasonal rate
	 */
	private SeasonalRate (final double rate) {
		this (getFirstDayOfYear(), getLastDayOfYear(), rate);
	}
	
	/**
	 * Accesses, caches and returns the first day of the year 
	 * in the format used by the SeasonalRate
	 */	
	private static Date getFirstDayOfYear() {
		if (FIRST_DAY_OF_YEAR == null) {
			FIRST_DAY_OF_YEAR = parseDate("01-01");
		}
		
		return FIRST_DAY_OF_YEAR;
	}

	/**
	 * Accesses, caches and returns the first day of the year 
	 * in the format used by the SeasonalRate
	 */		
	private static Date getLastDayOfYear() {
		if (LAST_DAY_OF_YEAR == null) {
			LAST_DAY_OF_YEAR = parseDate("12-31");
		}
		
		return LAST_DAY_OF_YEAR;
	}

	/**
	 * Factory method idiom to create a SeasonalRate 
	 * from a given text chunk of a JSON file
	 * @param rateData The text chunk of the JSON file to parse
	 * @return A new SeasonalRate instance
	 */	
	static SeasonalRate newSeasonalRate (final String rateData) {
		return new SeasonalRate(parseRate(rateData));
	}	

	/**
	 * Factory method idiom to create a SeasonalRate 
	 * from a given JSONObject from a JSON file
	 * @param seasonalRateData The JSONObject from a JSON file
	 * @return A new SeasonalRate instance
	 */		
	static SeasonalRate newSeasonalRate (final JSONObject seasonalRateData) {
		final Date s = parseDate((String) seasonalRateData.get("start"));
		final Date e = parseDate((String) seasonalRateData.get("end"));
		final double r = parseRate((String) seasonalRateData.get("rate"));
		return new SeasonalRate(s, e, r);
	}
	
	/**
	 * Helper method for above factory methods; It parses a piece of text
	 * to a Date object
	 * @param possibleDate A piece of text presumably describing a Date
	 * in the appropriate SeasonalRate format
	 * @return A new Date instance
	 */
	private static Date parseDate (final String possibleDate) {
		Date date = null;
		
		try {
			date = FORMATTER.parse(possibleDate);
		} catch (ParseException e) {
			System.err.println("Could not parse Date: " + possibleDate);
			System.err.println(e.getMessage());
		}
		
		return date;
	}

	/**
	 * Helper method for above factory methods; It parses a piece of text to a rate
	 * @param possibleDate A piece of text presumably describing a rate
	 * @return A variable containing a rate
	 */	
	private static double parseRate (final String possibleRate) {
		return Double.parseDouble(possibleRate.trim().replace("$", ""));
	}

	// --- getters ---
	
	public Date getStart() {
		return start;
	}
	
	public Date getEnd() {
		return end;
	}
	
	public double getRate() {
		return rate;
	}
	
	/**
	 * Useful for inspection
	 */		
	@Override
	public String toString() {
		final String s = FORMATTER.format(start);
		final String e = FORMATTER.format(end);
		return String.format("In [%s -- %s], rate is: $%.2f", s, e, rate);
	}
}
