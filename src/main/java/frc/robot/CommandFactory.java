package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import entech.util.SpeakerPivotSolution;
import frc.robot.commands.GyroReset;
import frc.robot.commands.PositionPivotCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.StartShooterCommand;
import frc.robot.io.RobotIO;
import frc.robot.processors.OdometryProcessor;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.navx.NavXSubsystem;
import frc.robot.subsystems.pivot.PivotSubsystem;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.vision.VisionSubsystem;

@SuppressWarnings("unused")
public class CommandFactory {
  private final DriveSubsystem driveSubsystem;
  private final VisionSubsystem visionSubsystem;
  private final NavXSubsystem navXSubsystem;
  private final IntakeSubsystem intakeSubsystem;
  private final ShooterSubsystem shooterSubsystem;
  private final TransferSubsystem transferSubsystem;
  private final PivotSubsystem pivotSubsystem;
  private final OdometryProcessor odometry;

  private final SubsystemManager subsystemManager;
  private final SendableChooser<Command> autoChooser;


  public CommandFactory(SubsystemManager subsystemManager, OdometryProcessor odometry) {

    this.driveSubsystem = subsystemManager.getDriveSubsystem();
    this.visionSubsystem = subsystemManager.getVisionSubsystem();
    this.navXSubsystem = subsystemManager.getNavXSubsystem();
    this.shooterSubsystem = subsystemManager.getShooterSubsystem();
    this.transferSubsystem = subsystemManager.getTransferSubsystem();
    this.pivotSubsystem = subsystemManager.getPivotSubsystem();
    this.intakeSubsystem = subsystemManager.getIntakeSubsystem();
    this.odometry = odometry;
    this.subsystemManager = subsystemManager;


    AutoBuilder.configureHolonomic(odometry::getEstimatedPose, // Robot pose supplier
        odometry::resetOdometry,
        // Method to reset odometry (will be called if your auto has a starting pose)
        () -> navXSubsystem.toOutputs().getChassisSpeeds(), // ChassisSpeeds supplier. MUST BE ROBOT
                                                            // RELATIVE
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

  public Command shootSpeakerCommand() {
    return new SequentialCommandGroup(
        new ParallelCommandGroup(new StartShooterCommand(shooterSubsystem),
            new PositionPivotCommand(pivotSubsystem, SpeakerPivotSolution.getShooterSolutionDeg())),
        new ShootCommand(transferSubsystem, shooterSubsystem));
  }
}
