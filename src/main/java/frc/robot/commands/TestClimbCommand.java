package frc.robot.commands;

import entech.commands.EntechCommand;
import entech.util.IsWithinTolerance;
import entech.util.PeriodicLoopsPerSecond;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.subsystems.climb.ClimbInput;
import frc.robot.subsystems.climb.ClimbSubsystem;

public class TestClimbCommand extends EntechCommand {

  private ClimbInput input = new ClimbInput();
  private ClimbSubsystem cSubsystem = new ClimbSubsystem();
  private StoppingCounter counter = new StoppingCounter(getClass().getSimpleName(),
      PeriodicLoopsPerSecond.getLoopsPerSecond(RobotConstants.TEST_CONSTANTS.STANDARD_TEST_LENGTH));

  public TestClimbCommand(ClimbSubsystem subsystem) {
    super(subsystem);
    this.cSubsystem = subsystem;
  }

  @Override
  public void initialize() {
    counter.reset();
    input.setActivate(true);
    input.setRequestedPosition(RobotConstants.TEST_CONSTANTS.CLIMB.TEST_POSITION_IN);
    input.setBrakeModeEnabled(false);
    cSubsystem.updateInputs(input);
  }

  @Override
  public void end(boolean interupted) {
    input.setActivate(false);
    input.setRequestedPosition(0);
    cSubsystem.updateInputs(input);
  }

  @Override
  public boolean isFinished() {
    return counter.isFinished(
        IsWithinTolerance.isWithinTolerance(RobotConstants.TEST_CONSTANTS.CLIMB.TEST_TOLERANCE_IN,
            cSubsystem.getOutputs().getCurrentPosition(),
            RobotConstants.TEST_CONSTANTS.CLIMB.TEST_POSITION_IN));
  }
}
