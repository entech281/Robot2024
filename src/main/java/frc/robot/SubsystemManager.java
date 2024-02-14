// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.io.RobotIO;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.vision.VisionDataPacket;

import java.util.Optional;


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


        RobotIO.state.updateDrive(driveSubsystem.getOutputs());
        RobotIO.state.updateVision(visionSubsystem.getOutputs());
        //RobotIO.state.updateNavx(navxSubsystem.getOutputs());

        Optional<VisionDataPacket> visionData = visionSubsystem.getData();
        if (visionData.isPresent()) {
            driveSubsystem.addVisionData(visionData.get());
        }
    }
}
