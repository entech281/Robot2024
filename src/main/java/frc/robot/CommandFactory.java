package frc.robot;

import java.util.Optional;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.GyroResetByAngleCommand;
import frc.robot.commands.IntakeNoteCommand;
import frc.robot.commands.LEDDefaultCommand;
import frc.robot.commands.MoveToNoteCommand;
import frc.robot.commands.ShootAngleCommand;
import frc.robot.io.RobotIO;
import frc.robot.operation.UserPolicy;
import frc.robot.processors.OdometryProcessor;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.led.LEDSubsystem;
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
  private final LEDSubsystem ledSubsystem;

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
    this.ledSubsystem = subsystemManager.getLedSubsystem();

    if (subsystemManager.getLedSubsystem().isEnabled()) {
      subsystemManager.getLedSubsystem()
          .setDefaultCommand(new LEDDefaultCommand(subsystemManager.getLedSubsystem()));
    }

    AutoBuilder.configureHolonomic(odometry::getEstimatedPose, // Robot pose supplier
        // Method to reset odometry (will be called if your auto has a starting pose)
        odometry::resetOdometry,
        // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE. Choose one:
        // () -> navXSubsystem.toOutputs().getChassisSpeeds(),
        driveSubsystem::getChassisSpeeds, driveSubsystem::pathFollowDrive,
        new HolonomicPathFollowerConfig(
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

    NamedCommands.registerCommand("intake", new IntakeNoteCommand(intakeSubsystem,
        transferSubsystem, shooterSubsystem, subsystemManager.getLedSubsystem()));
    NamedCommands.registerCommand("subwooferShot", new ShootAngleCommand(shooterSubsystem,
        pivotSubsystem, transferSubsystem, RobotConstants.PIVOT.SPEAKER_SUBWOOFER_SCORING));
    NamedCommands.registerCommand("podiumShot", new ShootAngleCommand(shooterSubsystem,
        pivotSubsystem, transferSubsystem, RobotConstants.PIVOT.SPEAKER_PODIUM_SCORING));
    NamedCommands.registerCommand("ampShot", new ShootAngleCommand(shooterSubsystem, pivotSubsystem,
        transferSubsystem, RobotConstants.PIVOT.SHOOT_AMP_POSITION_DEG));
    NamedCommands.registerCommand("autoIntake", new ParallelCommandGroup(
        new MoveToNoteCommand(driveSubsystem, 0, RobotIO.getInstance(), 0.4),
        new IntakeNoteCommand(intakeSubsystem, transferSubsystem, shooterSubsystem, ledSubsystem)));
    NamedCommands.registerCommand("autoIntakeSlow", new ParallelCommandGroup(
        new MoveToNoteCommand(driveSubsystem, 0, RobotIO.getInstance(), 0.05),
        new IntakeNoteCommand(intakeSubsystem, transferSubsystem, shooterSubsystem, ledSubsystem)));

    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  public Command getAutoCommand() {
    SequentialCommandGroup auto = new SequentialCommandGroup();
    auto.addCommands(
        new GyroResetByAngleCommand(navXSubsystem, odometry, autoChooser.getSelected().getName()));
    auto.addCommands(new WaitCommand(0.5));
    auto.addCommands(autoChooser.getSelected());
    return auto;
  }

  public Command getTargetSpeakerCommand() {
    return Commands.runOnce(() -> {
      Optional<Alliance> team = DriverStation.getAlliance();
      if (team.isPresent() && team.get() == Alliance.Blue) {
        UserPolicy.getInstance().setTargetPose(new Pose2d(0.0, 5.31, new Rotation2d()));
        return;
      }
      UserPolicy.getInstance().setTargetPose(new Pose2d(16.54, 5.54, new Rotation2d()));
    });
  }

  public Command getTargetAmpCommand() {
    return Commands.runOnce(() -> {
      Optional<Alliance> team = DriverStation.getAlliance();
      if (team.isPresent() && team.get() == Alliance.Blue) {
        UserPolicy.getInstance().setTargetPose(new Pose2d(1.78, 8.14, new Rotation2d()));
        return;
      }
      UserPolicy.getInstance().setTargetPose(new Pose2d(14.75, 8.14, new Rotation2d()));
    });
  }
}
