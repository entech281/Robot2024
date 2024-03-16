package frc.robot.processors.filters;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.drive.DriveInput;

public class HoldYawFilter implements DriveFilterI {
  private final PIDController controller = new PIDController(0.0075, 0, 0.0);
  private Rotation2d holdAngle;

  public HoldYawFilter() {
    controller.enableContinuousInput(0, 360);
  }

  @Override
  public DriveInput process(DriveInput input) {
    DriveInput filteredInput = new DriveInput(input);

    if (input.getRotation() != 0.0) {
      holdAngle = input.getLatestOdometryPose().getRotation();
    } else if (holdAngle != null) {
      filteredInput.setRotation(controller.calculate(
          input.getLatestOdometryPose().getRotation().getDegrees(), holdAngle.getDegrees()));
    }

    return filteredInput;
  }
}
