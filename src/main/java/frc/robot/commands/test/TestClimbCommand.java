package frc.robot.commands.test;

import frc.entech.commands.EntechCommand;
import frc.entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.subsystems.climb.ClimbInput;
import frc.robot.subsystems.climb.ClimbSubsystem;

public class TestClimbCommand extends EntechCommand {
  private ClimbInput input = new ClimbInput();
  private final ClimbSubsystem cSubsystem;
  private StoppingCounter counter =
      new StoppingCounter(RobotConstants.TEST_CONSTANTS.STANDARD_TEST_LENGTH);

  private int stage = 0;

  public TestClimbCommand(ClimbSubsystem subsystem) {
    super(subsystem);
    this.cSubsystem = subsystem;
  }

  @Override
  public void initialize() {
    stage = 0;
    counter.reset();
    input.setActivate(true);
    input.setBothSpeed(-0.5);
    cSubsystem.updateInputs(input);
  }

  @Override
  public void end(boolean interupted) {
    input.setActivate(false);
    input.setBothSpeed(0);
    cSubsystem.updateInputs(input);
  }

  @Override
  public void execute() {
    switch (stage) {
      case 0:
        input.setActivate(true);
        input.setBothSpeed(0.5);
        cSubsystem.updateInputs(input);
        if (cSubsystem.getOutputs().getCurrentPosition() >= RobotConstants.CLIMB.CLIMB_EXTENDED) {
          stage++;
        }
        break;
      case 1:
        input.setActivate(true);
        input.setBothSpeed(-0.5);
        cSubsystem.updateInputs(input);
        if (cSubsystem.getOutputs().getCurrentPosition() <= RobotConstants.CLIMB.CLIMB_RETRACTED) {
          stage++;
        }
        break;
      default:
        break;
    }
  }

  @Override
  public boolean isFinished() {
    return stage == 2;
  }
}
