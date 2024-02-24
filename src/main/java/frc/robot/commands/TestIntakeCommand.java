package frc.robot.commands;

import frc.robot.subsystems.intake.IntakeSubsystem;
import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.subsystems.intake.IntakeInput;

public class TestIntakeCommand extends EntechCommand {

  private IntakeSubsystem intake = new IntakeSubsystem();
  private IntakeInput intakeI = new IntakeInput();
  private StoppingCounter stopCounter = new StoppingCounter(getClass().getSimpleName(), 100);

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

  public void execute() {
    isFinished(stopCounter.isFinished(intake.getOutputs().isActive()));
  }

  public void end() {
    intakeI.setActivate(false);
    intake.updateInputs(intakeI);
  }

  public boolean isFinished(boolean isFinished) {
    return isFinished;
  }
}
