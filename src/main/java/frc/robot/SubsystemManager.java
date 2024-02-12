// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubsystem;
import frc.robot.subsystems.VisionSubsystem;

/**
 * Manages the subsystems and the interactions between them.
 */
public class SubsystemManager {
    private final DriveSubsystem driveSubsystem = new DriveSubsystem();
    private final VisionSubsystem visionSubsystem = new VisionSubsystem();
    private final NavXSubsystem navXSubsystem = new NavXSubsystem();

    public SubsystemManager() {
        navXSubsystem.initialize();
        driveSubsystem.initialize();
        visionSubsystem.initialize();

        periodic();
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

    public void periodic() {
        RobotOutputs outputs = RobotOutputs.getInstance();
        if (visionSubsystem.isEnabled()) {
            outputs.setVisionOutput(visionSubsystem.getOutputs());
        }

        if (driveSubsystem.isEnabled()) {
            outputs.setDriveOutput(driveSubsystem.getOutputs());
        }

        if (navXSubsystem.isEnabled()) {
            outputs.setNavXOutput(navXSubsystem.getOutputs());
        }
    }
}
