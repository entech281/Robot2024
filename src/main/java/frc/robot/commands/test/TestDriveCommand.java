package frc.robot.commands.test;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.entech.commands.EntechCommand;
import frc.entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.subsystems.drive.DriveInput;
import frc.robot.subsystems.drive.DriveSubsystem;

public class TestDriveCommand extends EntechCommand {
  private final DriveSubsystem drive;
  private final StoppingCounter counter =
      new StoppingCounter(RobotConstants.TEST_CONSTANTS.STANDARD_TEST_LENGTH);
  private static final double DRIVE_POWER = 0.15;
  private int stage = 0;

  public TestDriveCommand(DriveSubsystem driveSubsystem) {
    super(driveSubsystem);
    this.drive = driveSubsystem;
  }

  @Override
  public void execute() {
    DriveInput input = new DriveInput();

    input.setLatestOdometryPose(new Pose2d());
    input.setGyroAngle(new Rotation2d());
    input.setRotation(0.0);
    input.setXSpeed(0.0);
    input.setYSpeed(0.0);

    switch (stage) {
      case 0:
        input.setXSpeed(DRIVE_POWER);
        break;
      case 1:
        input.setYSpeed(DRIVE_POWER);
        input.setXSpeed(DRIVE_POWER);
        break;
      case 2:
        input.setYSpeed(DRIVE_POWER);
        break;
      case 3:
        input.setXSpeed(-DRIVE_POWER);
        input.setYSpeed(DRIVE_POWER);
        break;
      case 4:
        input.setXSpeed(-DRIVE_POWER);
        break;
      case 5:
        input.setRotation(DRIVE_POWER);
        break;
      case 6:
        input.setRotation(-DRIVE_POWER);
        break;
      case 7:
        input.setXSpeed(DRIVE_POWER);
        break;
      case 8:
        double sum = 0;
        for (SwerveModuleState state : drive.getOutputs().getModuleStates()) {
          sum += state.speedMetersPerSecond;
        }
        counter.isFinished(sum < 0.0001);
        break;
      case 9:
        drive.setX();
        break;
      default:
        break;
    }

    if (counter.isFinished(true)) {
      counter.reset();
      stage++;
    }

    if (stage != 9)
      drive.updateInputs(input);
  }

  @Override
  public void initialize() {
    counter.reset();
    DriveInput stop = new DriveInput();
    stage = 0;

    stop.setLatestOdometryPose(new Pose2d());
    stop.setGyroAngle(new Rotation2d());
    stop.setRotation(0.0);
    stop.setXSpeed(0.0);
    stop.setYSpeed(0.0);

    drive.updateInputs(stop);
  }

  @Override
  public boolean isFinished() {
    return stage >= 10;
  }

  @Override
  public void end(boolean interrupted) {
    DriveInput stop = new DriveInput();

    stop.setLatestOdometryPose(new Pose2d());
    stop.setGyroAngle(new Rotation2d());
    stop.setRotation(0.0);
    stop.setXSpeed(0.0);
    stop.setXSpeed(0.0);

    drive.updateInputs(stop);
  }
}
