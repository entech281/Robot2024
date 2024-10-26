package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import frc.entech.commands.EntechCommand;
import frc.robot.operation.UserPolicy;

public class SetTargetCommand extends EntechCommand {
  private final Pose2d target;

  public SetTargetCommand(Pose2d target) {
    this.target = target;
  }

  @Override
  public void initialize() {
    UserPolicy.getInstance().setTargetPose(target);
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
