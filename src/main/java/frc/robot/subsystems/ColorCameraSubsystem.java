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

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.util.sendable.SendableBuilder;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import frc.robot.vision.CameraContainer;
import frc.robot.vision.VisionDataPacket;

public class ColorCameraSubsystem extends EntechSubsystem {
    private static final boolean ENABLED = false;

    private CameraContainer cameras;

    private Pose3d estimatedPose;
    private double timeStamp = -1;
    private List<PhotonTrackedTarget> targets = new ArrayList<>();

    @Override
    public void initialize() {

    }

    @Override
    public void periodic() {
        if (ENABLED) {
            timeStamp = cameras.getFilteredResult().getLatencyMillis();
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

    public Optional<Double> getTimeStamp() {
        return !ENABLED || timeStamp == -1 ? Optional.of(timeStamp) : Optional.empty();
    }

    public Optional<VisionDataPacket> getData() {
        if (ENABLED) {
            VisionDataPacket dataPacket = new VisionDataPacket();

            dataPacket.setLatency(cameras.getLatency());
            dataPacket.setNumberOfTarets(cameras.getTargetCount());
            dataPacket.setTimeStamp(getTimeStamp());

            return Optional.of(dataPacket);
        }
        return Optional.empty();
    }
}