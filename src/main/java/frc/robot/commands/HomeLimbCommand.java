// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import entech.commands.EntechCommandBase;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElbowSubsystem;

/** An example command that uses an example subsystem. */
public class HomeLimbCommand extends EntechCommandBase {

    private final ElbowSubsystem elbowSubsystem;
    private final ArmSubsystem armSubsystem;

    /**
     * Creates a new PositionArmCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public HomeLimbCommand(ElbowSubsystem elbowSubsystem, ArmSubsystem armSubsystem) {
        super(elbowSubsystem, armSubsystem);
        this.elbowSubsystem = elbowSubsystem;
        this.armSubsystem = armSubsystem;

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        armSubsystem.home();
        if (armSubsystem.isAtLowerLimit())
            elbowSubsystem.home();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return armSubsystem.isAtLowerLimit() && elbowSubsystem.isAtLowerLimit();
    }

    // Returns true if this command should run when robot is disabled.
    @Override
    public boolean runsWhenDisabled() {
        return false;
    }
}