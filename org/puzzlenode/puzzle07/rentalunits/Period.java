package org.puzzlenode.puzzle07.rentalunits;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.puzzlenode.puzzle07.util.DateFactory;

/**
 * The reservation period
 * @author pek
 */
public final class Period {
	private static DateFactory DATE_FACTORY = new DateFactory(); // a DateFactory instance
	private static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd");
								// a suitable Date Formatter instance 
	private final Date from;	// The starting date of the period
	private final Date to;		// The finishing date of the period
	
	/**
	 * Constructor
	 * @param from The starting date of the period
	 * @param to The finishing date of the period
	 */
	private Period(final Date from, final Date to) {
		this.from = from;
		this.to = to;
	}
	
	/**
	 * Factory method idiom to create a Period from a given text file
	 * @param inputFile The filename of the text file to open and parse
	 * @return A new Period instance
	 */
	public static Period newPeriod (final String inputFile) {
		Period period = null;
		
		try (BufferedReader input = new BufferedReader(new FileReader(new File(inputFile)))) {
			String line;
			
			while ((line = input.readLine()) != null) {
				final List<Date> dates = parseLine(line.trim());
				
				if (dates.size() == 2) {
					period = new Period(dates.get(0), dates.get(1));
					break;
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("File " + inputFile + " does not exist");
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println("Cannot read from file: " + inputFile);
			System.err.println(e.getMessage());
		}
		
		if (period == null || !period.isValid()) {
			System.err.println("Could not properly set Period");
		}
		
		return period;
	}

	/**
	 * Helper method for the above factory method; It parses a line of text
	 * and creates and finally returns a list of Dates
	 * @param line A line from the text file
	 * @return A list of Dates
	 */
	private static List<Date> parseLine (final String line) {
		final List<Date> dates = new ArrayList<Date>();
		final String[] possibleDates = line.split("-");
		
		for (String possibleDate : possibleDates) {
			try {
				dates.add(FORMATTER.parse(possibleDate.trim()));
			} catch (ParseException e) {
				System.err.println("Could not parse line: " + line);
			}
		}
		
		return dates;
	}

	/**
	 * Factory method idiom to create a Period starting from a given date 
	 * and finishing on the last day of a given year
	 * @param day The starting date of the period
	 * @param year The year whose last day is the finishing date of the period
	 * @return A new Period instance
	 */
	public static Period makePeriodTillEndOfYear (final Date day, final int year) {
		return new Period(day, DATE_FACTORY.createLastDateOfYear(year));
	}

	/**
	 * Factory method idiom to create a Period starting 
	 * from the first day of a given year and finishing on a given date
	 * @param day The finishing date of the period
	 * @param year The year whose last day is the starting date of the period
	 * @return A new Period instance
	 */
	public static Period makePeriodFromStartOfYear (final Date day, final int year) {
		return new Period(DATE_FACTORY.createFirstDateOfYear(year), day);
	}

	/**
	 * Factory method idiom to create a Period starting 
	 * from the first day of a given year and finishing on 
	 * the last day of that given year
	 * @param year The year whose first and last days are the starting 
	 * and finishing dates of the period, respectively
	 * @return A new Period instance
	 */
	public static Period makePeriodForYear(final int year) {
		return new Period(DATE_FACTORY.createFirstDateOfYear(year),
							DATE_FACTORY.createLastDateOfYear(year));
	}

	/**
	 * It checks the state of an instance
	 * @return It returns \a true if the state if fine;
	 * \a false otherwise
	 */
	public boolean isValid() {
		return (from != null && to != null && from.before(to));
	}
	
	/**
	 * Getter 
	 */
	public Date getFrom() {
		return from;
	}
	
	/**
	 * Getter 
	 */
	public Date getTo() {
		return to;
	}

	/**
	 * Returns the year of the starting date 
	 */
	public int getFromYear() {
		return getYearOfDate(from);
	}

	/**
	 * Returns the year of the finishing date 
	 */	
	public int getToYear() {
		return getYearOfDate(to);
	}
	
	/**
	 * Calculates and returns the amount of years that
	 * the period spans across
	 */
	public int spannedYears() {
		return this.getToYear() - this.getFromYear();
	}

	/**
	 * It returns the year of a given date
	 * (Helper method for methods getFromYear(), getToYear())
	 */
	private static int getYearOfDate (final Date date) {
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * Useful for inspection
	 */	
	@Override
	public String toString() {
		final String f = FORMATTER.format(from);
		final String t = FORMATTER.format(to);
		return String.format("Period is: [%s, %s]", f, t);
	}
	
}
