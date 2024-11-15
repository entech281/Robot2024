/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.vision;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.ejml.simple.UnsupportedOperation;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import frc.robot.commands.test.TestVisionCommand;

public class VisionSubsystem extends EntechSubsystem<VisionInput, VisionOutput> {
  private static final boolean ENABLED = false;

  private CameraContainerI cameras;

  private Pose2d estimatedPose;
  private double timeStamp = -1;
  private List<PhotonTrackedTarget> targets = new ArrayList<>();


  @Override
  public void updateInputs(VisionInput input) {
    throw new UnsupportedOperation();
  }

  @Override
  public VisionOutput toOutputs() {
    VisionOutput output = new VisionOutput();

    output.setEstimatedPose(getEstimatedPose());
    output.setHasTargets(!targets.isEmpty());

    output.setNumberOfTargets(targets.size());
    output.setTimeStamp(getTimeStamp());
    output.setTargets(targets);

    if (ENABLED) {
      output.setTargetsData(cameras.getTargetData());
      output.setLatency(cameras.getLatency());
      output.setDriverMode(cameras.isDriverMode());
      output.setConnected(cameras.isConnected());
    } else {
      output.setTargetsData(new ArrayList<>());
      output.setLatency(-1);
      output.setDriverMode(false);
      output.setConnected(true);
    }

    return output;
  }

  @Override
  public void initialize() {
    if (ENABLED) {
      AprilTagFieldLayout photonAprilTagFieldLayout;
      try {
        photonAprilTagFieldLayout =
            AprilTagFieldLayout.loadFromResource(AprilTagFields.k2024Crescendo.m_resourceFile);
      } catch (IOException e) {
        throw new RuntimeException("Could not load wpilib AprilTagFields");
      }

      CameraContainerI cameraLeft = new SoloCameraContainer(RobotConstants.Vision.Cameras.LEFT,
          RobotConstants.Vision.Transforms.LEFT, photonAprilTagFieldLayout);

      CameraContainerI cameraRight = new SoloCameraContainer(RobotConstants.Vision.Cameras.RIGHT,
          RobotConstants.Vision.Transforms.RIGHT, photonAprilTagFieldLayout);

      this.cameras = new MultiCameraContainer(cameraLeft, cameraRight);
    }
  }

  private void updateVisionData() {
    Optional<Pose2d> estPose = cameras.getEstimatedPose();
    PhotonPipelineResult result = cameras.getFilteredResult();
    if (estPose.isPresent()) {
      estimatedPose = estPose.get();
      timeStamp = result.getTimestampSeconds();
    }
    targets = result.getTargets();
  }

  @Override
  public void periodic() {
    if (ENABLED) {
      updateVisionData();
    }
  }

  @Override
  public boolean isEnabled() {
    return ENABLED;
  }

  public Optional<Pose2d> getEstimatedPose() {
    if (estimatedPose == null)
      return Optional.empty();
    return ENABLED ? Optional.of(estimatedPose) : Optional.empty();
  }

  public Optional<Double> getTimeStamp() {
    return ENABLED && timeStamp != -1 ? Optional.of(timeStamp) : Optional.empty();
  }

  @Override
  public Command getTestCommand() {
    return new TestVisionCommand(this);
  }
}
