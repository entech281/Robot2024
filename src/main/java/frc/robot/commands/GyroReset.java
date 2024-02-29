package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import entech.commands.EntechCommand;
import frc.robot.processors.OdometryProcessor;
import frc.robot.subsystems.navx.NavXSubsystem;

public class GyroReset extends EntechCommand {
  private final Runnable reset;
  private final Runnable correctOdomtry;

  public GyroReset(NavXSubsystem navx, OdometryProcessor odometry) {
    reset = navx::zeroYaw;
    correctOdomtry = () -> odometry.resetOdometry(
        new Pose2d(odometry.getEstimatedPose().getTranslation(), Rotation2d.fromDegrees(0)));
  }

  @Override
  public void initialize() {
    reset.run();
    correctOdomtry.run();
  }

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
