/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.IOException;
import java.util.Optional;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.util.sendable.SendableBuilder;
import entech.subsystems.EntechSubsystem;
import frc.robot.Robot;
import frc.robot.RobotConstants;
import frc.robot.vision.CameraContainer;

public class VisionSubsystem extends EntechSubsystem {
    private static final boolean ENABLED = true;

    private CameraContainer cameras;

    private Pose3d estimatedPose;

    @Override
    public void initialize() {

        AprilTagFieldLayout photonAprilTagFieldLayout;
        try {
            photonAprilTagFieldLayout = AprilTagFieldLayout
                    .loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
        } catch (IOException e) {
            throw new RuntimeException("Could not load wpilib AprilTagFields");
        }

        if (Robot.isReal() && ENABLED) {
            this.cameras = new CameraContainer(RobotConstants.Vision.Cameras.FRONT_RIGHT,
                    RobotConstants.Vision.Transforms.FRONT_RIGHT, photonAprilTagFieldLayout,
                    null);
        }
    }

    public Optional<Boolean> hasTargets() {
        return ENABLED ? Optional.of(cameras.hasTargets()) : Optional.empty();
    }

    public Optional<Double> getLatency() {
        return ENABLED ? Optional.of(cameras.getLatency()) : Optional.empty();
    }

    public Optional<Integer> getNumberOfTargets() {
        return ENABLED ? Optional.of(cameras.getTargetCount()) : Optional.empty();
    }

    private void updateEstimatedPose() {
        Optional<Pose3d> estPose = cameras.getEstimatedPose();
        estimatedPose = estPose.isPresent() ? estPose.get() : null;
    }

    @Override
    public void periodic() {
        if (ENABLED) {
            updateEstimatedPose();

            Logger logger = Logger.getInstance();
            Optional<Pose3d> pose = getEstimatedPose3d();
            if (pose.isPresent())
                logger.recordOutput("Vision estimated pose", pose.get());
            Optional<Double> lat = getLatency();
            if (lat.isPresent())
                logger.recordOutput("Vision average latency", lat.get());
            Optional<Integer> num = getNumberOfTargets();
            if (num.isPresent())
                logger.recordOutput("Vision number of targets", num.get());
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
                return this.getLatency().get();
            }, null);
            builder.addIntegerProperty("Number of targets", () -> {
                return this.getNumberOfTargets().get();
            }, null);
        }
    }

    public Optional<Pose3d> getEstimatedPose3d() {
        if (estimatedPose == null)
            return Optional.empty();
        return ENABLED ? Optional.of(estimatedPose) : Optional.empty();
    }

    public Optional<Pose2d> getEstimatedPose2d() {
        if (estimatedPose == null)
            return Optional.empty();
        return ENABLED ? Optional.of(estimatedPose.toPose2d()) : Optional.empty();
    }
}