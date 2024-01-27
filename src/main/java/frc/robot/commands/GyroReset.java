package frc.robot.commands;

import entech.commands.EntechCommand;
import frc.robot.subsystems.DriveSubsystem;

public class GyroReset extends EntechCommand {
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

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
