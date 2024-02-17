package frc.robot.processors.filters;

import frc.robot.subsystems.drive.DriveInput;

public class SquaringFilter implements DriveFilterI {
    @Override
    public DriveInput process(DriveInput input) {
        DriveInput processedInput = new DriveInput();

        processedInput.gyroAngle = input.gyroAngle;
        processedInput.pose = input.pose;

        processedInput.xSpeed = Math.copySign(input.xSpeed * input.xSpeed, input.xSpeed);
        processedInput.ySpeed = Math.copySign(input.ySpeed * input.ySpeed, input.ySpeed);
        processedInput.rot = Math.copySign(input.rot * input.rot, input.rot);

        return processedInput;
    }
}
