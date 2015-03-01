package org.puzzlenode.puzzle07.rentalunits;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/** 
 * The Rental Units described in the JSON input files
 * @author pek
 */
public final class RentalUnits {
	private final List<RentalUnit> rentalUnits; // a list of Rental Unit instances
	
	/**
	 * Constructor
	 * @param units A list of Rental Unit instances
	 */
	private RentalUnits(final List<RentalUnit> units) {
		rentalUnits = units;
	}
	
	/**
	 * Factory method idiom to create a RentalUnits instance from a given JSON file
	 * @param inputFile The filename of the JSON/text file to open and parse
	 * @return A new RentalUnits instance
	 */	
	public static RentalUnits newRentalUnits(final String inputFile) {
		final List<RentalUnit> units = new ArrayList<RentalUnit>();
		final JSONParser parser = new JSONParser();
		
		try (FileReader input = new FileReader(new File(inputFile))) {
			final JSONArray rentalUnitsData = (JSONArray) parser.parse(input);

			for (Object rentalUnitData : rentalUnitsData) {
				units.add(parseRentalUnit((JSONObject) rentalUnitData));
			}
		} catch (FileNotFoundException e) {
			System.err.println("File " + inputFile + " does not exist");
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println("Cannot read from file: " + inputFile);
			System.err.println(e.getMessage());
		} catch (ParseException e) {
			System.err.println("Cannot parse JSON file: " + inputFile);
			System.err.println(e.getMessage());
		}
		
		return new RentalUnits(units);
	}
	
	/**
	 * A helper method for the above factory method;
	 * It parses a given JSONObject instance and create a RentalUnit instance
	 * @param rentalUnitData The given JSONObject instance
	 * @return A RentalUnit instance
	 */
	private static RentalUnit parseRentalUnit (final JSONObject rentalUnitData) {
		return RentalUnit.newRentalUnit(rentalUnitData);
	}
	
	/**
	 * Returns an Iterable of all Rental Units this RentalUnits instance contains
	 */
	public Iterable<RentalUnit> getRentalUnits() {
		return rentalUnits;
	}

	/**
	 * Useful for inspection
	 */	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		
		for (RentalUnit rentalUnit : rentalUnits) {
			sb.append(rentalUnit);
		}
		
		return sb.toString();
	}
}
