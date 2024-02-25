package frc.robot.processors.filters;

import org.opencv.core.Point;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import frc.robot.io.RobotIO;
import frc.robot.operation.UserPolicy;
import frc.robot.subsystems.drive.DriveInput;
import frc.robot.subsystems.note_detector.NoteDetectorOutput;

public class NoteAlignmentFilter implements DriveFilterI {
  private final PIDController controller = new PIDController(0.02, 0, 0.0);
  private double driveSpeed;

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
      double noteAngle = input.getLatestOdometryPose().getRotation().getRadians()
          - Units.degreesToRadians(targetYaw);
      driveSpeed = Math.sqrt(Math.pow(input.getXSpeed(), 2) + Math.pow(input.getYSpeed(), 2));
      Point noteUnitVector = new Point(Math.cos(noteAngle), Math.sin(noteAngle));
      DriveInput adjustedDriveInput = new DriveInput(input);
      adjustedDriveInput.setXSpeed(driveSpeed * noteUnitVector.x);
      adjustedDriveInput.setYSpeed(driveSpeed * noteUnitVector.y);
      if (!(Math.abs(input.getLatestOdometryPose().getRotation().getDegrees()) - targetYaw < 2)
          && !UserPolicy.getInstance().isTwistable()) {
        adjustedDriveInput.setRotation(
            controller.calculate(input.getLatestOdometryPose().getRotation().getDegrees(),
                input.getLatestOdometryPose().getRotation().getDegrees() - targetYaw));
      }

      return adjustedDriveInput;
    } else {
      return new DriveInput(input);
    }
  }
}
