/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.IOException;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.apriltag.AprilTagFieldLayout;

import frc.robot.Robot;
import frc.robot.RobotConstants;

import entech.subsystems.EntechSubsystem;

public class VisionSubsystem extends EntechSubsystem {

  private PhotonCamera frontLeftCamera;
  private PhotonCamera frontRightCamera;

  private boolean enabled = true;

  @Override
  public void initialize() {

    AprilTagFieldLayout photonAprilTagFieldLayout;
	try {
		photonAprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);		
		
	} catch (IOException e) {
		throw new RuntimeException("Could not load wpilib AprilTagFields");
	}

	if ( Robot.isReal()) {
        frontRightCamera = new PhotonCamera(RobotConstants.Vision.Cameras.FRONT_RIGHT);
        frontLeftCamera = new PhotonCamera(RobotConstants.Vision.Cameras.FRONT_LEFT);
        PhotonPoseEstimator a = new PhotonPoseEstimator(
            photonAprilTagFieldLayout,
            PoseStrategy.MULTI_TAG_PNP,
            frontLeftCamera,
            RobotConstants.Vision.Transforms.FRONT_LEFT
        );
	}
  }
  
//   private boolean hasTargets() {
// 	  return true;
//   }

//   private double getLatency() {
// 	  return currentStatus.getLatency();
//   }
  
  @Override
  public void periodic() {
	  if ( Robot.isReal()) {
		  if (enabled) {
		  }		    
	  }
  }

@Override
public boolean isEnabled() {
	return enabled;
}
}
