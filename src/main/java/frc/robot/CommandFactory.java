package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.GyroReset;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.NavXSubsystem;
import frc.robot.subsystems.OdometrySubsystem;
import frc.robot.subsystems.VisionSubsystem;

@SuppressWarnings("unused")
public class CommandFactory {
    private DriveSubsystem driveSubsystem;
    private VisionSubsystem visionSubsystem;
    private NavXSubsystem navXSubsystem;
    private OdometrySubsystem odometrySubsystem;

    public CommandFactory(SubsystemManager subsystemManager) {
        this.driveSubsystem = subsystemManager.getDriveSubsystem();
        this.visionSubsystem = subsystemManager.getVisionSubsystem();

        AutoBuilder.configureHolonomic(
                () -> { return odometrySubsystem.getOutputs().pose; }, // Robot pose supplier
                odometrySubsystem::resetOdometry, // Method to reset odometry (will be called if your auto has a starting pose)
                () -> { return odometrySubsystem.getOutputs().chassisSpeeds; }, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
                driveSubsystem::pathFollowDrive,
                new HolonomicPathFollowerConfig( // HolonomicPathFollowerConfig, this should likely live in your Constants class
                        new PIDConstants(RobotConstants.AUTONOMOUS.TRANSLATION_CONTROLLER_P, 0.0, 0.0), // Translation PID constants
                        new PIDConstants(RobotConstants.AUTONOMOUS.ROTATION_CONTROLLER_P, 0.0, 0.0), // Rotation PID constants
                        RobotConstants.AUTONOMOUS.MAX_MODULE_SPEED_METERS_PER_SECOND, // Max module speed, in m/s
                        RobotConstants.DrivetrainConstants.DRIVE_BASE_RADIUS_METERS, // Drive base radius in meters. Distance from robot center to furthest module.
                        new ReplanningConfig() // Default path replanning config. See the API for the options here
                ),
                () -> {
                    // Boolean supplier that controls when the path will be mirrored for the red alliance
                    // This will flip the path being followed to the red side of the field.
                    // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

                    var alliance = DriverStation.getAlliance();
                    if (alliance.isPresent()) {
                        return alliance.get() == DriverStation.Alliance.Red;
                    }
                    return false;
                },
                driveSubsystem
            );
    }

    public Command getAutoCommand() {
        SequentialCommandGroup auto = new SequentialCommandGroup();
        auto.addCommands(new GyroReset(navXSubsystem, odometrySubsystem));
        auto.addCommands(new WaitCommand(2));
        auto.addCommands(new PathPlannerAuto("auto1p1"));
        return auto;
    }
}
