package org.usfirst.frc.team6378.robot;

import org.usfirst.frc.team6378.subsystems.Climber;
import org.usfirst.frc.team6378.utils.Mapping;
import org.usfirst.frc.team6378.utils.Utils;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The main class that controls the robot.
 * 
 * @author FRC 6378
 *
 */
public class Robot extends IterativeRobot {

	final boolean squaredInputs = true;

	RobotDrive m_robot;
	Climber m_climber;

	XboxController m_xBox;
	Joystick m_jStick;

	double maxDriveSpeed = 1;

	/* AUTO MODES */
	String autoSelected;
	final String defaultAuto = "default";
	final String reverseAuto = "reverse";

	public void robotInit() {

		// Camera setup
		CameraServer server = CameraServer.getInstance();
		server.startAutomaticCapture();
		
		// Robot Drive
		m_robot = new RobotDrive(Mapping.fl, Mapping.bl, Mapping.fr, Mapping.br);
		m_robot.setExpiration(0.1);
		m_robot.setSafetyEnabled(true);
		
		// Subsystems
		m_climber = new Climber(Mapping.l_climb, Mapping.r_climb);

		// Controllers
		m_xBox = new XboxController(0);
		//m_jStick = new Joystick(1);

		System.out.println("initialized");
	}

	public void teleopInit() {
		
	}

	public void teleopPeriodic() {

		// Xbox controller with both sticks

		/* CHANGING SPEEDS */
		if (m_xBox.getYButton())
			maxDriveSpeed = 1;
		else if (m_xBox.getXButton())
			maxDriveSpeed = 0.75;

		/* DRIVING */
		double y = m_xBox.getRawAxis(1);
		double x = -m_xBox.getRawAxis(4);
		y = Utils.map(y, -1, 1, -maxDriveSpeed, maxDriveSpeed);
		x = Utils.map(x, -1, 1, -maxDriveSpeed, maxDriveSpeed);

		m_robot.arcadeDrive(y, x, squaredInputs);

		/* CLIMBER */
		double leftTrigger = m_xBox.getRawAxis(Mapping.l_trigger_axis);
		double rightTrigger = m_xBox.getRawAxis(Mapping.r_trigger_axis);

		if (rightTrigger > 0)
			m_climber.climbUp(rightTrigger);
		else if (leftTrigger > 0)
			m_climber.climbDown(leftTrigger);
	}

	public void testInit() {

	}

	public void testPeriodic() {

		/* CHANGING SPEEDS */
		if (m_xBox.getYButton())
			maxDriveSpeed = 1;
		else if (m_xBox.getXButton())
			maxDriveSpeed = 0.75;

		/* DRIVING */
		// // Xbox controller with triggers
		double leftTrigger = m_xBox.getRawAxis(Mapping.l_trigger_axis);
		double rightTrigger = m_xBox.getRawAxis(Mapping.r_trigger_axis);
		
		double y = 0;
		double x = -m_xBox.getRawAxis(4);

		if (rightTrigger > 0)
			y = -rightTrigger;
		else if (leftTrigger > 0)
			y = leftTrigger;
		
		y = Utils.map(y, -1, 1, -maxDriveSpeed, maxDriveSpeed);
		x = Utils.map(x, -1, 1, -maxDriveSpeed, maxDriveSpeed);
		
		m_robot.arcadeDrive(y, x, squaredInputs);
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
	}

	public void autonomousPeriodic() {
		switch (autoSelected) {
		case reverseAuto:
			m_robot.arcadeDrive(0.6, 0);
			break;
		case defaultAuto:
		default:
			m_robot.arcadeDrive(-0.4, 0);
			break;
		}
	}

}
