package org.puzzlenode.puzzle07.rentalunits;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/** 
 * The Rental Unit described in the JSON input files
 * @author pek
 */

public final class RentalUnit {
	private final String name;					// The name of the rental unit
	private final double cleaningFee;			// The cleaning fee of the rental unit
	private final List<SeasonalRate> seasonalRates; // The seasonal rates of the unit

	/**
	 * Constructor
	 * @param name The name of the rental unit
	 * @param fee The cleaning fee of the rental unit
	 * @param seasonalRates The seasonal rates of the rental unit
	 */
	private RentalUnit (final String name, 
						final double fee, 
						final List<SeasonalRate> seasonalRates) {
		this.name = name;
		this.cleaningFee = fee;
		this.seasonalRates = seasonalRates;
	}
	
	/**
	 * Factory method idiom to create a RentalUnit from a given JSONObject instance
	 * @param rentalUnitData The given JSONObject instance to parse
	 * @return A new RentalUnit instance
	 */	
	public static RentalUnit newRentalUnit (final JSONObject rentalUnitData) {
		final String nameData = (String) rentalUnitData.get("name");
		final List<SeasonalRate> seasonalRates = parseSeasonalRates(rentalUnitData);
		final double fee = parseCleaningFee(rentalUnitData);
		return new RentalUnit(nameData, fee, seasonalRates);
	}

	/**
	 * Helper method for above factory method: newRentalUnit(); 
	 * It parses data related to the seasonal rates
	 * @return A list of SeasonalRate objects
	 */
	private static List<SeasonalRate> parseSeasonalRates (final JSONObject rentalUnitData) {
		final List<SeasonalRate> seasonalRates = new ArrayList<SeasonalRate>();
		
		if (rentalUnitData.get("seasons") != null) {
			for (Object seasonalRatesData : (JSONArray) rentalUnitData.get("seasons")) {
				for (Object seasonalRateData : ((JSONObject) seasonalRatesData).values()) {
					seasonalRates.add(
						SeasonalRate.newSeasonalRate((JSONObject) seasonalRateData));
				}
			}
		}
		else {
			seasonalRates.add(
				SeasonalRate.newSeasonalRate((String) rentalUnitData.get("rate")));
		}
		
		return seasonalRates;
	}

	/**
	 * Helper method for above factory method: newRentalUnit(); 
	 * It parses data related to the rental unit
	 * @return The cleaning fee of the rental unit
	 */	
	private static double parseCleaningFee (JSONObject rentalUnitData) {
		final String cleaningFeeData = (String) rentalUnitData.get("cleaning fee");
		
		if (cleaningFeeData == null
			|| cleaningFeeData.trim().isEmpty()) {
			return 0.0;
		}
		
		final String feeData = cleaningFeeData.trim().replace("$", "");
		return Double.parseDouble(feeData);
	}
	
	// --- getters ---
	
	public String getName() {
		return name;
	}
	
	public double getCleaningFee() {
		return cleaningFee;
	}
	
	public List<SeasonalRate> getSeasonalRates() {
		return seasonalRates;
	}

	/**
	 * Useful for inspection
	 */	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("-------").append("\n");
		sb.append("Rental Unit: \"")
			.append(name)
			.append("\"")
			.append("\n");
		sb.append("  ")
			.append("Cleaning Fee is $")
			.append(String.format("%.2f", cleaningFee))
			.append("\n");
		sb.append("  ")
			.append("Seasonal Rates are")
			.append("\n");
		
		for (SeasonalRate sr : seasonalRates) {
			sb.append("    ")
				.append(sr)
				.append("\n");
		}
		
		sb.append("-------").append("\n");
		return sb.toString();		
	}
}
