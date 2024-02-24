package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.GyroReset;
import frc.robot.processors.OdometryProcessor;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.navx.NavXSubsystem;
import frc.robot.subsystems.vision.VisionSubsystem;

@SuppressWarnings("unused")
public class CommandFactory {
  private final DriveSubsystem driveSubsystem;
  private final VisionSubsystem visionSubsystem;
  private final NavXSubsystem navXSubsystem;

  private final OdometryProcessor odometry;

  private final SendableChooser<Command> autoChooser;


  public CommandFactory(SubsystemManager subsystemManager, OdometryProcessor odometry) {
    this.driveSubsystem = subsystemManager.getDriveSubsystem();
    this.visionSubsystem = subsystemManager.getVisionSubsystem();
    this.navXSubsystem = subsystemManager.getNavXSubsystem();
    this.odometry = odometry;

    AutoBuilder.configureHolonomic(odometry::getEstimatedPose, // Robot pose supplier
        odometry::resetOdometry,
        // Method to reset odometry (will be called if your auto has a starting pose)
        () -> navXSubsystem.getOutputs().getChassisSpeeds(), // ChassisSpeeds supplier. MUST BE
                                                             // ROBOT RELATIVE
        driveSubsystem::pathFollowDrive, new HolonomicPathFollowerConfig(
            // HolonomicPathFollowerConfig, this should likely live in your Constants
            // class
            new PIDConstants(RobotConstants.AUTONOMOUS.TRANSLATION_CONTROLLER_P, 0.0, 0.0),
            // Translation PID constants
            new PIDConstants(RobotConstants.AUTONOMOUS.ROTATION_CONTROLLER_P, 0.0, 0.0),
            // Rotation PID constants
            RobotConstants.AUTONOMOUS.MAX_MODULE_SPEED_METERS_PER_SECOND,
            // Max module speed, in m/s
            RobotConstants.DrivetrainConstants.DRIVE_BASE_RADIUS_METERS,
            // Drive base radius in meters. Distance from robot center to furthest
            // module.
            new ReplanningConfig()
        // Default path replanning config. See the API for the options here
        ), () -> {
          // Boolean supplier that controls when the path will be mirrored for the red
          // alliance
          // This will flip the path being followed to the red side of the field.
          // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

          var alliance = DriverStation.getAlliance();
          if (alliance.isPresent()) {
            return alliance.get() == DriverStation.Alliance.Red;
          }
          return false;
        }, driveSubsystem);


    NamedCommands.registerCommand("Marker 1", Commands.print("Passed marker 1"));
    NamedCommands.registerCommand("IntakeCommand", Commands.print("Activate Intake"));
    // NamedCommands.registerCommand("Marker 2", Commands.print("Passed marker 2"));
    NamedCommands.registerCommand("Marker 2", Commands.run(() -> {
      DriverStation.reportWarning("********** I am at marker 2", false);
    }));

    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);

  }

  public Command getAutoCommand() {
    SequentialCommandGroup auto = new SequentialCommandGroup();
    auto.addCommands(new GyroReset(navXSubsystem, odometry));
    auto.addCommands(new WaitCommand(2));
    auto.addCommands(autoChooser.getSelected());
    return auto;
  }
}
