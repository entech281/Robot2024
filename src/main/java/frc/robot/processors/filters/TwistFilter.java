package frc.robot.processors.filters;

import frc.robot.operation.UserPolicy;
import frc.robot.subsystems.drive.DriveInput;

public class TwistFilter implements DriveFilterI {
  @Override
  public DriveInput process(DriveInput input) {
    DriveInput processedInput = new DriveInput(input);

    processedInput.setRotation(UserPolicy.getInstance().isTwistable() ? input.getRotation() : 0.0);

    return processedInput;
  }
}
