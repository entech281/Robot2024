package frc.robot.processors.filters;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.drive.DriveInput;

public class AutoYawFilter implements DriveFilterI {
    @Override
    public DriveInput process(DriveInput input) {
        DriveInput processedInput = new DriveInput();

        processedInput.gyroAngle = input.gyroAngle;
        processedInput.latestOdometryPose = input.latestOdometryPose;

        processedInput.xSpeed = input.xSpeed;
        processedInput.ySpeed = input.ySpeed;
        processedInput.rot = input.rot;

        Logger.recordOutput("TargetPoseDirection", new Pose2d(input.latestOdometryPose.getTranslation(), calculateTargetAngle(input.latestOdometryPose)));

        return processedInput;
    }


    private Rotation2d calculateTargetAngle(Pose2d currentPose) {
        double xDist = Math.abs()

        return Rotation2d.fromDegrees(0);
    }
}
