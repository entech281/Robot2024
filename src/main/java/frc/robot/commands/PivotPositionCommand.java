package frc.robot.commands;

import entech.commands.EntechCommand;
import entech.util.EntechUtils;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.pivot.PivotSubsystem;

public class PivotPositionCommand extends EntechCommand {

  private PivotInput input = new PivotInput();
  private final PivotSubsystem pSubsystem;
  private StoppingCounter counter =
      new StoppingCounter(RobotConstants.TEST_CONSTANTS.STANDARD_TEST_LENGTH);

  public PivotPositionCommand(PivotSubsystem subsystem) {
    super(subsystem);
    this.pSubsystem = subsystem;
  }

  @Override
  public void initialize() {
    counter.reset();
    input.setActivate(true);
    input.setRequestedPosition(RobotConstants.TEST_CONSTANTS.PIVOT.TEST_POSITION_DEG);
    pSubsystem.updateInputs(input);
  }

  @Override
  public void end(boolean interupted) {}

  @Override
  public boolean isFinished() {
    return counter.isFinished(
        EntechUtils.isWithinTolerance(RobotConstants.TEST_CONSTANTS.PIVOT.TEST_TOLERANCE_DEG,
            pSubsystem.getOutputs().getCurrentPosition(),
            RobotConstants.TEST_CONSTANTS.PIVOT.TEST_POSITION_DEG));
  }
}
