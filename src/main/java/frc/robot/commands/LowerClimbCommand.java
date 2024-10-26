package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.entech.commands.EntechCommand;
import frc.entech.util.EntechUtils;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.climb.ClimbInput;
import frc.robot.subsystems.climb.ClimbSubsystem;

public class LowerClimbCommand extends EntechCommand {

  private ClimbInput cInput = new ClimbInput();
  private ClimbSubsystem cSubsystem;

  private Trigger freeze;

  public LowerClimbCommand(ClimbSubsystem cSubsystem, Trigger freeze) {
    super(cSubsystem);
    this.cSubsystem = cSubsystem;
    this.freeze = freeze;
  }

  @Override
  public void initialize() {
    cInput.setActivate(true);
    cInput.setBrakeModeEnabled(true);
    cInput.setFreeze(!freeze.getAsBoolean());
    cInput.setBothSpeed(-1);
    cSubsystem.updateInputs(cInput);
  }

  @Override
  public void execute() {
    cInput.setFreeze(!freeze.getAsBoolean());
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
