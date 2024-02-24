package frc.robot.commands;

import frc.robot.subsystems.intake.IntakeSubsystem;
import entech.commands.EntechCommand;
import entech.util.PeriodicLoopsPerSecond;
import entech.util.StoppingCounter;
import frc.robot.subsystems.intake.IntakeInput;

public class TestIntakeCommand extends EntechCommand {

  private IntakeSubsystem intake = new IntakeSubsystem();
  private IntakeInput intakeI = new IntakeInput();
  private StoppingCounter stopCounter =
      new StoppingCounter(getClass().getSimpleName(), PeriodicLoopsPerSecond.getLoopsPerSecond(1));

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
