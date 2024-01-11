package frc.robot;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import entech.commands.EntechCommand;
import frc.robot.commands.GyroReset;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

@SuppressWarnings("unused")
public class CommandFactory {
    private DriveSubsystem driveSubsystem;
    private VisionSubsystem visionSubsystem;

    public CommandFactory(RobotContainer robotContainer) {
        this.driveSubsystem = robotContainer.getDriveSubsystem();
        this.visionSubsystem = robotContainer.getVisionSubsystem();
    }

    public EntechCommand gyroResetCommand() {
        return new GyroReset(driveSubsystem);
    }

    public Command getAutoCommand() {
        SequentialCommandGroup auto = new SequentialCommandGroup();
        auto.addCommands(new PathPlannerAuto("Test"));
        return auto;
    }
}
