package org.usfirst.frc.team6378.robot;

import org.usfirst.frc.team6378.subsystems.Winch;
import org.usfirst.frc.team6378.utils.Mapping;
import org.usfirst.frc.team6378.utils.Utils;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The main class that controls the robot.
 * 
 * @author Omar Ashqar
 * @author Prisha Rathi
 *
 */
public class Robot extends IterativeRobot {

	private final double DRIVE_SPEED_FAST = 1;
	private final double DRIVE_SPEED_SLOW = 0.75;
	
	private double m_driveSpeed = 1;
	
	// Robot Drive
	private RobotDrive m_robot;

	// Subsystems
	private Winch m_winch;
	private Gyro gyro;
	private Encoder encoder;

	// Controllers
	private XboxController m_xBox;
	private Joystick m_jStick;
	
	// Misc
	private Timer m_timer;

	/* AUTO MODES */
	private String autoSelected;
	private final String defaultAuto = "default";
	private final String reverseAuto = "reverse";

	private double angleSetPoint = 0.0;

	public void robotInit() {
		
		CameraServer.getInstance().startAutomaticCapture("LYNX Camera", 0);
		
		// Robot Drive
		m_robot = new RobotDrive(Mapping.fl, Mapping.bl, Mapping.fr, Mapping.br);
		m_robot.setExpiration(0.5);
		m_robot.setSafetyEnabled(true);

		// Subsystems
		m_winch = new Winch(Mapping.l_climb, Mapping.r_climb);
		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		encoder = new Encoder(0, 1, false, EncodingType.k4X);

		// Controllers
		m_xBox = new XboxController(0);
		// m_jStick = new Joystick(1);

		// Misc
		m_timer = new Timer();

		System.out.println(">> Robot initialized");
	}

	public void teleopInit() {

	}

	public void teleopPeriodic() {

		// Xbox controller with both sticks

		/* CHANGING SPEEDS */
		if (m_xBox.getYButton())
			m_driveSpeed = 1;
		else if (m_xBox.getXButton())
			m_driveSpeed = 0.75;

		/* DRIVING */
		double y = m_xBox.getRawAxis(Mapping.l_y_axis);
		double x = -m_xBox.getRawAxis(Mapping.r_x_axis);
		y = Utils.map(y, -1, 1, -m_driveSpeed, m_driveSpeed);
		x = Utils.map(x, -1, 1, -m_driveSpeed, m_driveSpeed);

		m_robot.arcadeDrive(y, x, true);

		/* CLIMBER */
		double leftTrigger = m_xBox.getRawAxis(Mapping.l_trigger_axis);
		double rightTrigger = m_xBox.getRawAxis(Mapping.r_trigger_axis);
		
		m_winch.climb(leftTrigger, rightTrigger);
	}

	public void testInit() {
		gyro.reset();
		encoder.reset();
	}

	public void testPeriodic() {

		/* CHANGING SPEEDS */
		if (m_xBox.getYButton())
			m_driveSpeed = DRIVE_SPEED_FAST;
		else if (m_xBox.getXButton())
			m_driveSpeed = DRIVE_SPEED_SLOW;

		/* DRIVING */

		// -y is forward, +y is backward

		// // Xbox controller with triggers
		double leftTrigger = m_xBox.getRawAxis(Mapping.l_trigger_axis);
		double rightTrigger = m_xBox.getRawAxis(Mapping.r_trigger_axis);

		double y = 0;
		double x = -m_xBox.getRawAxis(4);

		if (rightTrigger > 0)
			y = -rightTrigger;
		else if (leftTrigger > 0)
			y = leftTrigger;

		y = Utils.map(y, -1, 1, -m_driveSpeed, m_driveSpeed);
		x = Utils.map(x, -1, 1, -m_driveSpeed, m_driveSpeed);

		double turningValue = (angleSetPoint - gyro.getAngle());

		// Invert the direction of the turn if we are going backwards
		// TODO Might have to change sign on y value
		turningValue = Math.copySign(turningValue, -y);

		if (x == 0)
			m_robot.arcadeDrive(y, turningValue, true);
		else {
			m_robot.arcadeDrive(y, x, true);
			gyro.reset(); // Set the current heading to zero
		}

		SmartDashboard.putNumber("Current angle", gyro.getAngle());
		SmartDashboard.putNumber("Delta from setpoint angle", turningValue);
		SmartDashboard.putNumber("Distance", encoder.getDistance());
	}

	/**
	 * Different auto modes can be selected. This can be chosen in the dashboard
	 * textbox, under Gyro
	 * 
	 * Runs once when Autonomous is enabled. autoSelected is the String entred
	 * from the dashboard, which dictates which auto mode is to be used
	 */
	public void autonomousInit() {
		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);

		m_timer.reset(); // Resets back to zero
		m_timer.start(); // Starts the timer
	}

	public void autonomousPeriodic() {
		switch (autoSelected) {
		case reverseAuto:
			m_robot.arcadeDrive(0.6, 0);
			break;
		case defaultAuto:
		default:
			m_robot.arcadeDrive(-0.6, 0);
			break;
		}
	}

	public void disabledInit() {
	}

	public void disabledPeriodic() {
	}

	public void robotPeriodic() {
	}

}
