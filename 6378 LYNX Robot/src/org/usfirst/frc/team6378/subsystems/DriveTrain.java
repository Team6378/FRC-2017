package org.usfirst.frc.team6378.subsystems;

import org.usfirst.frc.team6378.utils.Mapping;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Controls the robot drivetrain.
 * 
 * @author Omar Ashqar
 *
 */
public class DriveTrain extends RobotDrive {

	private final double AUTO_STRAIGHT_SPEED = 0.3;
	private final double AUTO_TURN_SPEED = 0.3;
	private final long sanicSeconds = 10;

	// Subsystems
	private Gyro gyro;
	public Encoder encoder;

	/* AUTO */
	private final String timeAuto = "time";
	private final String encoderAuto = "encoder";
	private final String middleAuto = "middle";

	private final String rightSliderAuto = "rightslider";
	private final String leftSliderAuto = "leftslider";

	private final String lbAuto = "lb";
	private final String rbAuto = "rb";
	private final String lrAuto = "lr";
	private final String rrAuto = "rr";

	private boolean reached = false;

	private long autoStartTime;

	/* AUTO CONSTANTS */
	private final double OFFSET_TIME = 1.5, OFFSET = 0.00001;

	// ???
	private long currTime;
	private double currAngle, currDistance, slider0, slider1;

	public DriveTrain(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);

		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		gyro.calibrate();

		encoder = new Encoder(Mapping.encoder_yellow, Mapping.encoder_blue, true, EncodingType.k4X);
		encoder.setDistancePerPulse(Math.PI * 6 / 1440);
		encoder.reset();
	}

	public void resetEncoder() {
		encoder.reset();
	}

	public void resetGyro() {
		gyro.reset();
	}

	public void initTimer() {
		autoStartTime = System.currentTimeMillis();
		reached = false;
	}

	public void leftAuto(double distanceSet, double angleSet) {
		if (currTime < OFFSET_TIME) // Straight
			drive(AUTO_STRAIGHT_SPEED, OFFSET);
		else if (currTime < distanceSet && !reached) // Straight
			drive(AUTO_STRAIGHT_SPEED, 0);
		else if (currAngle < angleSet) { // Turn
			reached = true;
			drive(AUTO_TURN_SPEED, 1);
		} else if (currDistance < 200) // Straight
			drive(AUTO_STRAIGHT_SPEED, 0);
	}

	public void rightAuto(double distanceSet, double angleSet) {
		if (currTime < OFFSET_TIME) // Straight
			drive(AUTO_STRAIGHT_SPEED, OFFSET);
		else if (currTime < distanceSet && !reached) // Straight
			drive(AUTO_STRAIGHT_SPEED, 0);
		else if (currAngle < angleSet) { // Turn
			reached = true;
			drive(AUTO_TURN_SPEED, -1);
		} else if (currDistance < 200) // Straight
			drive(AUTO_STRAIGHT_SPEED, 0);
	}

	public void driveAuto(String autoSelected) {

		currTime = (System.currentTimeMillis() - autoStartTime) / 1000;
		currAngle = Math.abs(gyro.getAngle());
		currDistance = Math.abs(encoder.getDistance());

		slider0 = SmartDashboard.getDouble("DB/Slider 0");
		slider1 = SmartDashboard.getDouble("DB/Slider 1");

		switch (autoSelected) {

		case leftSliderAuto:
			leftAuto(slider0, slider1);
			break;

		case rightSliderAuto:
			rightAuto(slider0, slider1);
			break;

		case lbAuto:
			leftAuto(slider0, slider1);
			break;

		case rbAuto:
			rightAuto(slider0, slider1);
			break;

		case lrAuto:
			leftAuto(slider0, slider1);
			break;

		case rrAuto:
			rightAuto(slider0, slider1);
			break;

		case timeAuto:
			if (currTime < sanicSeconds)
				drive(AUTO_STRAIGHT_SPEED, 0);
			break;

		case encoderAuto:
			if (currDistance < 200)
				drive(AUTO_STRAIGHT_SPEED, 0);
			break;

		case middleAuto:
		default:
			if (currTime < OFFSET_TIME)
				drive(AUTO_STRAIGHT_SPEED, OFFSET);
			else if (currTime < 5)
				drive(AUTO_STRAIGHT_SPEED, 0);
			break;

		} // End of Switch

		tick();
	}

	public void tick() {
		SmartDashboard.putString("DB/String 0", "Distance: " + encoder.getDistance());
		SmartDashboard.putString("DB/String 1", "Current angle: " + gyro.getAngle());
	}
}
