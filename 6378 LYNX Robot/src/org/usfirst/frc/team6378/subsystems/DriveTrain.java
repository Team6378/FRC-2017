package org.usfirst.frc.team6378.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends RobotDrive {

	// Subsystems
	private Gyro gyro;

	private Encoder encoder;

	private double startedTurningAngle;

	private boolean turn180 = false;

	/* AUTO MODES */
	private final String defaultAuto = "default";
	private final String reverseAuto = "reverse";

	public DriveTrain(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);

		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		gyro.calibrate();

		encoder = new Encoder(0, 1, true, EncodingType.k4X);
		encoder.reset();
	}

	public void resetEncoder() {
		encoder.reset();
	}

	public void resetGyro() {
		gyro.reset();
	}

	public void maybe180() {
		if (turn180)
			tankDrive(0.4, 0.4);

		double error = Math.abs(180 - gyro.getAngle());
		if (error < 10)
			turn180 = false;
	}

	public void startTurning180() {
		if (!turn180) {
			turn180 = true;
			gyro.reset();
			startedTurningAngle = gyro.getAngle();
		}
	}

	public void driveAuto(String autoSelected) {
		switch (autoSelected) {
		case reverseAuto:
			// eyyo
			break;
		case defaultAuto:
		default:
			if (Math.abs(encoder.getDistance()) < 1700)
				drive(0.5, 0);
			break;
		}
	}

	public void tick() {
		maybe180();

		SmartDashboard.putString("DB/String 0", "Distance: " + encoder.getDistance());
		SmartDashboard.putString("DB/String 1", "Current angle: " + gyro.getAngle());
	}
}
