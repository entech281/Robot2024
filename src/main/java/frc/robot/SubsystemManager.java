// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubsystem;
import frc.robot.subsystems.OdometryInput;
import frc.robot.subsystems.OdometrySubsystem;
import frc.robot.subsystems.VisionOutput;
import frc.robot.subsystems.VisionSubsystem;

/**
 * Manages the subsystems and the interactions between them.
 */
public class SubsystemManager {
    private final DriveSubsystem driveSubsystem = new DriveSubsystem();
    private final VisionSubsystem visionSubsystem = new VisionSubsystem();
    private final NavXSubsystem navXSubsystem = new NavXSubsystem();
    private final OdometrySubsystem odometrySubsystem = new OdometrySubsystem();

    public SubsystemManager() {
        navXSubsystem.initialize();
        driveSubsystem.initialize();
        visionSubsystem.initialize();
        odometrySubsystem.initialize();
    }

    public DriveSubsystem getDriveSubsystem() {
        return driveSubsystem;
    }

    public VisionSubsystem getVisionSubsystem() {
        return visionSubsystem;
    }

    public NavXSubsystem getNavXSubsystem() {
        return navXSubsystem;
    }

    public OdometrySubsystem getOdometrySubsystem() {
        return odometrySubsystem;
    }

    public void periodic() {
        if (driveSubsystem.isEnabled()) {
            OdometryInput odoIn = new OdometryInput();

            odoIn.chassisSpeeds = navXSubsystem.getOutputs().chassisSpeeds;
            odoIn.yaw = Rotation2d.fromDegrees(navXSubsystem.getOutputs().yaw);
            if (visionSubsystem.isEnabled()) {
                VisionOutput visionOut = visionSubsystem.getOutputs();
                odoIn.visionEstimate = visionOut.getEstimatedPose();
                odoIn.visionTimeStamp = visionOut.getTimeStamp();
            }
        }
    }
}
