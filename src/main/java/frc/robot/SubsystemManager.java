// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.io.RobotIO;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.navx.NavXSubsystem;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.vision.VisionSubsystem;


/**
 * Manages the subsystems and the interactions between them.
 */
public class SubsystemManager {
    private final DriveSubsystem driveSubsystem = new DriveSubsystem();
    private final VisionSubsystem visionSubsystem = new VisionSubsystem();
    private final NavXSubsystem navXSubsystem = new NavXSubsystem();
    private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
    private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
    private final TransferSubsystem transferSubsystem = new TransferSubsystem();

    public SubsystemManager() {
        navXSubsystem.initialize();
        driveSubsystem.initialize();
        visionSubsystem.initialize();
        intakeSubsystem.initialize();
        shooterSubsystem.initialize();
        transferSubsystem.initialize();

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

    public IntakeSubsystem getIntakeSubsystem() {
        return intakeSubsystem;
    }

    public ShooterSubsystem getShooterSubsystem() {
        return shooterSubsystem;
    }

    public TransferSubsystem getTransferSubsystem() {
        return transferSubsystem;
    }

    public void periodic() {
        RobotIO outputs = RobotIO.getInstance();
        if (visionSubsystem.isEnabled()) {
            outputs.updateVision(visionSubsystem.getOutputs());
        }

        if (driveSubsystem.isEnabled()) {
            outputs.updateDrive(driveSubsystem.getOutputs());
        }

        if (navXSubsystem.isEnabled()) {
            outputs.updateNavx(navXSubsystem.getOutputs());
        }

        if (transferSubsystem.isEnabled()) {
            outputs.updateTransfer(transferSubsystem.getOutputs());
        }

        if (intakeSubsystem.isEnabled()) {
            outputs.updateIntake(intakeSubsystem.getOutputs());
        }

        if (shooterSubsystem.isEnabled()) {
            outputs.updateShooter(shooterSubsystem.getOutputs());
        }
    }
}
