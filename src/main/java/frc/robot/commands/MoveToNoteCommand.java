package frc.robot.commands;

import org.opencv.core.Point;
import frc.entech.commands.EntechCommand;
import frc.robot.io.DriveInputSupplier;
import frc.robot.io.RobotIO;
import frc.robot.operation.UserPolicy;
import frc.robot.processors.filters.NoteAlignmentFilter;
import frc.robot.subsystems.drive.DriveInput;
import frc.robot.subsystems.drive.DriveSubsystem;

public class MoveToNoteCommand extends EntechCommand {
  private final double speed;
  private final DriveSubsystem drive;
  private final DriveInputSupplier inputSupplier;
  private final double xSpeed;
  private final double ySpeed;
  private final NoteAlignmentFilter filter = new NoteAlignmentFilter();

  public MoveToNoteCommand(DriveSubsystem drive, double roughRobotAngle, DriveInputSupplier inputs,
      double speed) {
    super(drive);
    this.speed = speed;
    this.inputSupplier = inputs;
    this.drive = drive;
    Point noteUnitVector = new Point(Math.cos(roughRobotAngle), Math.sin(roughRobotAngle));
    xSpeed = this.speed * noteUnitVector.x;
    ySpeed = this.speed * noteUnitVector.y;
  }

  @Override
  public void end(boolean interrupted) {
    DriveInput stop = new DriveInput(inputSupplier.getDriveInput());
    stop.setRotation(0.0);
    stop.setXSpeed(0.0);
    stop.setYSpeed(0.0);
    drive.updateInputs(stop);
    UserPolicy.getInstance().setAligningToNote(false);
  }

  @Override
  public void execute() {
    if (!RobotIO.getInstance().getInternalNoteDetectorOutput().hasNote()) {
      DriveInput input = new DriveInput(inputSupplier.getDriveInput());
      input.setRotation(0.0);
      input.setXSpeed(xSpeed);
      input.setYSpeed(ySpeed);
      drive.updateInputs(filter.process(input));
    } else {
      DriveInput stop = new DriveInput(inputSupplier.getDriveInput());
      stop.setRotation(0.0);
      stop.setXSpeed(0.0);
      stop.setYSpeed(0.0);
      drive.updateInputs(stop);
      UserPolicy.getInstance().setAligningToNote(false);
    }
  }

  @Override
  public boolean isFinished() {
    return Math.abs(RobotIO.getInstance().getDriveOutput().getSpeeds().vxMetersPerSecond) < 0.1
        && Math.abs(RobotIO.getInstance().getDriveOutput().getSpeeds().vyMetersPerSecond) < 0.1;
  }

  @Override
  public void initialize() {
    UserPolicy.getInstance().setAligningToNote(true);
  }
}
