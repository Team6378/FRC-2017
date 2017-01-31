package org.usfirst.frc.team6378.subsystems;

import org.usfirst.frc.team6378.utils.Utils;

import edu.wpi.first.wpilibj.VictorSP;

public class Climber {

	private final double maxSpeed = 0.5;
	
	private VictorSP m_leftMotor;
	private VictorSP m_rightMotor;
	
	public Climber(int leftPin, int rightPin) {
		m_leftMotor = new VictorSP(leftPin);
		m_rightMotor = new VictorSP(rightPin);
	}
	
	public void climbUp(double speed){
		double mappedSpeed = Utils.map(speed, 0, 1, 0, maxSpeed);
		m_leftMotor.set(mappedSpeed);
		m_rightMotor.set(-mappedSpeed);
	}
	
	public void climbDown(double speed){
		double mappedSpeed = Utils.map(speed, 0, 1, 0, maxSpeed);
		m_leftMotor.set(-mappedSpeed);
		m_rightMotor.set(mappedSpeed);
	}
}
