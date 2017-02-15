package org.usfirst.frc.team6378.pid;

import edu.wpi.first.wpilibj.PIDOutput;

public class PIDGyroOutput implements PIDOutput{

	private double output;
	
	@Override
	public void pidWrite(double output) {
		this.output = output;
	}
	
	public double getOutput(){
		return output;
	}
}
