package frc.robot.processors.filters;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.OI.UserPolicy;
import frc.robot.subsystems.drive.DriveInput;

public class AutoYawFilter implements DriveFilterI {
    private final PIDController controller = new PIDController(0.03, 0, 0.0);

    @Override
    public DriveInput process(DriveInput input) {
        DriveInput processedInput = new DriveInput();

        processedInput.gyroAngle = input.gyroAngle;
        processedInput.latestOdometryPose = input.latestOdometryPose;

        processedInput.xSpeed = input.xSpeed;
        processedInput.ySpeed = input.ySpeed;

        double calculatedRot = controller.calculate(input.latestOdometryPose.getRotation().getDegrees(), calculateTargetAngle(input.latestOdometryPose).getDegrees());

        if (Math.abs(input.latestOdometryPose.getRotation().getDegrees() - calculateTargetAngle(input.latestOdometryPose).getDegrees()) < 2) {
            calculatedRot = 0.0;
        }

        processedInput.rot = UserPolicy.getInstance().isTwistable() ? input.rot : calculatedRot;

        Logger.recordOutput("TargetPoseDirection", new Pose2d(input.latestOdometryPose.getTranslation(), calculateTargetAngle(input.latestOdometryPose)));

        return processedInput;
    }


    private Rotation2d calculateTargetAngle(Pose2d currentPose) {
        double xDist = currentPose.getX() - UserPolicy.getInstance().getTargetPose().getX();
        double yDist = currentPose.getY() - UserPolicy.getInstance().getTargetPose().getY();

        return Rotation2d.fromRadians(Math.atan2(yDist, xDist));
    }
}
