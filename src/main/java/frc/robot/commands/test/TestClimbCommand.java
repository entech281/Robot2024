package frc.robot.commands.test;

import entech.commands.EntechCommand;
import entech.util.EntechUtils;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.subsystems.climb.ClimbInput;
import frc.robot.subsystems.climb.ClimbSubsystem;

public class TestClimbCommand extends EntechCommand {
  private ClimbInput input = new ClimbInput();
  private final ClimbSubsystem cSubsystem;
  private StoppingCounter counter = new StoppingCounter(getClass().getSimpleName(),
      RobotConstants.TEST_CONSTANTS.STANDARD_TEST_LENGTH);

  public TestClimbCommand(ClimbSubsystem subsystem) {
    super(subsystem);
    this.cSubsystem = subsystem;
  }

  @Override
  public void initialize() {
    counter.reset();
    input.setActivate(true);
    input.setSpeed(-0.5);
    cSubsystem.updateInputs(input);
  }

  @Override
  public void end(boolean interupted) {
    input.setActivate(false);
    input.setSpeed(0);
    cSubsystem.updateInputs(input);
  }

  @Override
  public boolean isFinished() {
    return counter.isFinished(
        EntechUtils.isWithinTolerance(RobotConstants.TEST_CONSTANTS.CLIMB.TEST_TOLERANCE_IN,
            cSubsystem.getOutputs().getCurrentPosition(), RobotConstants.CLIMB.CLIMB_RETRACTED));
  }
}
