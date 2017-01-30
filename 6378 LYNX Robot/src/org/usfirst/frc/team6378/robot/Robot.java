package org.usfirst.frc.team6378.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.VictorSP;
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
	
	VictorSP vex = new VictorSP(5);
	
	RobotDrive m_robot;
	Climber m_climber;

	XboxController m_xBox;
	Joystick m_jStick;

	double maxDriveSpeed = 1;

	/* AUTO MODES */
	String autoSelected;
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";

	/**
	 * Runs once when the robot is enabled
	 */
	@Override
	public void robotInit() {
//		m_robot = new RobotDrive(0, 1, 2, 3);
//		m_robot.setExpiration(0.1);
//		m_robot.setSafetyEnabled(true);
		
		//m_climber = new Climber();

		// These should match up with the numbers on the DriverStation
		//m_jStick = new Joystick(0);
		m_xBox = new XboxController(0);
	}

	//////////////////////////////////////////////////////

	// Xbox controller with both sticks
	@Override
	public void teleopPeriodic() {

		/* CHANGING SPEEDS */
//		if (m_xBox.getYButton())
//			maxDriveSpeed = 1;
//		else if (m_xBox.getXButton())
//			maxDriveSpeed = 0.75;
//		
//		/* DRIVING */
//		double y = -m_xBox.getRawAxis(1);
//		double x = -m_xBox.getRawAxis(4);
//		y = Utils.map(y, -1, 1, -maxDriveSpeed, maxDriveSpeed);
//		x = Utils.map(x, -1, 1, -maxDriveSpeed, maxDriveSpeed);
//		
//		m_robot.arcadeDrive(y, x, squaredInputs);
		
		/* CLIMBER */
//		double rightTrigger = m_xBox.getRawAxis(Mapping.r_trigger_axis);
//		double leftTrigger = m_xBox.getRawAxis(Mapping.l_trigger_axis);
//		
//		if (rightTrigger > 0)
//			m_climber.climbUp(rightTrigger);
//		else if (leftTrigger > 0)
//			m_climber.climbDown(leftTrigger);
		
		vex.set(0.4);
		
	}

	// Xbox controller with triggers
	// @Override
	// public void teleopPeriodic() {
	// // BEFORE RUNNING THIS CODE:
	// // Make sure that an un-touched trigger at its default position has an
	// // Axis value of ZERO. Otherwise, the robot will go out of control!
	// double leftTrigger = xBox.getTriggerAxis(Hand.kLeft);
	// double rightTrigger = xBox.getTriggerAxis(Hand.kRight);
	//
	// if (rightTrigger != 0) {
	// robot.arcadeDrive(rightTrigger, xBox.getRawAxis(4), true);
	// }
	//
	// else if (leftTrigger != 0) {
	// robot.arcadeDrive(-leftTrigger, xBox.getRawAxis(4), true);
	// }
	// }

	// Joystick
	// @Override
	// public void teleopPeriodic() {
	// robot.arcadeDrive(-jStick.getY(), -jStick.getX(), true);
	// }

	//////////////////////////////////////////////////////
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {

	}

	/**
	 * Different auto modes can be selected. This can be chosen in the dashboard
	 * textbox, under Gyro
	 * 
	 * Runs once when Autonomous is enabled. autoSelected is the String entred
	 * from the dashboard, which dictates which auto mode is to be used
	 */
	@Override
	public void autonomousInit() {
		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * Runs periodically during auto
	 */
	@Override
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
