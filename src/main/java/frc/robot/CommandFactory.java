package frc.robot;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.DriveCommand;

public class CommandFactory {
    private SubsystemInterface subsystems;
    private Supplier<Pose2d> robotPoseSupplier;

    public CommandFactory(SubsystemInterface subsystems, Supplier<Pose2d> robotPoseSupplier) {
        this.subsystems = subsystems;
        this.robotPoseSupplier = robotPoseSupplier;
    }

    public Command driveCommand(Joystick js) {
        return new DriveCommand(subsystems.getDriveSubsys(), subsystems.getNavXSubSys(), js);
    }

    public Command getAutoCommand() {
        return null;
    }
}
