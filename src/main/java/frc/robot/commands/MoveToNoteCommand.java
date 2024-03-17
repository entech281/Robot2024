package frc.robot.commands;

import org.opencv.core.Point;
import entech.commands.EntechCommand;
import frc.robot.io.DriveInputSupplier;
import frc.robot.operation.UserPolicy;
import frc.robot.processors.filters.NoteAlignmentFilter;
import frc.robot.subsystems.drive.DriveInput;
import frc.robot.subsystems.drive.DriveSubsystem;

public class MoveToNoteCommand extends EntechCommand {
  private static final double SPEED = 0.25;
  private final DriveSubsystem drive;
  private DriveInput input;
  private final DriveInputSupplier inputSupplier;
  private final double xSpeed, ySpeed;
  private final NoteAlignmentFilter filter = new NoteAlignmentFilter();

  public MoveToNoteCommand(DriveSubsystem drive, double roughRobotAngle,
      DriveInputSupplier inputs) {
    super(drive);
    this.inputSupplier = inputs;
    this.drive = drive;
    Point noteUnitVector = new Point(Math.cos(roughRobotAngle), Math.sin(roughRobotAngle));
    xSpeed = SPEED * noteUnitVector.x;
    ySpeed = SPEED * noteUnitVector.y;
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
    input = new DriveInput(inputSupplier.getDriveInput());
    input.setRotation(0.0);
    input.setXSpeed(xSpeed);
    input.setYSpeed(ySpeed);
    drive.updateInputs(filter.process(input));
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void initialize() {
    UserPolicy.getInstance().setAligningToNote(true);
  }
}
