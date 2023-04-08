package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;

import frc.robot.commands.DriveCommand;

import java.util.function.Supplier;
import java.util.List;

public class CommandFactory {
    private SubsystemInterface subsystems;
    private Supplier<Pose2d> robotPoseSupplier;

    public CommandFactory(SubsystemInterface subsystems, Supplier<Pose2d> robotPoseSupplier) {
        this.subsystems = subsystems;
        this.robotPoseSupplier = robotPoseSupplier;
    }

    public Command driveCommand(Joystick js) {
        return new DriveCommand(subsystems.getDriveSubsys(), subsystems.getNavXSubSys(), js, () -> { return false;});
    }

    public Command driveTrajectoryCommand(Pose2d endingPose, List<Translation2d> waypoints) {
        TrajectoryConfig config =
            new TrajectoryConfig(
                    RobotConstants.AutoConstants.MAX_SPEED_METERS_PER_SECOND,
                    RobotConstants.AutoConstants.MAX_ACCELERATION_METERS_PER_SECOND_SQUARED)
                .setKinematics(RobotConstants.Swerve.SWERVE_KINEMATICS);
        
        Trajectory trajectory =
            TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                robotPoseSupplier.get(),
                waypoints,
                endingPose,
                config);

        ProfiledPIDController thetaController =
        new ProfiledPIDController(
            RobotConstants.AutoConstants.P_THETA_CONTROLLER, 0, 0, RobotConstants.AutoConstants.THETA_CONTROLLER_CONSTRAINTS);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);
        
        return new SwerveControllerCommand(
            trajectory, 
            robotPoseSupplier, 
            RobotConstants.Swerve.SWERVE_KINEMATICS, 
            new PIDController(RobotConstants.AutoConstants.PX_CONTROLLER, 0, 0),
            new PIDController(RobotConstants.AutoConstants.PY_CONTROLLER, 0, 0),
            thetaController,
            null, 
            subsystems.getDriveSubsys());
    }

    public Command getAutoCommand() {
        return driveTrajectoryCommand(new Pose2d(3, 0, Rotation2d.fromDegrees(90)), List.of(new Translation2d(1.5, 1)));
    }
}
