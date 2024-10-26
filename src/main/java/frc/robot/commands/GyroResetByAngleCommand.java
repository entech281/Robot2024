package frc.robot.commands;

import java.util.Optional;
import com.pathplanner.lib.commands.PathPlannerAuto;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.entech.commands.EntechCommand;
import frc.robot.io.RobotIO;
import frc.robot.processors.OdometryProcessor;
import frc.robot.subsystems.navx.NavXSubsystem;

public class GyroResetByAngleCommand extends EntechCommand {
  private final Runnable reset;
  private final Runnable set;
  private final Runnable correctOdometry;
  private final double angle;

  public GyroResetByAngleCommand(NavXSubsystem navx, OdometryProcessor odometry, String auto) {
    angle = PathPlannerAuto.getStaringPoseFromAutoFile(auto).getRotation().getDegrees();
    reset = navx::zeroYaw;
    Optional<Alliance> teamOpt = DriverStation.getAlliance();
    if (teamOpt.isPresent()) {
      if (teamOpt.get() == Alliance.Blue) {
        set = () -> navx
            .setAngleAdjustment(RobotIO.getInstance().getNavXOutput().getAngleAdjustment() + angle);
      } else {
        set = () -> navx
            .setAngleAdjustment(RobotIO.getInstance().getNavXOutput().getAngleAdjustment() - angle);
      }
    } else {
      set = () -> navx
          .setAngleAdjustment(RobotIO.getInstance().getNavXOutput().getAngleAdjustment() + angle);
    }
    correctOdometry = () -> {
      Pose2d pose =
          new Pose2d(odometry.getEstimatedPose().getTranslation(), Rotation2d.fromDegrees(angle));
      odometry.resetOdometry(pose, Rotation2d.fromDegrees(0.0));
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

