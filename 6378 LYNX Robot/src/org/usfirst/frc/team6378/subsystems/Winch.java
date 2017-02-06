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

	public Winch(int leftPin, int rightPin) {
		m_leftMotor = new VictorSP(leftPin);
		m_rightMotor = new VictorSP(rightPin);
	}

	public void climbForward(double speed) {
		double mappedSpeed = Utils.map(speed, 0, 1, 0, maxSpeed);
		m_leftMotor.set(mappedSpeed);
		m_rightMotor.set(-mappedSpeed);
	}

	public void climbReverse(double speed) {
		double mappedSpeed = Utils.map(speed, 0, 1, 0, maxSpeed);
		m_leftMotor.set(-mappedSpeed);
		m_rightMotor.set(mappedSpeed);
	}

	public void stop() {
		m_leftMotor.set(0);
		m_rightMotor.set(0);
	}
}
