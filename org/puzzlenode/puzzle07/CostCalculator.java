package org.puzzlenode.puzzle07;

import java.util.Date;

import org.puzzlenode.puzzle07.rentalunits.Period;
import org.puzzlenode.puzzle07.rentalunits.RentalUnit;
import org.puzzlenode.puzzle07.rentalunits.SeasonalRate;
import org.puzzlenode.puzzle07.util.DateOverlap;
import org.puzzlenode.puzzle07.util.DateFactory;

/**
 * It calculates the accommodation cost in a given rental unit 
 * and for a given reservation period. Since:
 * (a) A calculator could apply very different charging policies
 * (b) All calculators need to calculate the amount of overnights
 * the decision was to go for the abstraction offered by "abstract classes"
 * @author pek
 */
abstract class CostCalculator {
	// A date-factory for the date calculations
	protected static final DateFactory DATE_FACTORY = new DateFactory();

	/**
	 * Calculates and returns the accommodation cost for a given rental unit 
     * and for a given reservation period;
     * It must be implemented by sub-classes
	 * @param rentalUnit The given rental unit
	 * @param period The given reservation period
	 */
	abstract double calculateCost (final RentalUnit rentalUnit, final Period period);
	
	/**
	 * Calculates and returns the amount of overnights for a given seasonal rate
	 * and for a given reservation period
	 * @param seasonalRate The given seasonal rate
	 * @param period The given reservation period
	 */
	protected long calculateNights (final SeasonalRate seasonalRate, final Period period) {
		long nights = 0;
		final Date seasonFirst = DATE_FACTORY.createDateOfYear(seasonalRate.getStart(), 
																period.getFromYear());
		final Date seasonLast = DATE_FACTORY.createDateOfYear(seasonalRate.getEnd(), 
																period.getToYear());
		final Date periodFirst = period.getFrom();
		final Date periodLast = period.getTo();
		
		if (seasonLast.compareTo(seasonFirst) >= 0) {
			final DateOverlap overlap 
				= DateOverlap.newDateOverlap(seasonFirst, seasonLast, periodFirst, periodLast);
			nights = overlap.calculateOverlap(true);
		} else {
			final Date firstOfYear = DATE_FACTORY.createFirstDateOfYear(period.getFromYear());
			final Date lastOfYear = DATE_FACTORY.createLastDateOfYear(period.getToYear());
			final DateOverlap overlapFirst 
				= DateOverlap.newDateOverlap(firstOfYear, seasonLast, periodFirst, periodLast);
			final DateOverlap overlapLast 
				= DateOverlap.newDateOverlap(seasonFirst, lastOfYear, periodFirst, periodLast);
			nights = overlapFirst.calculateOverlap(true) 
						+ overlapLast.calculateOverlap(true);
		}
		
		return nights;
	}
	
}
