package frc.robot;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import entech.commands.EntechCommandBase;
import entech.util.EntechJoystick;
import frc.robot.commands.DriveCommand;

public class CommandFactory {
    private SubsystemInterface subsystems;
    // private Supplier<Pose2d> robotPoseSupplier;

    public CommandFactory(SubsystemInterface subsystems, Supplier<Pose2d> robotPoseSupplier) {
        this.subsystems = subsystems;
        // this.robotPoseSupplier = robotPoseSupplier;
    }

    public EntechCommandBase driveCommand(EntechJoystick joystick) {
        return new DriveCommand(
                subsystems.getDriveSubsys(),
                joystick::getY,
                joystick::getX,
                joystick::getZ,
                () -> {
                    return false;
                },
                true);
    }
}
