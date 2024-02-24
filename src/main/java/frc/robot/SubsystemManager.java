// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import entech.subsystems.EntechSubsystem;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.climb.ClimbSubsystem;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.navx.NavXSubsystem;
import frc.robot.subsystems.pivot.PivotSubsystem;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.vision.VisionSubsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  private final PivotSubsystem pivotSubsystem = new PivotSubsystem();
  private final ClimbSubsystem climbSubsystem = new ClimbSubsystem();

  public SubsystemManager() {
    navXSubsystem.initialize();
    driveSubsystem.initialize();
    visionSubsystem.initialize();
    intakeSubsystem.initialize();
    shooterSubsystem.initialize();
    transferSubsystem.initialize();
    pivotSubsystem.initialize();
    climbSubsystem.initialize();

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

  public PivotSubsystem getPivotSubsystem() {
    return pivotSubsystem;
  }


  public List<EntechSubsystem<?, ?>> getSubsystemList() {
    ArrayList<EntechSubsystem<?, ?>> r = new ArrayList<>();
    r.add(navXSubsystem);
    r.add(driveSubsystem);
    r.add(visionSubsystem);
    r.add(intakeSubsystem);
    r.add(shooterSubsystem);
    r.add(transferSubsystem);
    r.add(pivotSubsystem);
    return r;
  }

  public void periodic() {
    RobotIO outputs = RobotIO.getInstance();
    if (climbSubsystem.isEnabled()) {
      outputs.updateClimb(climbSubsystem.getOutputs());
    }
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

    if (pivotSubsystem.isEnabled()) {
      outputs.updatePivot(pivotSubsystem.getOutputs());
    }
  }
}
