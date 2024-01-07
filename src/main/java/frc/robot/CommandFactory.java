package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import entech.commands.EntechCommandBase;
import frc.robot.commands.GyroReset;
import frc.robot.subsystems.DriveSubsystem;

public class CommandFactory {
    private DriveSubsystem driveSubsystem;

    public CommandFactory(RobotContainer robotContainer) {
        this.driveSubsystem = robotContainer.getDriveSubsystem();
    }

    public EntechCommandBase gyroResetCommand() {
        return new GyroReset(driveSubsystem);
    }

    public Command getAutoCommand() {
        SequentialCommandGroup auto = new SequentialCommandGroup();
        return auto;
    }
}
