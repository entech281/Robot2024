package frc.robot.commands;

import entech.commands.EntechCommand;
import entech.util.IsWithinTolerance;
import frc.robot.subsystems.climb.ClimbInput;
import frc.robot.subsystems.climb.ClimbOutput;
import frc.robot.subsystems.climb.ClimbSubsystem;

public class LowerClimbCommand extends EntechCommand {

  private ClimbInput cInput = new ClimbInput();
  private ClimbOutput cOutput = new ClimbOutput();
  private ClimbSubsystem cSubsystem = new ClimbSubsystem();

  private double POSITION = 0;

  public LowerClimbCommand(ClimbSubsystem cSubsystem) {
    super(cSubsystem);
    this.cSubsystem = cSubsystem;
  }

  @Override
  public void initialize() {
    cInput.setActivate(true);
    cInput.setBrakeModeEnabled(true);
    cInput.setRequestedPosition(POSITION);
    cSubsystem.updateInputs(cInput);
  }

  public void end() {
    cInput.setActivate(false);
    cSubsystem.updateInputs(cInput);
  }

  @Override
  public boolean isFinished() {
    return IsWithinTolerance.isWithinTolerance(1, cOutput.getCurrentPosition(), POSITION);
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }

}
