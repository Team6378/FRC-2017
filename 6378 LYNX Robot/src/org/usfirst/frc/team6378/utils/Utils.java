package org.usfirst.frc.team6378.utils;

/**
 * THis class has multiple useful algorithms.
 * 
 * @author FRC 6378
 *
 */
public class Utils {

	/**
	 * Returns a value that's mapped onto the new range.
	 * @param x Value to map
	 * @param in_min Min from input range
	 * @param in_max Max from input range
	 * @param out_min Min from output range
	 * @param out_max Max from outut range
	 * @return
	 */
	public static double map(double x, double in_min, double in_max, double out_min, double out_max) {
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

}
