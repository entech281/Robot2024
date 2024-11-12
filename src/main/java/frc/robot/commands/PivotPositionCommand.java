package frc.robot.commands;

import frc.entech.commands.EntechCommand;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.pivot.PivotSubsystem;

public class PivotPositionCommand extends EntechCommand {

  private PivotInput input = new PivotInput();
  private final PivotSubsystem pSubsystem;
  private final double position;

  public PivotPositionCommand(PivotSubsystem subsystem, double position) {
    super(subsystem);
    this.pSubsystem = subsystem;
    this.position = position;
  }

  @Override
  public void initialize() {
    input.setActivate(true);
    input.setRequestedPosition(position);
    pSubsystem.updateInputs(input);
  }

  @Override
  public void end(boolean interupted) {
    input.setActivate(false);
    input.setRequestedPosition(0.0);
    pSubsystem.updateInputs(input);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
