/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.IOException;
import java.util.Optional;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.EstimatedRobotPose;

import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.apriltag.AprilTagFieldLayout;

import frc.robot.Robot;
import frc.robot.RobotConstants;

import entech.subsystems.EntechSubsystem;

public class VisionSubsystem extends EntechSubsystem {

  private PhotonCamera frontLeftCamera;
  private PhotonCamera frontRightCamera;

  private PhotonPoseEstimator frontLeft;
  private PhotonPoseEstimator frontRight;

  private boolean enabled = true;

  private Pose3d POSE_NOT_FOUND = new Pose3d(999, 999, 999, new Rotation3d(999, 999, 999));

  private Pose3d estimatedPose = POSE_NOT_FOUND;

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

      frontLeft = new PhotonPoseEstimator(
        photonAprilTagFieldLayout,
        PoseStrategy.MULTI_TAG_PNP,
        frontLeftCamera,
        RobotConstants.Vision.Transforms.FRONT_LEFT
      );
      frontRight = new PhotonPoseEstimator(
        photonAprilTagFieldLayout, 
        PoseStrategy.MULTI_TAG_PNP, 
        frontRightCamera, 
        RobotConstants.Vision.Transforms.FRONT_RIGHT
      );

      frontLeft.setMultiTagFallbackStrategy(PoseStrategy.CLOSEST_TO_LAST_POSE);
      frontRight.setMultiTagFallbackStrategy(PoseStrategy.CLOSEST_TO_LAST_POSE);
    }
  }
  
  public boolean hasTargets() {
    boolean frontRightHas = frontRightCamera.getLatestResult().hasTargets();
    boolean frontLeftHas = frontLeftCamera.getLatestResult().hasTargets();
	  return frontLeftHas || frontRightHas;
  }

  public double getLatency() {
    double frontRightLatency = frontRightCamera.getLatestResult().getLatencyMillis();
    double frontLeftLatency = frontLeftCamera.getLatestResult().getLatencyMillis();
	  return (frontLeftLatency + frontRightLatency) / 2;
  }

  private Pose3d getEstimatedPose() {
    Optional<EstimatedRobotPose> frontLeftEstimate = frontLeft.update();
    Optional<EstimatedRobotPose> frontRightEstimate = frontRight.update();
    if (frontLeftEstimate.isPresent() && frontRightEstimate.isPresent()) {
      Pose3d frontLeftPose = frontLeftEstimate.get().estimatedPose;
      Pose3d frontRightPose = frontRightEstimate.get().estimatedPose;

      Rotation3d avgRotation = new Rotation3d(
        (frontLeftPose.getRotation().getX() + frontRightPose.getRotation().getX()) / 2,
        (frontLeftPose.getRotation().getY() + frontRightPose.getRotation().getY()) / 2,
        (frontLeftPose.getRotation().getZ() + frontRightPose.getRotation().getZ()) / 2
      );

      return new Pose3d(
        (frontLeftPose.getX() + frontRightPose.getX()) / 2,
        (frontLeftPose.getY() + frontRightPose.getY()) / 2,
        (frontLeftPose.getZ() + frontRightPose.getZ()) / 2,
        avgRotation
      );
    } else {
      if (frontLeftEstimate.isPresent()) {
        return frontLeftEstimate.get().estimatedPose;
      }
      if (frontLeftEstimate.isPresent()) {
        return frontLeftEstimate.get().estimatedPose;
      }
      return POSE_NOT_FOUND;
    }
  }

  public int getNumberOfTargets() {
    int frontLeftTargets = frontLeftCamera.getLatestResult().getTargets().size();
    int frontRightTargets = frontRightCamera.getLatestResult().getTargets().size();
    return frontLeftTargets + frontRightTargets;
  }
  
  @Override
  public void periodic() {
	  if ( Robot.isReal()) {
		  if (enabled) {
        estimatedPose = getEstimatedPose();
		  }		    
	  }
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.addDoubleProperty("epX", () -> { return estimatedPose.getX(); }, null);
    builder.addDoubleProperty("epY", () -> { return estimatedPose.getY(); }, null);
    builder.addDoubleProperty("epZ", () -> { return estimatedPose.getZ(); }, null);
    builder.addDoubleProperty("epYaw", () -> { return estimatedPose.getRotation().getZ(); }, null);
    builder.addDoubleProperty("Latency", this::getLatency, null);
    builder.addIntegerProperty("Number of tarets", this::getNumberOfTargets, null);
  }
}
