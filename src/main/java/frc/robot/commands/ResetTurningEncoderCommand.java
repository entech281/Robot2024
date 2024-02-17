package frc.robot.commands;

import entech.commands.EntechCommand;
import frc.robot.subsystems.drive.DriveSubsystem;

public class ResetTurningEncoderCommand extends EntechCommand {
    private final DriveSubsystem driveSubsystem;

    public ResetTurningEncoderCommand(DriveSubsystem driveSubsystem) {
        super(driveSubsystem);
        this.driveSubsystem = driveSubsystem;
    }

    @Override
    public void initialize() {
        driveSubsystem.resetTurningEncoders();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}