package frc.robot.commands;

import entech.commands.EntechCommand;
import frc.robot.RobotConstants;
import frc.robot.subsystems.climb.ClimbInput;
import frc.robot.subsystems.climb.ClimbSubsystem;

public class ClimbJogStopCommand extends EntechCommand {

  private final ClimbInput cInput = new ClimbInput();
  private final ClimbSubsystem cSubsystem;

  public ClimbJogStopCommand(ClimbSubsystem cSubsystem) {
    super(cSubsystem);
    this.cSubsystem = cSubsystem;
  }

  @Override
  public void initialize() {
    cInput.setActivate(false);
    cInput.setBothSpeed(0);
    cInput.setFreeze(true);
    cSubsystem.updateInputs(cInput);
    cSubsystem.setPosition(RobotConstants.CLIMB.CLIMB_RETRACTED);
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
