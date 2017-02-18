package org.usfirst.frc.team6378.robot;

import org.usfirst.frc.team6378.subsystems.DriveTrain;
import org.usfirst.frc.team6378.subsystems.Winch;
import org.usfirst.frc.team6378.utils.Mapping;
import org.usfirst.frc.team6378.utils.Utils;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
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

	double increment = 0.0001;

	private final double DRIVE_SPEED_FAST = 1;
	private final double DRIVE_SPEED_SLOW = 0.75;

	private double m_driveSpeed = 1;

	// Robot Drive
	private DriveTrain m_robot;

	// Subsystems
	private Winch m_winch;

	boolean goddanitthisisabadidea = false;
	
	// Controllers
	private XboxController m_xBox;
	private Joystick m_jStick;

	// Misc
	private Timer m_timer;

	/* AUTO MODES */
	private String autoSelected;
	private final String defaultAuto = "default";
	private final String reverseAuto = "reverse";

	public void robotInit() {

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

		// Misc
		m_timer = new Timer();

		System.out.println(">> Robot initialized");
	}

	public void teleopInit() {
		m_robot.resetEncoder();
	}

	public void teleopPeriodic() {

		// Xbox controller with both sticks

		/* CHANGING SPEEDS */
		if (m_xBox.getYButton())
			m_driveSpeed = DRIVE_SPEED_FAST;
		else if (m_xBox.getXButton())
			m_driveSpeed = DRIVE_SPEED_SLOW;
		else if (m_xBox.getAButton())
			m_driveSpeed = 0.5;
		else if (m_xBox.getBButton())
			m_robot.resetEncoder();
		
		else if (m_xBox.getRawButton(6))
			goddanitthisisabadidea = false;
		else if (m_xBox.getRawButton(5))
			goddanitthisisabadidea = true;

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
			m_driveSpeed = DRIVE_SPEED_SLOW;
		else if (m_xBox.getAButton())
			m_robot.resetGyro();

		/* Tuning PID */
		if (m_jStick.getRawButton(7))
			m_robot.addP(-increment);
		else if (m_jStick.getRawButton(8))
			m_robot.addP(increment);

		else if (m_jStick.getRawButton(9))
			m_robot.addI(-increment);
		else if (m_jStick.getRawButton(10))
			m_robot.addI(increment);

		else if (m_jStick.getRawButton(11))
			m_robot.addD(-increment);
		else if (m_jStick.getRawButton(12))
			m_robot.addD(increment);

		m_robot.setMultiplier(Utils.map(m_jStick.getRawAxis(3), -1, 1, 0, 1));

		/* DRIVING */
		double y = -m_xBox.getRawAxis(Mapping.l_y_axis);
		y = Utils.map(y, -1, 1, -m_driveSpeed, m_driveSpeed);

		m_robot.driveStraight(y);

		m_robot.tick();
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
			m_robot.driveAuto();
			break;
		}
	}

	public void disabledInit() {
	}

	public void disabledPeriodic() {
		if (goddanitthisisabadidea)
			m_winch.climb(0.3, 0.3);
	}

	public void robotPeriodic() {
	}

}
