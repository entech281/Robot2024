package frc.robot.commands;

import entech.commands.EntechCommand;
import entech.util.EntechUtils;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.climb.ClimbInput;
import frc.robot.subsystems.climb.ClimbSubsystem;

public class LowerClimbCommand extends EntechCommand {

  private ClimbInput cInput = new ClimbInput();
  private ClimbSubsystem cSubsystem;

  private boolean freeze;

  public LowerClimbCommand(ClimbSubsystem cSubsystem, boolean freeze) {
    super(cSubsystem);
    this.cSubsystem = cSubsystem;
    this.freeze = freeze;
  }

  @Override
  public void initialize() {
    cInput.setActivate(true);
    cInput.setBrakeModeEnabled(true);
    cInput.setFeeze(freeze);
    cInput.setRequestedPosition(RobotConstants.CLIMB.CLIMB_RETRACTED);
    cSubsystem.updateInputs(cInput);
  }

  @Override
  public void execute() {
    cInput.setFeeze(freeze);
    cSubsystem.updateInputs(cInput);
  }

  public void end() {
    cInput.setActivate(false);
    cSubsystem.updateInputs(cInput);
  }

  @Override
  public boolean isFinished() {
    return EntechUtils.isWithinTolerance(1,
        RobotIO.getInstance().getClimbOutput().getCurrentPosition(),
        RobotConstants.CLIMB.CLIMB_RETRACTED);
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }

}
