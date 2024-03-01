package frc.robot.commands.test;

import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.subsystems.intake.IntakeInput;
import frc.robot.subsystems.intake.IntakeSubsystem;

public class TestIntakeCommand extends EntechCommand {

  private IntakeSubsystem intake = new IntakeSubsystem();
  private IntakeInput intakeI = new IntakeInput();
  private StoppingCounter stopCounter = new StoppingCounter(getClass().getSimpleName(),
      RobotConstants.TEST_CONSTANTS.STANDARD_TEST_LENGTH);

  public TestIntakeCommand(IntakeSubsystem intakeSubsystem) {
    super(intakeSubsystem);
  }

  @Override
  public void initialize() {
    stopCounter.reset();
    intakeI.setActivate(true);
    intakeI.setBrakeModeEnabled(false);
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
