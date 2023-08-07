package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import entech.commands.EntechCommandBase;

import java.util.function.Supplier;
import frc.robot.commands.DriveCommand;

public class CommandFactory {
    private SubsystemInterface subsystems;
    // private Supplier<Pose2d> robotPoseSupplier;

    public CommandFactory(SubsystemInterface subsystems, Supplier<Pose2d> robotPoseSupplier) {
        this.subsystems = subsystems;
        // this.robotPoseSupplier = robotPoseSupplier;
    }

    public EntechCommandBase driveCommand(CommandJoystick inputJoyStick) {
        return new DriveCommand(
            subsystems.getDriveSubsys(), 
            () -> { return inputJoyStick.getY();}, 
            () -> { return inputJoyStick.getX(); }, 
            () -> { return inputJoyStick.getZ(); },
            () -> { return true; }, 
            false
            );
    }
}
