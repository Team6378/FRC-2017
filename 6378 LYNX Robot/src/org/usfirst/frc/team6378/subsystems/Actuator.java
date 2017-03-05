package org.usfirst.frc.team6378.subsystems;

import edu.wpi.first.wpilibj.Servo;

/**
 * Controls the linear actuator mechanism.
 * 
 * @author Omar Ashqar
 *
 */
public class Actuator extends Servo{
	
	public Actuator(int m_channel) {
		super(m_channel);
	}
	
	public void retract(){
		set(0);
	}
	
	public void extend(){
		set(1);
	}
	
}
