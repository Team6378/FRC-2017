package org.usfirst.frc.team6378.pid;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class PIDGyro implements PIDSource{

	private PIDSourceType sourceType = PIDSourceType.kRate;
	
	private Gyro gyro;
	private double setPoint = 0.0;
	
	public PIDGyro(Gyro gyro) {
		this.gyro = gyro;
	}
	
	@Override
	public double pidGet() {
		return gyro.getAngle();
	}

	@Override
	public void setPIDSourceType(PIDSourceType sourceType) {
		this.sourceType = sourceType;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return sourceType;
	}
	
	public double getAngle(){
		return gyro.getAngle();
	}
	
	public double getSetpoint(){
		return setPoint;
	}
	
	public void setSetpoint(double setPoint){
		this.setPoint = setPoint;
	}
}
