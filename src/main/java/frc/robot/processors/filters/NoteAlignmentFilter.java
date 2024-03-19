package frc.robot.processors.filters;

import java.util.Optional;
import org.opencv.core.Point;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.io.RobotIO;
import frc.robot.operation.UserPolicy;
import frc.robot.subsystems.drive.DriveInput;
import frc.robot.subsystems.noteDetector.NoteDetectorOutput;

public class NoteAlignmentFilter implements DriveFilterI {
  private final PIDController controller = new PIDController(0.0204, 0, 0.0);
  private double driveSpeed;

  @Override
  public DriveInput process(DriveInput input) {
    if (UserPolicy.getInstance().isAligningToNote()) {
      NoteDetectorOutput no = RobotIO.getInstance().getNoteDetectorOutput();
      Optional<Alliance> teamOpt = DriverStation.getAlliance();
      Alliance team = teamOpt.isPresent() ? teamOpt.get() : Alliance.Blue;
      return process(input, no, team);
    }
    return new DriveInput(input);
  }

  public DriveInput process(DriveInput input, NoteDetectorOutput no, Alliance team) {
    double targetYaw = 0;
    if (!no.getMidpoint().isEmpty()) {
      targetYaw = no.getYaw();
      double noteAngle = input.getLatestOdometryPose().getRotation().getRadians()
          - Units.degreesToRadians(targetYaw);
      double driverInputAngle = Math.toDegrees(Math.atan2(input.getYSpeed(), input.getXSpeed()))
          * (Alliance.Blue == team ? 1 : 1);
      DriveInput adjustedDriveInput = new DriveInput(input);
      if (Math.abs(input.getLatestOdometryPose().getRotation().getDegrees()) - noteAngle >= 1.5
          && !UserPolicy.getInstance().isTwistable()) {
        adjustedDriveInput.setRotation(
            controller.calculate(input.getLatestOdometryPose().getRotation().getDegrees(),
                Units.radiansToDegrees(noteAngle)));
      }
      driveSpeed = Math.sqrt(Math.pow(input.getXSpeed(), 2) + Math.pow(input.getYSpeed(), 2)) * 0.5;
      double diff = Math.abs(driverInputAngle - Units.radiansToDegrees(noteAngle));
      if (diff > 180) {
        diff = 360 - diff;
      }
      if (diff > 135) {
        driveSpeed = -driveSpeed;
      }
      Point noteUnitVector = new Point(Math.cos(noteAngle), Math.sin(noteAngle));
      adjustedDriveInput.setXSpeed(driveSpeed * noteUnitVector.x);
      adjustedDriveInput.setYSpeed(driveSpeed * noteUnitVector.y);
      return adjustedDriveInput;
    }
    return new DriveInput(input);
  }
}
