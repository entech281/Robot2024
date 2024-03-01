package frc.robot.commands;

import entech.commands.EntechCommand;
import frc.robot.operation.UserPolicy;

public class AlignNoteToggleCommand extends EntechCommand {
  @Override
  public void initialize() {
    UserPolicy.getInstance().setAligningToNote(!UserPolicy.getInstance().isAligningToNote());
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
