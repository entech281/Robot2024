package frc.robot.commands;

import entech.commands.EntechCommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class GyroReset extends EntechCommandBase {
    private final Runnable reset;

    public GyroReset(DriveSubsystem driveSubsystem) {
        reset = () -> {
            driveSubsystem.zeroHeading();
        };
    }

    @Override
    public void initialize() {
        reset.run();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
