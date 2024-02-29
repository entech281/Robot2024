package frc.robot.commands.testCommands;

import entech.commands.EntechCommand;
import entech.util.IsWithinTolerance;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.pivot.PivotSubsystem;

public class TestPivotCommand extends EntechCommand {

  private PivotInput input = new PivotInput();
  private PivotSubsystem pSubsystem = new PivotSubsystem();
  private StoppingCounter counter = new StoppingCounter(getClass().getSimpleName(),
      RobotConstants.TEST_CONSTANTS.STANDARD_TEST_LENGTH);

  public TestPivotCommand(PivotSubsystem subsystem) {
    super(subsystem);
    this.pSubsystem = subsystem;
  }

  @Override
  public void initialize() {
    counter.reset();
    input.setActivate(true);
    input.setRequestedPosition(RobotConstants.TEST_CONSTANTS.PIVOT.TEST_POSITION_DEG);
    input.setBrakeModeEnabled(false);
    pSubsystem.updateInputs(input);
  }

  @Override
  public void end(boolean interupted) {
    input.setRequestedPosition(0);
    input.setActivate(false);
    pSubsystem.updateInputs(input);
  }

  @Override
  public boolean isFinished() {
    return counter.isFinished(
        IsWithinTolerance.isWithinTolerance(RobotConstants.TEST_CONSTANTS.PIVOT.TEST_TOLERANCE_DEG,
            pSubsystem.getOutputs().getCurrentPosition(),
            RobotConstants.TEST_CONSTANTS.PIVOT.TEST_POSITION_DEG));
  }
}
