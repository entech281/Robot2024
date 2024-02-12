package frc.robot.processors.filters;

import frc.robot.OI.UserPolicy;
import frc.robot.subsystems.DriveInput;

public class TwistFilter implements DriveFilterI {
    @Override
    public DriveInput process(DriveInput input) {
        DriveInput processedInput = new DriveInput();
        processedInput.gyroAngle = input.gyroAngle;
        processedInput.pose = input.pose;
        processedInput.xSpeed = input.xSpeed;
        processedInput.ySpeed = input.ySpeed;

        processedInput.rot = UserPolicy.getInstance().isTwistable() ? input.rot : 0.0;
        return processedInput;
    }
}
