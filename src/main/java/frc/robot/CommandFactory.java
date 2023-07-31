package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;

import java.util.function.Supplier;

public class CommandFactory {
    private SubsystemInterface subsystems;
    private Supplier<Pose2d> robotPoseSupplier;

    public CommandFactory(SubsystemInterface subsystems, Supplier<Pose2d> robotPoseSupplier) {
        this.subsystems = subsystems;
        this.robotPoseSupplier = robotPoseSupplier;
    }

    public Command driveCommand(double forward, double left, double rotation) {
        
    }
}
