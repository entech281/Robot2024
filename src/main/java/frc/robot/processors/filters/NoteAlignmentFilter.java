package frc.robot.processors.filters;

import org.opencv.core.Point;
import frc.robot.io.RobotIO;
import frc.robot.operation.UserPolicy;
import frc.robot.subsystems.drive.DriveInput;
import frc.robot.subsystems.note_detector.NoteDetectorOutput;

public class NoteAlignmentFilter implements DriveFilterI {

  private double driveSpeed;

  public double toRadians(double degrees) {
    return degrees * Math.PI / 180;
  }

  @Override
  public DriveInput process(DriveInput input) {
    if (UserPolicy.getInstance().isAligningToNote()) {
      NoteDetectorOutput no = RobotIO.getInstance().getNoteDetectorOutput();
      return process(input, no);
    }
    return new DriveInput(input);
  }

  public DriveInput process(DriveInput input, NoteDetectorOutput no) {
    double targetYaw = 0;
    if (!no.getMidpoint().isEmpty()) {
      targetYaw = no.getYaw();
      double noteAngle =
          input.getLatestOdometryPose().getRotation().getRadians() - toRadians(targetYaw);
      driveSpeed = Math.sqrt(Math.pow(input.getXSpeed(), 2) + Math.pow(input.getYSpeed(), 2));
      Point noteUnitVector = new Point(Math.cos(noteAngle), -Math.sin(noteAngle));
      DriveInput adjustedDriveInput = new DriveInput(input);
      adjustedDriveInput.setXSpeed(driveSpeed * noteUnitVector.x);
      adjustedDriveInput.setYSpeed(driveSpeed * noteUnitVector.y);

      return adjustedDriveInput;
    } else {
      return input;
    }
  }
}
