package frc.robot.commands;

import java.util.Optional;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import entech.commands.EntechCommand;
import frc.robot.processors.OdometryProcessor;

public class ResetOdometryCommand extends EntechCommand {
  private final OdometryProcessor odometry;

  public ResetOdometryCommand(OdometryProcessor odometry) {
    this.odometry = odometry;
  }

  @Override
  public void initialize() {
    Optional<Alliance> team = DriverStation.getAlliance();
    if (team.isPresent() && team.get() == Alliance.Blue) {
      odometry.resetOdometry(new Pose2d(1.38, 5.53, odometry.getEstimatedPose().getRotation()));
      return;
    }
    odometry.resetOdometry(new Pose2d(15.17, 5.53, odometry.getEstimatedPose().getRotation()));
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
