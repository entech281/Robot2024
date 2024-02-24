package frc.robot.commands;

import entech.commands.EntechCommand;
import entech.util.PeriodicLoopsPerSecond;
import entech.util.StoppingCounter;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.pivot.PivotSubsystem;

public class TestPivotCommand extends EntechCommand {

  private PivotInput input = new PivotInput();
  private PivotSubsystem pSubsystem = new PivotSubsystem();
  private StoppingCounter counter =
      new StoppingCounter(getClass().getSimpleName(), PeriodicLoopsPerSecond.getLoopsPerSecond(1));

  public TestPivotCommand(PivotSubsystem subsystem) {
    super(subsystem);
    this.pSubsystem = subsystem;
  }

  public void initialize() {
    counter.reset();
    input.setActivate(true);
    input.setRequestedPosition(5);
    input.setBrakeModeEnabled(false);
    pSubsystem.updateInputs(input);
  }

  public void execute() {
    isFinished(counter.isFinished(pSubsystem.getOutputs().getCurrentPosition() == 5));
  }

  public void end() {
    input.setRequestedPosition(0);
    input.setActivate(false);
    pSubsystem.updateInputs(input);
  }

  public boolean isFinished(boolean isFinished) {
    return isFinished;
  }
}
