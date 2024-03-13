package frc.robot.commands;

import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.pivot.PivotSubsystem;

public class PivotUpCommand extends EntechCommand {

  private PivotInput input = new PivotInput();
  private final PivotSubsystem pSubsystem;
  private StoppingCounter counter =
      new StoppingCounter(RobotConstants.TEST_CONSTANTS.STANDARD_TEST_LENGTH);

  public PivotUpCommand(PivotSubsystem subsystem) {
    super(subsystem);
    this.pSubsystem = subsystem;
  }

  @Override
  public void initialize() {
    counter.reset();
    input.setActivate(true);
    input.setRequestedPosition(RobotConstants.PIVOT.SHOOT_AMP_POSITION_DEG);
    pSubsystem.updateInputs(input);
  }

  @Override
  public void end(boolean interupted) {
    counter.reset();
    input.setActivate(true);
    input.setRequestedPosition(0.0);
    pSubsystem.updateInputs(input);
  }
}
