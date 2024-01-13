// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import entech.commands.AutoSequence;
import frc.robot.OI.OperatorInterface;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private AutoSequence autonomousCommand;
    private SubsystemManager subsystemManager;
    private CommandFactory commandFactory;

    @Override
    public void robotInit() {
        subsystemManager = new SubsystemManager();
        commandFactory = new CommandFactory(subsystemManager);
        OperatorInterface.create(commandFactory, subsystemManager);
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledPeriodic() {
        subsystemManager.periodic();
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = commandFactory.getAutoCommand();

        if (autonomousCommand != null) {
            subsystemManager.getDriveSubsystem().resetOdometry(autonomousCommand.getStartingPose());
            autonomousCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {
        subsystemManager.periodic();
    }

    @Override
    public void teleopInit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {
        subsystemManager.periodic();
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }
}
