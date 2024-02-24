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
  private StoppingCounter counter =
      new StoppingCounter(getClass().getSimpleName(), PeriodicLoopsPerSecond.getLoopsPerSecond(1));

  public TestClimbCommand(ClimbSubsystem subsystem) {
    super(subsystem);
    this.cSubsystem = subsystem;
  }

  public void initialize() {
    counter.reset();
    input.setActivate(true);
    input.setRequestedPosition(RobotConstants.CLIMB.TEST_POSITION_IN);
    input.setBrakeModeEnabled(false);
    cSubsystem.updateInputs(input);
  }

  public void execute() {
    isFinished(counter
        .isFinished(IsWithinTolerance.isWithinTolerance(RobotConstants.CLIMB.TEST_TOLERANCE_IN,
            cSubsystem.getOutputs().getCurrentPosition(), RobotConstants.CLIMB.TEST_POSITION_IN)));
  }

  public void end() {
    input.setActivate(false);
    input.setRequestedPosition(0);
    cSubsystem.updateInputs(input);
  }

  public boolean isFinished(boolean isFinished) {
    return isFinished;
  }
}
