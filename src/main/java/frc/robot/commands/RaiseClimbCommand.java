package frc.robot.commands;

import entech.commands.EntechCommand;
import frc.robot.subsystems.climb.ClimbInput;
import frc.robot.subsystems.climb.ClimbSubsystem;

public class RaiseClimbCommand extends EntechCommand {

  private ClimbInput cInput = new ClimbInput();
  private ClimbSubsystem cSubsystem = new ClimbSubsystem();

  private double POSITION = 5;

  public RaiseClimbCommand(ClimbSubsystem cSubsystem) {
    super(cSubsystem);
    this.cSubsystem = cSubsystem;
  }

  @Override
  public void initialize() {
    cInput.setActivate(true);
    cInput.setBrakeModeEnabled(true);
    cInput.setCurrentAngle(0);
    cInput.setRequestedPosition(POSITION);
    cSubsystem.updateInputs(cInput);
  }

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }

}
