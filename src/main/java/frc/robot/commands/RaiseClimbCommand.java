package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import entech.commands.EntechCommand;
import entech.util.EntechUtils;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.climb.ClimbInput;
import frc.robot.subsystems.climb.ClimbSubsystem;

public class RaiseClimbCommand extends EntechCommand {

  private ClimbInput cInput = new ClimbInput();
  private ClimbSubsystem cSubsystem;

  private Trigger freeze;

  public RaiseClimbCommand(ClimbSubsystem cSubsystem, Trigger freeze) {
    super(cSubsystem);
    this.cSubsystem = cSubsystem;
    this.freeze = freeze;
  }

  @Override
  public void initialize() {

    cInput.setActivate(true);
    cInput.setBothSpeed(1);
    cInput.setFeeze(!freeze.getAsBoolean());
    cSubsystem.updateInputs(cInput);
  }

  @Override
  public void execute() {
    cInput.setFeeze(!freeze.getAsBoolean());
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
        RobotConstants.CLIMB.CLIMB_EXTENDED);
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }

}
