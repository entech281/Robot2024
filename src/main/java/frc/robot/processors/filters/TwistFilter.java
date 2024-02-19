package frc.robot.processors.filters;

import frc.robot.oi.UserPolicy;
import frc.robot.subsystems.drive.DriveInput;

public class TwistFilter implements DriveFilterI {
  @Override
  public DriveInput process(DriveInput input) {
    DriveInput processedInput = new DriveInput();
    processedInput.gyroAngle = input.gyroAngle;
    processedInput.latestOdometryPose = input.latestOdometryPose;
    processedInput.xSpeed = input.xSpeed;
    processedInput.ySpeed = input.ySpeed;

    processedInput.rot = UserPolicy.getInstance().isTwistable() ? input.rot : 0.0;

    return processedInput;
  }
}
