package frc.robot.processors.filters;

import edu.wpi.first.math.MathUtil;
import frc.robot.RobotConstants;
import frc.robot.subsystems.DriveInput;

public class MaxConstraintFilter implements DriveFilterI {
    private static final double MAX_SPEED_PERCENT = 1;

    @Override
    public DriveInput process(DriveInput input) {
        DriveInput processedInput = new DriveInput();

        processedInput.gyroAngle = input.gyroAngle;
        processedInput.pose = input.pose;

        processedInput.xSpeed = MathUtil.applyDeadband(MathUtil.clamp(input.xSpeed, -MAX_SPEED_PERCENT, MAX_SPEED_PERCENT),
                RobotConstants.Ports.CONTROLLER.JOYSTICK_AXIS_THRESHOLD);
        processedInput.ySpeed = MathUtil.applyDeadband(MathUtil.clamp(input.ySpeed, -MAX_SPEED_PERCENT, MAX_SPEED_PERCENT),
                RobotConstants.Ports.CONTROLLER.JOYSTICK_AXIS_THRESHOLD);
        processedInput.rot = MathUtil.applyDeadband(MathUtil.clamp(input.rot, -MAX_SPEED_PERCENT, MAX_SPEED_PERCENT),
                    RobotConstants.Ports.CONTROLLER.JOYSTICK_AXIS_THRESHOLD);

        return processedInput;
    }
    
}
