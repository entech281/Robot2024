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
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.EstimatedRobotPose;

import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.apriltag.AprilTagFieldLayout;

import frc.robot.Robot;
import frc.robot.RobotConstants;

import entech.subsystems.EntechSubsystem;
import entech.util.EntechGeometryUtils;

public class VisionSubsystem extends EntechSubsystem {

  private PhotonCamera frontLeftCamera;
  private PhotonCamera frontRightCamera;

  private PhotonPoseEstimator frontLeftEstimator;
  private PhotonPoseEstimator frontRightEstimator;

  private boolean enabled = true;

  private static final Pose3d POSE_NOT_FOUND = new Pose3d(999, 999, 999, new Rotation3d(999, 999, 999));
  private static final double CAMERA_NOT_FOUND = 999;

  private Pose3d estimatedPose = POSE_NOT_FOUND;

  @Override
  public void initialize() {

    AprilTagFieldLayout photonAprilTagFieldLayout;
    try {
      photonAprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);		
    } catch (IOException e) {
      throw new RuntimeException("Could not load wpilib AprilTagFields");
    }

    if ( Robot.isReal() && enabled ) {
      frontRightCamera = new PhotonCamera(RobotConstants.Vision.Cameras.FRONT_RIGHT);
      frontLeftCamera = new PhotonCamera(RobotConstants.Vision.Cameras.FRONT_LEFT);

      frontLeftEstimator = new PhotonPoseEstimator(
        photonAprilTagFieldLayout,
        PoseStrategy.MULTI_TAG_PNP,
        frontLeftCamera,
        RobotConstants.Vision.Transforms.FRONT_LEFT
      );
      frontRightEstimator = new PhotonPoseEstimator(
        photonAprilTagFieldLayout, 
        PoseStrategy.MULTI_TAG_PNP, 
        frontRightCamera, 
        RobotConstants.Vision.Transforms.FRONT_RIGHT
      );

      frontLeftEstimator.setMultiTagFallbackStrategy(PoseStrategy.CLOSEST_TO_LAST_POSE);
      frontRightEstimator.setMultiTagFallbackStrategy(PoseStrategy.CLOSEST_TO_LAST_POSE);
    }
  }

  private PhotonPipelineResult getFrontLeftResult() {
    return frontLeftCamera.getLatestResult();
  }
  
  private PhotonPipelineResult getFrontRightResult() {
    return frontRightCamera.getLatestResult();
  }
  
  public boolean hasTargets() {
    if (enabled) {
      boolean frontLeftHas = getFrontLeftResult().hasTargets();
      boolean frontRightHas = getFrontRightResult().hasTargets();
      return frontLeftHas || frontRightHas;
    }
    return false;
  }

  public double getLatency() {
    if (enabled) {
      double frontLeftLatency = getFrontLeftResult().getLatencyMillis();
      double frontRightLatency = getFrontRightResult().getLatencyMillis();
      return (frontLeftLatency + frontRightLatency) / 2;
    }
    return CAMERA_NOT_FOUND;
  }

  public int getNumberOfTargets() {
    if (enabled) {
      int frontLeftTargets = getFrontLeftResult().getTargets().size();
      int frontRightTargets = getFrontRightResult().getTargets().size();
      return frontLeftTargets + frontRightTargets;
    }
    return (int) CAMERA_NOT_FOUND;
  }

  private Pose3d getEstimatedPose() {
    Optional<EstimatedRobotPose> frontLeftEstimate = frontLeftEstimator.update();
    Optional<EstimatedRobotPose> frontRightEstimate = frontRightEstimator.update();
    if (frontLeftEstimate.isPresent() && frontRightEstimate.isPresent()) {
      Pose3d frontLeftPose = frontLeftEstimate.get().estimatedPose;
      Pose3d frontRightPose = frontRightEstimate.get().estimatedPose;
      return EntechGeometryUtils.averagePose3d(frontLeftPose, frontRightPose);
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
