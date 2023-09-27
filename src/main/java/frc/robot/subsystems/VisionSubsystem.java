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
import frc.robot.Robot;
import frc.robot.RobotConstants;
import frc.robot.vision.CameraContainer;

public class VisionSubsystem extends EntechSubsystem {

    private static final double CAMERA_NOT_FOUND = 999;

    private CameraContainer cameras;

    private Pose3d estimatedPose;

    private boolean enabled = true;

    @Override
    public void initialize() {

        AprilTagFieldLayout photonAprilTagFieldLayout;
        try {
            photonAprilTagFieldLayout = AprilTagFieldLayout
                    .loadFromResource(AprilTagFields.k2023ChargedUp.m_resourceFile);
        } catch (IOException e) {
            throw new RuntimeException("Could not load wpilib AprilTagFields");
        }

        if (Robot.isReal() && enabled) {
            CameraContainer frontLeft = new CameraContainer(RobotConstants.Vision.Cameras.FRONT_LEFT,
                    RobotConstants.Vision.Transforms.FRONT_LEFT, photonAprilTagFieldLayout, null);
            this.cameras = new CameraContainer(RobotConstants.Vision.Cameras.FRONT_RIGHT,
                    RobotConstants.Vision.Transforms.FRONT_RIGHT, photonAprilTagFieldLayout, frontLeft);
        }
    }

    public boolean hasTargets() {
        return enabled ? cameras.hasTargets() : false;
    }

    public double getLatency() {
        return enabled ? cameras.getLatency() : CAMERA_NOT_FOUND;
    }

    public int getNumberOfTargets() {
        return enabled ? cameras.getTargetCount() : (int) CAMERA_NOT_FOUND;
    }

    private void updateEstimatedPose() {
        Optional<Pose3d> estPose = cameras.getEstimatedPose();
        estimatedPose = estPose.isPresent() ? estPose.get() : null;
    }

    @Override
    public void periodic() {
        if (Robot.isReal()) {
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
        builder.addDoubleProperty("Latency", this::getLatency, null);
        builder.addIntegerProperty("Number of tarets", this::getNumberOfTargets, null);
    }

    public Pose3d getEstimatedPose3d() {
        return enabled ? estimatedPose : null;
    }

    public Pose2d getEstimatedPose2d() {
        return enabled ? estimatedPose.toPose2d() : null;
    }
}
