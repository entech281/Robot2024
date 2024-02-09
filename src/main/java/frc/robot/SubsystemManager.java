// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionOutput;
import frc.robot.subsystems.VisionSubsystem;

/**
 * Manages the subsystems and the interactions between them.
 */
public class SubsystemManager {
    private final DriveSubsystem driveSubsystem = new DriveSubsystem();
    private final VisionSubsystem visionSubsystem = new VisionSubsystem();

    public SubsystemManager() {
        driveSubsystem.initialize();
        visionSubsystem.initialize();
    }

    public DriveSubsystem getDriveSubsystem() {
        return driveSubsystem;
    }

    public VisionSubsystem getVisionSubsystem() {
        return visionSubsystem;
    }

    public void periodic() {
        VisionOutput visionOutput = visionSubsystem.getOutputs();
        if (visionSubsystem.isEnabled()) {
            driveSubsystem.addVisionData(visionOutput);
        }
    }
}
