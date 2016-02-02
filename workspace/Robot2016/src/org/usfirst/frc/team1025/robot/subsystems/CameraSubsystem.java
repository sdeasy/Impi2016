package org.usfirst.frc.team1025.robot.subsystems;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.GetImageSizeResult;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 *
 */
public class CameraSubsystem extends Subsystem {
	
	CameraServer server;
    Image currentFrame=null;
    int session;
    Image binaryFrame=null;
	USBCamera targetingCamera=null;
	NIVision.Range HUE = new NIVision.Range(63,96);
	NIVision.Range SAT = new NIVision.Range(168, 255);
	NIVision.Range VAL = new NIVision.Range(55, 161);
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public CameraSubsystem() {
	 server = CameraServer.getInstance();
     //server.setQuality(50);
     //the camera name (ex "cam0") can be found through the roborio web interface
     //server.startAutomaticCapture("cam0");
     //targetingCamera= new USBCamera();
 	
 	session=NIVision.IMAQdxOpenCamera("cam0",NIVision.IMAQdxCameraControlMode.CameraControlModeController);
 	NIVision.IMAQdxConfigureGrab(session);
     currentFrame=NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
     binaryFrame=NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
	}
	
	 public void GetImage(){
	    	
	    	try{
	    		NIVision.IMAQdxStartAcquisition(session);
	    		 NIVision.IMAQdxGrab(session, currentFrame, 1);
	    		 NIVision.imaqColorThreshold(binaryFrame, currentFrame, 255, NIVision.ColorMode.HSV, HUE, SAT, VAL);
	             GetImageSizeResult result= NIVision.imaqGetImageSize(currentFrame);
	             SmartDashboard.putNumber("Image Height", result.height);
	            CameraServer.getInstance().setImage(currentFrame);
	            
	            NIVision.IMAQdxStopAcquisition(session);
	           
	           try{ getUpdates();}
	           catch(Exception exx){}//do nothing
	            sendUpdates();
	           
	            Timer.delay(.05);
	    	}
	    	catch(Exception ex){
	    		DriverStation.reportError("PROBLEMO " + ex.getMessage(), true);	
	    	}
	    	
	        
	    }

   public void getStream() {
	   server.setQuality(50);
	   server.startAutomaticCapture("cam0");
   }
	 public void sendUpdates() {
	   SmartDashboard.putNumber("HUE.min", HUE.minValue);
	   SmartDashboard.putNumber("HUE.max", HUE.maxValue);
	   SmartDashboard.putNumber("SAT.min", SAT.minValue);
	   SmartDashboard.putNumber("SAT.max", SAT.maxValue);
	   SmartDashboard.putNumber("VAL.min", VAL.minValue);
	   SmartDashboard.putNumber("VAL.max", VAL.maxValue);
   }
   public void getUpdates() {
	   HUE.minValue=(int) SmartDashboard.getNumber("HUE.min");
	   HUE.maxValue=(int) SmartDashboard.getNumber("HUE.max");
	   SAT.minValue=(int) SmartDashboard.getNumber("SAT.min");
	   SAT.maxValue=(int) SmartDashboard.getNumber("SAT.max");
	   VAL.minValue=(int) SmartDashboard.getNumber("VAL.min");
	   VAL.maxValue=(int) SmartDashboard.getNumber("VAL.max");
	   
   }
	 public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void updateStatus() {
    	
        this.targetingCamera.getImage(currentFrame);
        //server.setImage(currentFrame);
    }
    
}

