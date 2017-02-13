package org.usfirst.frc.team6378.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends RobotDrive {

	// Subsystems
	private Gyro gyro;

	private double angleSetPoint = 0.0;

	public DriveTrain(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);

		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		gyro.calibrate();
	}

	public void driveStraight(double y) {
		double turningValue = (angleSetPoint - gyro.getAngle());

		// Invert the direction of the turn if we are going backwards
		// TODO Might have to change sign on y value
		turningValue = Math.copySign(turningValue, y);
		drive(y, turningValue* 0.5);

		// else {
		// arcadeDrive(y, x, true);
		// //gyro.reset(); // Set the current heading to zero
		// }
	}

	public void resetGyro() {
		gyro.reset();
	}

	public void tick() {
		SmartDashboard.putString("DB/String 0", "Current angle: " + gyro.getAngle());
		SmartDashboard.putString("DB/String 1", "Delta: " + (angleSetPoint - gyro.getAngle()));
	}

}
