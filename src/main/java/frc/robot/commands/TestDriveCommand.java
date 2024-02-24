package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.subsystems.drive.DriveInput;
import frc.robot.subsystems.drive.DriveSubsystem;

public class TestDriveCommand extends EntechCommand {
  private final DriveSubsystem drive;
  private final StoppingCounter counter = new StoppingCounter(this.getClass().getSimpleName(), 150);
  private static final double DRIVE_POWER = 0.25;
  private int stage = 0;

  public TestDriveCommand(DriveSubsystem driveSubsystem) {
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
      default:
        break;
    }

    if (counter.isFinished(true)) {
      counter.reset();
      stage++;
    }

    drive.updateInputs(input);
  }

  @Override
  public void initialize() {
    DriveInput stop = new DriveInput();

    stop.setLatestOdometryPose(new Pose2d());
    stop.setGyroAngle(new Rotation2d());
    stop.setRotation(0.0);
    stop.setXSpeed(0.0);
    stop.setYSpeed(0.0);

    drive.updateInputs(stop);
  }

  @Override
  public boolean isFinished() {
    return stage >= 5;
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
