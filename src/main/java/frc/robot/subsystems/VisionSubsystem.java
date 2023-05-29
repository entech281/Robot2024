/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;

import org.photonvision.EstimatedRobotPose;

import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.apriltag.AprilTagFieldLayout;

import frc.robot.Robot;
import frc.robot.RobotConstants;

import entech.subsystems.EntechSubsystem;
import entech.util.EntechGeometryUtils;

public class VisionSubsystem extends EntechSubsystem {
  private CameraContainer[] cameras;

  private boolean enabled = true;

  private static final double CAMERA_NOT_FOUND = 999;
  private Pose3d estimatedPose;

  @Override
  public void initialize() {

    AprilTagFieldLayout photonAprilTagFieldLayout;
    try {
      photonAprilTagFieldLayout = AprilTagFieldLayout.loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);		
    } catch (IOException e) {
      throw new RuntimeException("Could not load wpilib AprilTagFields");
    }

    if ( Robot.isReal() && enabled ) {
      cameras = new CameraContainer[] {
        new CameraContainer(RobotConstants.Vision.Cameras.FRONT_LEFT, RobotConstants.Vision.Transforms.FRONT_LEFT, photonAprilTagFieldLayout),
        new CameraContainer(RobotConstants.Vision.Cameras.FRONT_RIGHT, RobotConstants.Vision.Transforms.FRONT_RIGHT, photonAprilTagFieldLayout)
      };
    }
  }
  
  public boolean hasTargets() {
    if (enabled) {
      for (CameraContainer cam : cameras) {
        if (cam.getLatestResult().hasTargets()) return true;
      }
    }
    return false;
  }

  public double getLatency() {
    if (enabled) {
      double latencySum = 0;
      for (CameraContainer cam : cameras) {
        latencySum += cam.getLatestResult().getLatencyMillis();
      }
      return latencySum / cameras.length;
    }
    return CAMERA_NOT_FOUND;
  }

  public int getNumberOfTargets() {
    if (enabled) {
      int targetCounter = 0;
      for (CameraContainer cam : cameras) {
        targetCounter += cam.getLatestResult().getTargets().size();
      }
      return targetCounter;
    }
    return (int) CAMERA_NOT_FOUND;
  }

  private void updateEstimatedPose() {
    List<Pose3d> estimates = new ArrayList<Pose3d>();
    for (CameraContainer cam : cameras) {
      Optional<Pose3d> est = cam.getEstimatedPose();
      if (est.isPresent()) {
        estimates.add(est.get());
      }
    }
    switch (estimates.size()) {
      case 0: estimatedPose = null;
      case 1: estimatedPose = estimates.get(0);
      default:
        Pose3d Estimate = null;
        for (Pose3d est : (Pose3d[]) estimates.toArray()) {
          if (Estimate == null) { Estimate = est; continue; }
          Estimate = EntechGeometryUtils.averagePose3d(est, Estimate);
        }
    }
  }
  
  @Override
  public void periodic() {
	  if ( Robot.isReal()) {
		  if (enabled) {
        updateEstimatedPose();
		  }
	  }
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType(getName());
    builder.addDoubleProperty("epX", () -> { return estimatedPose.getX(); }, null);
    builder.addDoubleProperty("epY", () -> { return estimatedPose.getY(); }, null);
    builder.addDoubleProperty("epZ", () -> { return estimatedPose.getZ(); }, null);
    builder.addDoubleProperty("epYaw", () -> { return estimatedPose.getRotation().getZ(); }, null);
    builder.addDoubleProperty("Latency", this::getLatency, null);
    builder.addIntegerProperty("Number of tarets", this::getNumberOfTargets, null);
  }

  public Pose3d getEstimatedPose3d() {
    if (enabled) {
      return estimatedPose;
    }
    return null;
  }

  public Pose2d getEstimatedPose2d() {
    if (enabled) {
      return estimatedPose.toPose2d();
    }
    return null;
  }

  private class CameraContainer {
    private PhotonCamera camera;
    private PhotonPoseEstimator estimator;

    public CameraContainer(String cameraName, Transform3d robotToCamera, AprilTagFieldLayout fieldLayout) {
      camera = new PhotonCamera(cameraName);
      estimator = new PhotonPoseEstimator(fieldLayout, PoseStrategy.MULTI_TAG_PNP, camera, robotToCamera);
      estimator.setMultiTagFallbackStrategy(PoseStrategy.CLOSEST_TO_LAST_POSE);
    }

    public PhotonPipelineResult getLatestResult() {
      return camera.getLatestResult();
    }

    public Optional<Pose3d> getEstimatedPose() {
      Optional<EstimatedRobotPose> estPose = estimator.update();
      if (estPose.isPresent()) {
        return Optional.of(estPose.get().estimatedPose);
      }
      return Optional.empty();
    }
  }
}
