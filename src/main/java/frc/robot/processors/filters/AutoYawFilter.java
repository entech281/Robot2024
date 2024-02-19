package frc.robot.processors.filters;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.operation.UserPolicy;
import frc.robot.subsystems.drive.DriveInput;

public class AutoYawFilter implements DriveFilterI {
  private final PIDController controller = new PIDController(0.03, 0, 0.0);

  @Override
  public DriveInput process(DriveInput input) {
    DriveInput processedInput = new DriveInput(input);

    double calculatedRot = controller.calculate(input.getLatestOdometryPose().getRotation().getDegrees(), calculateTargetAngle(input.getLatestOdometryPose()).getDegrees());

    if (Math.abs(input.getLatestOdometryPose().getRotation().getDegrees() - calculateTargetAngle(input.getLatestOdometryPose()).getDegrees()) < 2) {
        calculatedRot = 0.0;
    }

    processedInput.setRotation(UserPolicy.getInstance().isTwistable() ? input.getRotation() : calculatedRot);

    Logger.recordOutput("TargetPoseDirection", new Pose2d(input.getLatestOdometryPose().getTranslation(), calculateTargetAngle(input.getLatestOdometryPose())));

    return processedInput;
  }


  private Rotation2d calculateTargetAngle(Pose2d currentPose) {
    double xDist = currentPose.getX() - UserPolicy.getInstance().getTargetPose().getX();
    double yDist = currentPose.getY() - UserPolicy.getInstance().getTargetPose().getY();

    return Rotation2d.fromRadians(Math.atan2(yDist, xDist));
  }
}
