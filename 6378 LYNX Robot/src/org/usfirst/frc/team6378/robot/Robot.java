package org.usfirst.frc.team6378.robot;

import org.usfirst.frc.team6378.subsystems.DriveTrain;
import org.usfirst.frc.team6378.subsystems.Winch;
import org.usfirst.frc.team6378.utils.Mapping;
import org.usfirst.frc.team6378.utils.Utils;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The main class that controls the robot.
 * 
 * @author Omar Ashqar
 * @author Prisha Rathi
 *
 */
public class Robot extends IterativeRobot {

	private final double DRIVE_SPEED_FAST = 1, DRIVE_SPEED_MEDIUM = 0.75, DRIVE_SPEED_SLOW = 0.5;
	private double m_driveSpeed = DRIVE_SPEED_FAST;

	// Robot Drive
	private DriveTrain m_robot;

	// Subsystems
	private Winch m_winch;

	// Controllers
	private XboxController m_xBox;
	private Joystick m_jStick;

	/* AUTO MODES */
	private String autoSelected;

	public void robotInit() {

		// Camera setup
		CameraServer.getInstance().startAutomaticCapture("LYNX Camera", 0);
		
		// Robot Drive
		m_robot = new DriveTrain(Mapping.fl, Mapping.bl, Mapping.fr, Mapping.br);
		m_robot.setExpiration(0.5);
		m_robot.setSafetyEnabled(true);

		// Subsystems
		m_winch = new Winch(Mapping.l_climb, Mapping.r_climb);

		// Controllers
		m_xBox = new XboxController(0);
		// m_jStick = new Joystick(1);

		System.out.println(">>> Robot initialized");
	}

	public void teleopInit() {
		m_robot.resetEncoder();
	}

	public void teleopPeriodic() {

		/* CHANGING SPEEDS */
		if (m_xBox.getYButton())
			m_driveSpeed = DRIVE_SPEED_FAST;
		else if (m_xBox.getXButton())
			m_driveSpeed = DRIVE_SPEED_MEDIUM;
		else if (m_xBox.getAButton())
			m_driveSpeed = DRIVE_SPEED_SLOW;
		else if (m_xBox.getBButton())
			m_robot.resetEncoder();
		
		if (m_xBox.getRawButton(8))
			m_robot.startTurning180();
		if (m_xBox.getRawButton(5))
			m_robot.stop180();

		/* DRIVING */
		double y = -m_xBox.getRawAxis(Mapping.l_y_axis);
		double x = -m_xBox.getRawAxis(Mapping.r_x_axis);
		y = Utils.map(y, -1, 1, -m_driveSpeed, m_driveSpeed);
		x = Utils.map(x, -1, 1, -m_driveSpeed, m_driveSpeed);

		m_robot.arcadeDrive(y, x, true);

		/* CLIMBER */
		double leftTrigger = m_xBox.getRawAxis(Mapping.l_trigger_axis);
		double rightTrigger = m_xBox.getRawAxis(Mapping.r_trigger_axis);

		m_winch.climb(leftTrigger, rightTrigger);

		m_robot.tick();
	}

	public void testInit() {
		m_robot.resetEncoder();
	}

	public void testPeriodic() {

		/* CHANGING SPEEDS */
		if (m_xBox.getYButton())
			m_driveSpeed = DRIVE_SPEED_FAST;
		else if (m_xBox.getXButton())
			m_driveSpeed = DRIVE_SPEED_MEDIUM;
		else if (m_xBox.getAButton())
			m_robot.resetGyro();

		/* DRIVING */
		double y = -m_xBox.getRawAxis(Mapping.l_y_axis);
		y = Utils.map(y, -1, 1, -m_driveSpeed, m_driveSpeed);

		m_robot.tick();
	}

	public void autonomousInit() {
		autoSelected = SmartDashboard.getString("Auto Selector", "default");
		System.out.println("Auto selected: " + autoSelected);
		
		m_robot.resetEncoder();
	}

	public void autonomousPeriodic() {
			m_robot.driveAuto(autoSelected);
	}

	public void disabledInit() {
	}

	public void disabledPeriodic() {
	}

	public void robotPeriodic() {
	}

}
