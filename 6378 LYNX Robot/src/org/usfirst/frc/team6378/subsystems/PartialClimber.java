package org.usfirst.frc.team6378.subsystems;

import org.usfirst.frc.team6378.utils.Utils;

import edu.wpi.first.wpilibj.VictorSP;

public class PartialClimber {

	private final double maxSpeed = 0.5;
	
	private VictorSP m_motor;
	
	public PartialClimber(int pin) {
		m_motor = new VictorSP(pin);
	}
	
	public void climbUp(double speed){
		double mappedSpeed = Utils.map(speed, 0, 1, 0, maxSpeed);
		m_motor.set(mappedSpeed);
	}
	
	public void climbDown(double speed){
		double mappedSpeed = Utils.map(speed, 0, 1, 0, maxSpeed);
		m_motor.set(-mappedSpeed);
	}
}
