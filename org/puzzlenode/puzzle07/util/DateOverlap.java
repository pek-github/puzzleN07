package org.puzzlenode.puzzle07.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * The overlap between two date-ranges, each one defined by its starting and ending dates.
 * An essential class for the accommodation billing computations.
 * @author pek
 */
public final class DateOverlap {
	private final Date range1First; // The first date of the first range
	private final Date range1Last;  // The last date of the first range
	private final Date range2First; // The first date of the second range
	private final Date range2Last;  // The last date of the second range
	
	/**
	 * Constructor
	 * @param r1first The first date of the first range
	 * @param r1last The last date of the first range
	 * @param r2first The first date of the second range
	 * @param r2last The last date of the second range
	 */
	private DateOverlap (Date r1first, Date r1last, Date r2first, Date r2last) {
		range1First = r1first;
		range1Last = r1last;
		range2First = r2first;
		range2Last = r2last;
	}

	/**
	 * Factory method idiom to create a DateOverlap from a given input variables
	 * @param r1first The first date of the first range
	 * @param r1last The last date of the first range
	 * @param r2first The first date of the second range
	 * @param r2last The last date of the second range
	 */	
	public static DateOverlap newDateOverlap (final Date range1First, 
												final Date range1Last, 
												final Date range2First, 
												final Date range2Last) {
		return new DateOverlap(range1First, range1Last,range2First, range2Last);
	}
	
	/**
	 * Calculates and returns the amount of day for which 
	 * the two date-ranges overlap
	 * @param countLastDate It instructs whether or not 
	 * the last overlaping date should be taken into account
	 */
	public long calculateOverlap(final boolean countLastDate) {
		final Date fromDate = maximum(range1First, range2First);
		final Date untilDate = minimum(range1Last, range2Last);
		long overlap = 0L;
		
		if (untilDate.compareTo(fromDate) >= 0) {
			overlap = calcDateDifferenceInDays(fromDate, untilDate);
			
			if (considerLastDate(untilDate, countLastDate)) {
				overlap++;
			}
		}
		
		return overlap;
	}

	// --- helper methods (with minimal calculations) for the above method ---
	
	private Date minimum (final Date date1, final Date date2) {
		return (date1.compareTo(date2) <= 0) ? date1 : date2;
	}

	private Date maximum (final Date date1, final Date date2) {
		return (date1.compareTo(date2) >= 0) ? date1 : date2;
	}	
	
	private long calcDateDifferenceInDays(final Date date1, final Date date2) {
		final long diff = date2.getTime() - date1.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	private boolean considerLastDate (final Date lastDate, 
										final boolean countLastDate) {
		return countLastDate && range1Last.equals(lastDate);
	}
	
}
