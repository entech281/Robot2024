package frc.robot.commands.test;

import frc.entech.commands.EntechCommand;
import frc.entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.subsystems.intake.IntakeInput;
import frc.robot.subsystems.intake.IntakeSubsystem;

public class TestIntakeCommand extends EntechCommand {

  private final IntakeSubsystem intake;
  private IntakeInput intakeI = new IntakeInput();
  private StoppingCounter stopCounter =
      new StoppingCounter(RobotConstants.TEST_CONSTANTS.STANDARD_TEST_LENGTH);

  public TestIntakeCommand(IntakeSubsystem intakeSubsystem) {
    super(intakeSubsystem);
    intake = intakeSubsystem;
  }

  @Override
  public void initialize() {
    stopCounter.reset();
    intakeI.setActivate(true);
    intakeI.setSpeed(0.15);
    intake.updateInputs(intakeI);
  }

  @Override
  public void end(boolean interupted) {
    intakeI.setActivate(false);
    intake.updateInputs(intakeI);
  }

  @Override
  public boolean isFinished() {
    return stopCounter.isFinished(intake.getOutputs().isActive());
  }
}
