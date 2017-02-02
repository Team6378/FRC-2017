package org.usfirst.frc.team6378.robot;

import org.usfirst.frc.team6378.subsystems.Climber;
import org.usfirst.frc.team6378.utils.Mapping;
import org.usfirst.frc.team6378.utils.Utils;

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
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";

	public void robotInit() {
		
		m_xBox = new XboxController(0);


		
		System.out.println("initialized");
	}

	public void teleopInit() {
		m_robot = new RobotDrive(Mapping.fl, Mapping.bl, Mapping.fr, Mapping.br);
		m_robot.setExpiration(0.1);
		m_robot.setSafetyEnabled(true);
	}
	
	public void teleopPeriodic() {

		// Xbox controller with both sticks
		// camera

		/* CHANGING SPEEDS */
		if (m_xBox.getYButton())
			maxDriveSpeed = 1;
		else if (m_xBox.getXButton())
			maxDriveSpeed = 0.75;

		/* DRIVING */
		double y = -m_xBox.getRawAxis(1);
		double x = -m_xBox.getRawAxis(4);
		y = Utils.map(y, -1, 1, -maxDriveSpeed, maxDriveSpeed);
		x = Utils.map(x, -1, 1, -maxDriveSpeed, maxDriveSpeed);

		m_robot.arcadeDrive(y, x, squaredInputs);

		/* CLIMBER */
//		double leftTrigger = m_xBox.getRawAxis(Mapping.l_trigger_axis);
//		double rightTrigger = m_xBox.getRawAxis(Mapping.r_trigger_axis);
//
//		if (rightTrigger > 0)
//			m_climber.climbUp(rightTrigger);
//		else if (leftTrigger > 0)
//			m_climber.climbDown(leftTrigger);
	}

	public void testInit() {
		m_climber = new Climber(Mapping.l_climb, Mapping.r_climb);
	}
	
	public void testPeriodic() {

		m_climber.climbUp(1);
		
//		// Xbox controller with triggers
//
//		// BEFORE RUNNING THIS CODE:
//		// Make sure that an un-touched trigger at its default position has an
//		// Axis value of ZERO. Otherwise, the robot will go out of control!
//		double leftTrigger = m_xBox.getTriggerAxis(Hand.kLeft);
//		double rightTrigger = m_xBox.getTriggerAxis(Hand.kRight);
//
//		if (rightTrigger != 0) {
//			m_robot.arcadeDrive(rightTrigger, m_xBox.getRawAxis(4), true);
//		}
//
//		else if (leftTrigger != 0) {
//			m_robot.arcadeDrive(-leftTrigger, m_xBox.getRawAxis(4), true);
//		}
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
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}

}
