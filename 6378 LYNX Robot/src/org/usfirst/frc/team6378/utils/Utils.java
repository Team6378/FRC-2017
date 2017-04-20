package org.usfirst.frc.team6378.utils;

/**
 * This class has multiple useful algorithms.
 * 
 * @author Omar Ashqar
 *
 */
public class Utils {

	/**
	 * Returns a value that's mapped onto the new range.
	 * @param x Value to map
	 * @param in_min Min from input range
	 * @param in_max Max from input range
	 * @param out_min Min from output range
	 * @param out_max Max from output range
	 * @return The mapped value
	 */
	public static double map(double x, double in_min, double in_max, double out_min, double out_max) {
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

}
