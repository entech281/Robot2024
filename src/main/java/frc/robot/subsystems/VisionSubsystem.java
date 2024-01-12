/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.IOException;
import java.util.Optional;

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
    private static final boolean ENABLED = false;

    private CameraContainer cameras;

    private Pose3d estimatedPose;
    private double timeStamp = -1;

    @Override
    public void initialize() {
        AprilTagFieldLayout photonAprilTagFieldLayout;
        try {
            photonAprilTagFieldLayout = AprilTagFieldLayout
                    .loadFromResource(AprilTagFields.k2024Crescendo.m_resourceFile);
        } catch (IOException e) {
            throw new RuntimeException("Could not load wpilib AprilTagFields");
        }

        if (ENABLED) {
            this.cameras = new CameraContainer(RobotConstants.Vision.Cameras.FRONT_RIGHT,
                    RobotConstants.Vision.Transforms.FRONT_RIGHT, photonAprilTagFieldLayout,
                    null);
        }
    }

    private void updateEstimatedPose() {
        Optional<Pose3d> estPose = cameras.getEstimatedPose();
        timeStamp = cameras.getFilteredResult().getLatencyMillis();
        estimatedPose = estPose.isPresent() ? estPose.get() : null;
    }

    @Override
    public void periodic() {
        if (ENABLED) {
            updateEstimatedPose();

            // Optional<Pose3d> pose = getEstimatedPose3d();
            // if (pose.isPresent())
            // logger.recordOutput("Vision estimated pose", pose.get());
            // Optional<Double> lat = getLatency();
            // if (lat.isPresent())
            // logger.recordOutput("Vision average latency", lat.get());
            // Optional<Integer> num = getNumberOfTargets();
            // if (num.isPresent())
            // logger.recordOutput("Vision number of targets", num.get());
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

            return Optional.of(dataPacket);
        }
        return Optional.empty();
    }
}