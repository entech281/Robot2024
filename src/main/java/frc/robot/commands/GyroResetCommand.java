package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import entech.commands.EntechCommand;
import frc.robot.processors.OdometryProcessor;
import frc.robot.subsystems.navx.NavXSubsystem;

public class GyroResetCommand extends EntechCommand {
  private final Runnable reset;
  private final Runnable correctOdometry;

  public GyroResetCommand(NavXSubsystem navx, OdometryProcessor odometry) {
    reset = navx::zeroYaw;
    correctOdometry = () -> {
      Pose2d pose =
          new Pose2d(odometry.getEstimatedPose().getTranslation(), Rotation2d.fromDegrees(0));
      odometry.resetOdometry(pose);
    };
  }

  @Override
  public void initialize() {
    reset.run();
    correctOdometry.run();
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
