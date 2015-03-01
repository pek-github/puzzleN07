package org.puzzlenode.puzzle07;

import java.util.ArrayList;
import java.util.List;

import org.puzzlenode.puzzle07.rentalunits.Period;
import org.puzzlenode.puzzle07.rentalunits.RentalUnit;
import org.puzzlenode.puzzle07.rentalunits.SeasonalRate;

/**
 * It calculates the accommodation cost in the rental units
 * @author pek
 */
class BasicCostCalculator extends CostCalculator {

	private final static double TAX = 0.0411416;	// The tax for this policy 
	
	/**
	 * Calculates and returns the accommodation cost for a given rental unit 
     * and for a given reservation period; inherited from parent-classes
	 * @param rentalUnit The given rental unit
	 * @param period The given reservation period
	 */
	@Override
	public double calculateCost(final RentalUnit rentalUnit, final Period period) {
		final double basicRentalCost = this.calcBaseRentalCost(rentalUnit, period);
		final double rentalCost = basicRentalCost + rentalUnit.getCleaningFee();
		final double totalCost = rentalCost * (1.00 + TAX);
		return totalCost;
	}

	// --- helper methods for the above method ---
	
	private double calcBaseRentalCost(final RentalUnit rentalUnit, final Period period) {
		double cost = 0.0;
		final List<Period> shortPeriods = splitPeriod(period);
		
		for (Period shortPeriod : shortPeriods) {
			for (SeasonalRate seasonalRate : rentalUnit.getSeasonalRates()) {
				final long nights = calculateNights(seasonalRate, shortPeriod);
				cost += nights * seasonalRate.getRate();
			}
		}
		
		return cost;
	}

	private List<Period> splitPeriod (final Period longPeriod) {
		final List<Period> periods = new ArrayList<Period>();
		
		if (longPeriod.spannedYears() == 0) {
			periods.add(longPeriod);
		} else {
			final int fromYear = longPeriod.getFromYear();
			final int toYear = longPeriod.getToYear();
			
			for (int year = fromYear; year <= toYear; year++) {
				if (year == fromYear) {
					periods.add(Period.makePeriodTillEndOfYear(longPeriod.getFrom(), fromYear));
				} else if (year == toYear) {
					periods.add(Period.makePeriodFromStartOfYear(longPeriod.getTo(), toYear));
				} else {
					periods.add(Period.makePeriodForYear(year));
				}
			}
		}
		
		return periods;
	}
}
