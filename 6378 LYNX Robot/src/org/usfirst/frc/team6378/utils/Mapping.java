package org.usfirst.frc.team6378.utils;

/**
 * This class holds the wiring pin numbers for all the electronics and hardware.
 * 
 * @author Omar Ashqar
 *
 */
public final class Mapping {

	/* f: front, b: back, l: left, r: right */

	/* PWM */
	public static final int fl = 0, bl = 1, fr = 2, br = 3;
	public static final int l_climb = 4, r_climb = 5;
	public static final int actuator = 6;

	/* ANALOG INPUT */
	public static final int gyro = 0;
	public static final int encoder_yellow = 2, encoder_blue = 3;

	/* CONTROLLERS */
	public static final int l_y_axis = 1, r_x_axis = 4;
	public static final int l_trigger_axis = 2, r_trigger_axis = 3;
	public static final int l_bumper = 5, r_bumper = 6;

}
