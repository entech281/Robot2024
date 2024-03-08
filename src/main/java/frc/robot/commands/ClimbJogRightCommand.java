package frc.robot.commands;

import entech.commands.EntechCommand;
import frc.robot.RobotConstants;
import frc.robot.subsystems.climb.ClimbInput;
import frc.robot.subsystems.climb.ClimbSubsystem;

public class ClimbJogRightCommand extends EntechCommand {

  private final ClimbInput cInput = new ClimbInput();
  private final ClimbSubsystem cSubsystem;

  public ClimbJogRightCommand(ClimbSubsystem cSubsystem) {
    super(cSubsystem);
    this.cSubsystem = cSubsystem;
  }

  @Override
  public void initialize() {
    cInput.setActivate(true);
    cInput.setBrakeModeEnabled(true);
    cInput.setSpeedRight(-0.2);
    cSubsystem.updateInputs(cInput);
    cSubsystem.setPosition(RobotConstants.CLIMB.CLIMB_EXTENDED);
  }

  @Override
  public void execute() {
    cInput.setActivate(true);
    cInput.setSpeedRight(-0.2);
    cSubsystem.updateInputs(cInput);
  }

  @Override
  public void end(boolean interrupted) {
    cInput.setActivate(false);
    cInput.setBothSpeed(0);
    cSubsystem.updateInputs(cInput);
    cSubsystem.setPosition(RobotConstants.CLIMB.CLIMB_RETRACTED);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }

}
