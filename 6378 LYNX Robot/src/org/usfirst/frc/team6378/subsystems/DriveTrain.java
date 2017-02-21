package org.usfirst.frc.team6378.subsystems;

import org.usfirst.frc.team6378.pid.PIDGyro;
import org.usfirst.frc.team6378.pid.PIDGyroOutput;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends RobotDrive {

	// Subsystems
	private Gyro gyro;
	private PIDGyro pidGyro;
	private PIDGyroOutput pidGyroOutput;
	private PIDController pidGyroController;
	private double kPGyro = 0.0, kIGyro = 0.0, kDGyro = 0.0;

	private Encoder encoder;

	private double multiplier = 1;

	private boolean turn180 = false;
	private double startedTurningAngle = 0.0;

	/* AUTO MODES */
	private final String defaultAuto = "default";
	private final String reverseAuto = "reverse";

	public DriveTrain(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);

		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		gyro.calibrate();

		pidGyro = new PIDGyro(gyro);

		pidGyroOutput = new PIDGyroOutput();

		pidGyroController = new PIDController(kPGyro, kIGyro, kDGyro, pidGyro, pidGyroOutput);

		pidGyroController.setAbsoluteTolerance(5);
		pidGyroController.setInputRange(-180.0, 180.0);
		pidGyroController.setOutputRange(-1.0, 1.0);
		pidGyroController.setSetpoint(0);
		pidGyroController.enable();

		encoder = new Encoder(0, 1, true, EncodingType.k4X);
		encoder.reset();
	}

	public void driveStraight(double y) {
		drive(y, pidGyroOutput.getOutput() * multiplier);
	}

	public void resetEncoder() {
		encoder.reset();
	}

	public void resetGyro() {
		gyro.reset();
		pidGyro.setSetpoint(0);
	}

	public void maybe180() {
		if (turn180)
			drive(0, 0.5);

		double error = Math.abs(180 - gyro.getAngle());
		if (error < 10)
			turn180 = false;

		SmartDashboard.putString("DB/String 1", "turn?: " + turn180);
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

		SmartDashboard.putString("DB/String 2", "Distance: " + encoder.getDistance());
		// SmartDashboard.putString("DB/String 2", "Current angle: " +
		// gyro.getAngle());
		// SmartDashboard.putString("DB/String 3", "Setpoint: " +
		// pidGyro.getSetpoint());
		// SmartDashboard.putString("DB/String 4", "PID Output: " +
		// pidGyroOutput.getOutput());
		//
		// SmartDashboard.putString("DB/String 6", "P: " +
		// pidGyroController.getP());
		// SmartDashboard.putString("DB/String 7", "I: " +
		// pidGyroController.getI());
		// SmartDashboard.putString("DB/String 8", "D: " +
		// pidGyroController.getD());
	}

	public void addP(double x) {
		pidGyroController.setPID(pidGyroController.getP() + x, pidGyroController.getI(), pidGyroController.getD());
	}

	public void addI(double x) {
		pidGyroController.setPID(pidGyroController.getP(), pidGyroController.getI() + x, pidGyroController.getD());
	}

	public void addD(double x) {
		pidGyroController.setPID(pidGyroController.getP(), pidGyroController.getI(), pidGyroController.getD() + x);
	}

	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}

}
