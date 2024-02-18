package frc.robot.processors.filters;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.subsystems.drive.DriveInput;

public class WallCollisionReductionFilter implements DriveFilterI {
@Override
    public DriveInput process(DriveInput input) {
        DriveInput processedInput = new DriveInput();

        processedInput.gyroAngle = input.gyroAngle;
        processedInput.latestOdometryPose = input.latestOdometryPose;

        double xLimit = calculateXLimit(input.latestOdometryPose);
        double yLimit = calculateYLimit(input.latestOdometryPose);


        processedInput.xSpeed = MathUtil.clamp(input.xSpeed, -xLimit, xLimit);
        processedInput.ySpeed = MathUtil.clamp(input.ySpeed, -yLimit, yLimit);
        processedInput.rot = input.rot;

        return processedInput;
    }

    //8.2y
    //16.542x
    //0.59
    //within 0.1 limit 0.2
    //within 1.5 start limiting


    private double calculateXLimit(Pose2d currentPose) {
        if (currentPose.getX() > 16.542 - 3) {
            return Math.pow((16.542 - currentPose.getX()) / 3, 4);
        }

        if (currentPose.getY() < 3) {
            return Math.pow(currentPose.getX() / 3, 4);
        }

        return 1.0;
    }

    private double calculateYLimit(Pose2d currentPose) {
        if (currentPose.getX() > 8.2 - 3) {
            return Math.pow((16.542 - currentPose.getX()) / 3, 4);
        }

        if (currentPose.getY() < 3) {
            return Math.pow(currentPose.getX() / 3, 4);
        }

        return 1.0;
    }
}
