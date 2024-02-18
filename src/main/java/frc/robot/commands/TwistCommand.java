package frc.robot.commands;

import entech.commands.EntechCommand;
import frc.robot.OI.UserPolicy;

public class TwistCommand extends EntechCommand {
  @Override
  public void initialize() {
    UserPolicy.getInstance().setIsTwistable(true);
  }

  @Override
  public void end(boolean interrupted) {
    UserPolicy.getInstance().setIsTwistable(false);
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
