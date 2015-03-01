package org.puzzlenode.puzzle07;

import org.puzzlenode.puzzle07.rentalunits.Period;
import org.puzzlenode.puzzle07.rentalunits.RentalUnit;
import org.puzzlenode.puzzle07.rentalunits.RentalUnits;

/**
 * A solution for problem 7 (aka 'Blue Hawaii') of 'Puzzle Node'
 * The problem includes also a simpler 'sample version', so
 * they will be both addressed
 * It employs library 'JSON.simple' for parsing JSON files.
 * @author pek
 */
public class Solver {

	// a BasicCostCalculator instance for the calculations
	private final CostCalculator calc = new BasicCostCalculator();
	
	/**
	 * It solves the problem
	 * @param title The title of the problem
	 * @param rentalUnitsInputFileName The filename of the text file containing
	 * the data for the rental units
	 * @param periodInputFileName The filename of the text file containing
	 * the data for the reservation period 
	 */
	private void solve(final String title, 
							final String rentalUnitsInputFileName, 
							final String periodInputFileName) {
		printOpening(title);
		
		final RentalUnits rentalUnits = RentalUnits.newRentalUnits(rentalUnitsInputFileName);
		final Period period = Period.newPeriod(periodInputFileName);
		
		for (RentalUnit rentalUnit : rentalUnits.getRentalUnits()) {
			double cost = calc.calculateCost(rentalUnit, period);
			String msg = String.format("%s: $%.2f", rentalUnit.getName(), cost);
			System.out.println(msg);
		}
		
		printClosing();
	}

	// -- methods for pretty display of solution ---
	
	private void printOpening(final String title) {
		String fullTitle = String.format("Solution for \"%s\" :", title);
		
		printLine();
		System.out.println(fullTitle);
		System.out.println();
	}
	
	private void printClosing() {
		printLine();
		System.out.println();
	}
	
	private void printLine() {
		System.out.println("----------------------------------------");
	}

	
	/**
	 * Application starts here
	 * @param args No arguments needed
	 */	
	public static void main(String[] args) {
		Solver solver = new Solver();
		solver.solve("Sample Problem", 
					"/home/pek/workspace/puzzlenode07/io/sample_vacation_rentals.json",
					"/home/pek/workspace/puzzlenode07/io/sample_input.txt");
		
		solver.solve("Main Problem", 
					"/home/pek/workspace/puzzlenode07/io/vacation_rentals.json",
					"/home/pek/workspace/puzzlenode07/io/input.txt");
	}
	
}
