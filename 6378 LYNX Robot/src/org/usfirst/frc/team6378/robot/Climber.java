package org.usfirst.frc.team6378.robot;

import edu.wpi.first.wpilibj.VictorSP;

public class Climber {

	private final double maxSpeed = 0.2;
	
	private VictorSP m_leftMotor;
	private VictorSP m_rightMotor;
	
	public Climber() {
		m_leftMotor = new VictorSP(Mapping.l_climb_motor);
		m_rightMotor = new VictorSP(Mapping.r_climb_motor);
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
