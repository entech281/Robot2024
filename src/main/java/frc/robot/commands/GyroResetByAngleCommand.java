package frc.robot.commands;

import java.util.Optional;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import entech.commands.EntechCommand;
import frc.robot.io.RobotIO;
import frc.robot.processors.OdometryProcessor;
import frc.robot.subsystems.navx.NavXSubsystem;

public class GyroResetByAngleCommand extends EntechCommand {
  private final Runnable reset;
  private final Runnable set;
  private final Runnable correctOdometry;
  private final double angle;
  private double angleForOdometry = 0.0;

  public GyroResetByAngleCommand(NavXSubsystem navx, OdometryProcessor odometry,
      double operatorForwardAngleOffset) {
    angle = operatorForwardAngleOffset;
    reset = navx::zeroYaw;
    set = () -> navx
        .setAngleAdjustment(angle + RobotIO.getInstance().getNavXOutput().getAngleAdjustment());
    Optional<Alliance> teamOpt = DriverStation.getAlliance();
    if (teamOpt.isPresent()) {
      angleForOdometry = teamOpt.get() == Alliance.Blue ? 0.0 : 180.0;
    }
    angleForOdometry += angle;
    correctOdometry = () -> {
      Pose2d pose = new Pose2d(odometry.getEstimatedPose().getTranslation(),
          Rotation2d.fromDegrees(angleForOdometry));
      odometry.resetOdometry(pose);
    };
  }

  @Override
  public void initialize() {
    reset.run();
    correctOdometry.run();
    set.run();
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
