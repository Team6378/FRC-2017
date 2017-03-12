package org.usfirst.frc.team6378.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class DriveTrain extends RobotDrive {

	private final double autoSpeed = 0.3;
	private final long sanicSeconds = 10;
	
	// Subsystems
	private Gyro gyro;

	//private Encoder encoder;

//	private double startedTurningAngle;
//	private boolean turn180 = false;

	/* AUTO MODES */
	private final String defaultAuto = "default";
	private final String sanicAuto = "sanic";
	private final String encoderAuto = "encoder";
	
	private long autoStartTime;

	public DriveTrain(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);

		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		gyro.calibrate();

		//encoder = new Encoder(2, 3, true, EncodingType.k4X);
		//encoder.reset();
	}

	public void resetEncoder() {
		//encoder.reset();
	}

	public void resetGyro() {
		gyro.reset();
	}

//	public void stop180() {
//		turn180 = false;
//	}

//	public void maybe180() {
//		if (turn180)
//			setLeftRightMotorOutputs(0.3, -0.3);
//
//		double error = Math.abs(180 - gyro.getAngle());
//		if (error < 20)
//			turn180 = false;
//	}

//	public void startTurning180() {
//		if (!turn180) {
//			turn180 = true;
//			gyro.reset();
//			startedTurningAngle = gyro.getAngle();
//		}
//	}
	
	public void initTimer(){
		autoStartTime = System.currentTimeMillis();
	}

	public void driveAuto(String autoSelected) {
		switch (autoSelected) {
		case sanicAuto:
			if ((System.currentTimeMillis()-autoStartTime)/1000 < sanicSeconds)
				drive(autoSpeed, 0);
			break;
		case encoderAuto:
//			if (Math.abs(encoder.getDistance()) < 1700)
				drive(autoSpeed, 0);
			break;
		case defaultAuto:
		default:
				drive(autoSpeed, 0);
			break;
		}
	}

	public void tick() {
//		maybe180();

		//SmartDashboard.putString("DB/String 0", "Distance: " + encoder.getDistance());
//		SmartDashboard.putString("DB/String 1", "Current angle: " + gyro.getAngle());
	}
}
