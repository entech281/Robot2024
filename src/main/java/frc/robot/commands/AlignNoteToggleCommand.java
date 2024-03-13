package frc.robot.commands;

import entech.commands.EntechCommand;
import frc.robot.operation.UserPolicy;

public class AlignNoteToggleCommand extends EntechCommand {
  @Override
  public void initialize() {
    UserPolicy.getInstance().setAligningToNote(true);
  }

  @Override
  public void end(boolean interrupted) {
    UserPolicy.getInstance().setAligningToNote(false);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
