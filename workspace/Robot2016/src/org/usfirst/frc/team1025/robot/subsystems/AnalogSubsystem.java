package org.usfirst.frc.team1025.robot.subsystems;

import org.usfirst.frc.team1025.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AnalogSubsystem extends PIDSubsystem {
	
	private Victor Motor;
	private AnalogInput pot;
	public static int BASKET_ARM_UP = 3, BASKET_ARM_DOWN = 0;
    // Initialize your subsystem here
    public AnalogSubsystem() {
    	super("AnalogSubsystem", 1.0, 0, 0);
    	pot = new AnalogInput(RobotMap.AIO__ANALOG__POTENTIOMETER);
    	Motor = new Victor(RobotMap.PWM__TESTMOTOR__1);
    	setAbsoluteTolerance(0.05);
    	getPIDController().setContinuous(false);
    	setSetpoint(BASKET_ARM_UP);
    	enable();
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    public void updateStatus(){
    	SmartDashboard.putNumber("Potentiometer Value", pot.getValue());
    	SmartDashboard.putNumber("Potentiometer volts", pot.getVoltage());
    	SmartDashboard.putNumber("Pot Get Avge Val", pot.getAverageValue());
    }
    public void basketArmUp(){
    	this.setSetpoint(BASKET_ARM_UP);
    }
   
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
    public void basketArmDown(){
    	this.setSetpoint(BASKET_ARM_DOWN);
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
    	return pot.getAverageVoltage();
    }
    
    protected void usePIDOutput(double output) { 				//TODO Is this output returning 0?
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    Motor.set(output);
    
    }
   
}
