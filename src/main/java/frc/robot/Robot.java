// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.ResetTurningEncoderCommand;
import frc.robot.operation.OperatorInterface;
import frc.robot.processors.OdometryProcessor;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends LoggedRobot {
  private Command autonomousCommand;
  private SubsystemManager subsystemManager;
  private CommandFactory commandFactory;
  private OdometryProcessor odometry;
  private OperatorInterface operatorInterface;
  private PowerDistribution powerDistribution;

  public void loggerInit() {
    Logger.recordMetadata("ProjectName", BuildConstants.MAVEN_NAME);
    Logger.recordMetadata("Version", BuildConstants.VERSION);
    Logger.recordMetadata("GITRevision", BuildConstants.GIT_REVISION + "");
    Logger.recordMetadata("GIT_SHA", BuildConstants.GIT_SHA);
    Logger.recordMetadata("GIT_Date", BuildConstants.GIT_DATE);
    Logger.recordMetadata("GIT_Branch", BuildConstants.GIT_BRANCH);
    Logger.recordMetadata("BuildDate", BuildConstants.BUILD_DATE);
    Logger.recordMetadata("BuildUnixTime", BuildConstants.BUILD_UNIX_TIME + "");

    if (isReal()) {
      Logger.addDataReceiver(new WPILOGWriter()); // Log to a USB stick ("/U/logs")
      Logger.addDataReceiver(new NT4Publisher()); // Publish data to NetworkTables
      powerDistribution = new PowerDistribution(1, ModuleType.kRev); // Enables power distribution
      // logging
      powerDistribution.clearStickyFaults();
    } else {
      setUseTiming(false); // Run as fast as possible
      String logPath = LogFileUtil.findReplayLog(); // Pull the replay log from AdvantageScope (or
                                                    // prompt the user)
      Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim"))); // Save
                                                                                            // outputs
                                                                                            // to a
                                                                                            // new
                                                                                            // log
      Logger.addDataReceiver(new NT4Publisher());
    }

    // Logger.disableDeterministicTimestamps() // See "Deterministic Timestamps" in the
    // "Understanding Data Flow" page
    Logger.start(); // Start logging! No more data receivers, replay sources, or metadata values may
                    // be added.
    LogTable.disableProtobufWarning();
  }

  @Override
  public void robotInit() {
    loggerInit();
    subsystemManager = new SubsystemManager();
    odometry = new OdometryProcessor();
    commandFactory = new CommandFactory(subsystemManager, odometry);
    operatorInterface = new OperatorInterface(commandFactory, subsystemManager, odometry);
    operatorInterface.create();
    odometry.createEstimator();
  }

  @Override
  public void robotPeriodic() {
    subsystemManager.periodic();
    CommandScheduler.getInstance().run();
    odometry.update();
  }

  @Override
  public void autonomousInit() {
    odometry.setIntegrateVision(false);
    autonomousCommand = commandFactory.getAutoCommand();

    if (autonomousCommand != null) {
      autonomousCommand.schedule();
    }
  }

  @Override
  public void disabledExit() {
    new ResetTurningEncoderCommand(subsystemManager.getDriveSubsystem()).schedule();
  }

  @Override
  public void teleopInit() {
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    odometry.setIntegrateVision(true);
  }

  @Override
  public void teleopPeriodic() {
    // for things that only happen in teleop
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }
}
