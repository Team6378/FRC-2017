package org.usfirst.frc.team6378.subsystems;

import org.usfirst.frc.team6378.utils.Utils;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * Controls the winch subsystem that climbs the rope.
 * 
 * @author Omar Ashqar
 *
 */
public class Winch {

	private final double maxSpeed = 0.5;

	private SpeedController m_leftMotor, m_rightMotor;

	/**
	 * Constructs a Winch object that controls the robot's climbing mechanism.
	 * 
	 * @param leftPin
	 *            PWM pin for the left motor
	 * @param rightPin
	 *            PWM pin for the right motor
	 */
	public Winch(int leftPin, int rightPin) {
		m_leftMotor = new VictorSP(leftPin);
		m_rightMotor = new VictorSP(rightPin);
	}

	/**
	 * Drives the winch with the provided values for speed
	 * 
	 * @param leftTrigger
	 *            Range: 0 to 1 of the left trigger axis
	 * @param rightTrigger
	 *            Range: 0 to 1 of the right trigger axis
	 */
	public void climb(double leftTrigger, double rightTrigger) {
		if (rightTrigger > 0)
			setSpeed(rightTrigger);
//		else if (leftTrigger > 0)
//			setSpeed(-leftTrigger);
		else
			stop();
	}

	/**
	 * Drives the winch at the given speed
	 * 
	 * @param speed
	 *            Range: -1 to 1, representing full reverse to full forward
	 *            respectively
	 */
	public void setSpeed(double speed) {
		double mappedSpeed = Utils.map(speed, 0, 1, 0, maxSpeed);
		m_leftMotor.set(mappedSpeed);
		m_rightMotor.set(-mappedSpeed);
	}

	/**
	 * Stops the climbing mechanism
	 */
	public void stop() {
		m_leftMotor.set(0);
		m_rightMotor.set(0);
	}
}
