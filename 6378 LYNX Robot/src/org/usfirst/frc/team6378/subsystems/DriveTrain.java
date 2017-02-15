package org.usfirst.frc.team6378.subsystems;

import org.usfirst.frc.team6378.pid.PIDGyro;
import org.usfirst.frc.team6378.pid.PIDGyroOutput;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends RobotDrive {

	// Subsystems
	private Gyro gyro;
	private PIDGyro pidGyro;
	private PIDGyroOutput pidGyroOutput;
	private PIDController pidGyroController;
	private double kPGyro = 0.0, kIGyro = 0.0, kDGyro = 0.0;
	
	private double multiplier = 1;

	public DriveTrain(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);

		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		gyro.calibrate();

		pidGyro = new PIDGyro(gyro);
		
		pidGyroOutput = new PIDGyroOutput();

		pidGyroController = new PIDController(kPGyro, kIGyro, kDGyro, pidGyro, pidGyroOutput);
		
		pidGyroController.setSetpoint(0);
		pidGyroController.enable();
	}

	public void driveStraight(double y) {
		drive(y, pidGyroOutput.getOutput() * multiplier);
	}

	public void resetGyro() {
		gyro.reset();
		pidGyro.setSetpoint(0);
	}

	public void tick() {
		SmartDashboard.putString("DB/String 2", "Current angle: " + gyro.getAngle());
		SmartDashboard.putString("DB/String 3", "Setpoint: " + pidGyro.getSetpoint());
		SmartDashboard.putString("DB/String 4", "PID Output: " + pidGyroOutput.getOutput());
		
		SmartDashboard.putString("DB/String 6", "P: " + pidGyroController.getP());
		SmartDashboard.putString("DB/String 7", "I: " + pidGyroController.getI());
		SmartDashboard.putString("DB/String 8", "D: " + pidGyroController.getD());
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
	
	public void setMultiplier(double multiplier){
		this.multiplier = multiplier;
	}

}
