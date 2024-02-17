/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.vision;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.littletonrobotics.junction.Logger;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import frc.robot.vision.CameraContainerI;
import frc.robot.vision.MultiCameraContainer;
import frc.robot.vision.SoloCameraContainer;

public class VisionSubsystem extends EntechSubsystem<VisionInput, VisionOutput> {
    private static final boolean ENABLED = true;

    private CameraContainerI cameras;

    private Pose2d estimatedPose;
    private double timeStamp = -1;
    private List<PhotonTrackedTarget> targets = new ArrayList<>();


    @Override
    public void updateInputs(VisionInput input) {

    }

    @Override
    public VisionOutput getOutputs() {
        VisionOutput output = new VisionOutput();

        output.estimatedPose = getEstimatedPose();
        output.hasTargets = !targets.isEmpty();
        output.latency = cameras.getLatency();
        output.numberOfTargets = targets.size();
        output.timeStamp = getTimeStamp();
        output.targets = targets;

        return output;
    }

    @Override
    public void initialize() {
        if (ENABLED) {
            AprilTagFieldLayout photonAprilTagFieldLayout;
            try {
                photonAprilTagFieldLayout = AprilTagFieldLayout
                        .loadFromResource(AprilTagFields.k2024Crescendo.m_resourceFile);
            } catch (IOException e) {
                throw new RuntimeException("Could not load wpilib AprilTagFields");
            }

            CameraContainerI cameraBeta = new SoloCameraContainer(RobotConstants.Vision.Cameras.FRONT_LEFT, RobotConstants.Vision.Transforms.FRONT_LEFT, photonAprilTagFieldLayout);
            CameraContainerI cameraAlpha = new SoloCameraContainer(RobotConstants.Vision.Cameras.FRONT_RIGHT,
                    RobotConstants.Vision.Transforms.FRONT_RIGHT, photonAprilTagFieldLayout);

            this.cameras = new MultiCameraContainer(cameraAlpha, cameraBeta);
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

            VisionOutput data = getOutputs();
            Logger.recordOutput("Latency", data.latency);
            Logger.recordOutput("Total Targets Counted", data.numberOfTargets);
            Logger.recordOutput("Has Targets", data.hasTargets);

            if (data.estimatedPose.isPresent()) {
                Logger.recordOutput("Vision Pose2d", data.estimatedPose.get());
            }
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
}