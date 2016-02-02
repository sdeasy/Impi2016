package org.usfirst.frc.team1025.robot;


import org.usfirst.frc.team1025.robot.commands.PID_BasketArmUp;
import org.usfirst.frc.team1025.robot.subsystems.*;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.GetImageSizeResult;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class Robot extends IterativeRobot {


	
    public static CameraSubsystem cameraSubsystem;
	public static ChassisSubsystem chassisSubsystem;
	public static AnalogSubsystem analogSubsystem;
    public static OI oi;
    private Command autonomousCommand;
	public SendableChooser autoChooser;
	public SendableChooser autonomousDirectionChooser;
	public static boolean isBasketArmUpCommandRunning = false;
	public static boolean isBasketArmDownCommandRunning = false;


    public void robotInit() {
        cameraSubsystem = new CameraSubsystem();
        chassisSubsystem = new ChassisSubsystem();
        analogSubsystem = new AnalogSubsystem();
       
        oi = new OI();

		autoChooser = new SendableChooser();
        chassisSubsystem.stop();
      
       
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
		autonomousCommand = (Command) autoChooser.getSelected();
		autonomousCommand.start();
    }

    public void autonomousPeriodic() {
    	
        Scheduler.getInstance().run();
        updateStatus();
    }

    public void teleopInit() {
        if (autonomousCommand != null) {
        	autonomousCommand.cancel();
        }
        chassisSubsystem.stop();
       
    }

    public void disabledInit(){
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        updateStatus();
        cameraSubsystem.GetImage();
        //
    }

    public void testPeriodic() {
        LiveWindow.run();
    }

    public void updateStatus() {
        //SmartDashboard.putNumber("Num Totes in Robot",  numTotesInRobot);

    	//Testing Section
    	SmartDashboard.putBoolean("Down Running?", isBasketArmDownCommandRunning);
    	SmartDashboard.putBoolean("Up Running?", isBasketArmUpCommandRunning);
    	SmartDashboard.putNumber("IS GT 0?",Math.abs(Robot.analogSubsystem.getSetpoint()) - Robot.analogSubsystem.getPosition());
    	//SmartDashboard.putNumber("Dpad Direction", oi.driverController.getDpad());
    	//Not-Testing Section
        chassisSubsystem.updateStatus();
        analogSubsystem.updateStatus();
        //cameraSubsystem.updateStatus();
    
    }
    
   
    
   
}
