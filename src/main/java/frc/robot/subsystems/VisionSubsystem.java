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

import org.littletonrobotics.junction.Logger;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.util.sendable.SendableBuilder;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import frc.robot.vision.CameraContainer;
import frc.robot.vision.VisionDataPacket;

public class VisionSubsystem extends EntechSubsystem {
    private static final boolean ENABLED = true;

    private CameraContainer cameras;

    private Pose3d estimatedPose;
    private double timeStamp = -1;
    private List<PhotonTrackedTarget> targets = new ArrayList<>();

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

            CameraContainer cameraAlpha = new CameraContainer(RobotConstants.Vision.Cameras.FRONT_LEFT, RobotConstants.Vision.Transforms.FRONT_LEFT, photonAprilTagFieldLayout, null);

            this.cameras = new CameraContainer(RobotConstants.Vision.Cameras.FRONT_RIGHT,
                    RobotConstants.Vision.Transforms.FRONT_RIGHT, photonAprilTagFieldLayout,
                    cameraAlpha);
        }
    }

    @Override
    public void periodic() {
        if (ENABLED) {
            Optional<Pose3d> estPose = cameras.getEstimatedPose();
            timeStamp = cameras.getFilteredResult().getLatencyMillis();
            targets = cameras.getFilteredResult().getTargets();
            estimatedPose = estPose.isPresent() ? estPose.get() : null;

            if (estimatedPose != null) {
                Logger.recordOutput("Vision Pose2d", estimatedPose.toPose2d());
                
            }

            Optional<VisionDataPacket> data = getData();
            if (data.isPresent()) {
                VisionDataPacket packet = data.get();
                Logger.recordOutput("Latency", packet.getLatency());
                Logger.recordOutput("Total Targets Counted", packet.getNumberOfTarets());
                Logger.recordOutput("Has Targets", packet.isHasTargets());
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ENABLED;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType(getName());
        if (ENABLED) {
            builder.addDoubleProperty("epX", () -> {
                return estimatedPose.getX();
            }, null);
            builder.addDoubleProperty("epY", () -> {
                return estimatedPose.getY();
            }, null);
            builder.addDoubleProperty("epZ", () -> {
                return estimatedPose.getZ();
            }, null);
            builder.addDoubleProperty("epYaw", () -> {
                return estimatedPose.getRotation().getZ();
            }, null);
            builder.addDoubleProperty("Latency", () -> {
                return this.getData().get().getLatency();
            }, null);
            builder.addIntegerProperty("Number of targets", () -> {
                return this.getData().get().getNumberOfTarets();
            }, null);
        }
    }

    public Optional<Pose2d> getEstimatedPose2d() {
        if (estimatedPose == null)
            return Optional.empty();
        return ENABLED ? Optional.of(estimatedPose.toPose2d()) : Optional.empty();
    }

    public Optional<Double> getTimeStamp() {
        return !ENABLED || timeStamp == -1 ? Optional.of(timeStamp) : Optional.empty();
    }

    public Optional<VisionDataPacket> getData() {
        if (ENABLED) {
            VisionDataPacket dataPacket = new VisionDataPacket();

            dataPacket.setEstimatedPose(getEstimatedPose2d());
            dataPacket.setHasTargets(cameras.hasTargets());
            dataPacket.setLatency(cameras.getLatency());
            dataPacket.setNumberOfTarets(cameras.getTargetCount());
            dataPacket.setTimeStamp(getTimeStamp());
            dataPacket.setTargets(targets);

            return Optional.of(dataPacket);
        }
        return Optional.empty();
    }
}