package frc.robot.commands;

import entech.commands.EntechCommand;
import entech.util.EntechUtils;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.climb.ClimbInput;
import frc.robot.subsystems.climb.ClimbSubsystem;

public class LowerClimbCommand extends EntechCommand {

  private ClimbInput cInput = new ClimbInput();
  private ClimbSubsystem cSubsystem;

  public static final double POSITION = 0;

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
    return EntechUtils.isWithinTolerance(1,
        RobotIO.getInstance().getClimbOutput().getCurrentPosition(), POSITION);
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }

}
