package frc.robot.commands;

import java.util.function.Supplier;
import entech.commands.EntechCommand;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.pivot.PivotSubsystem;

public class PivotNudgeCommand extends EntechCommand {
  private final PivotSubsystem pivot;

  private static final double NUDGE_AMOUNT = 0.05;
  private final Supplier<Integer> pov;

  public PivotNudgeCommand(PivotSubsystem pivot, Supplier<Integer> pov) {
    super(pivot);
    this.pivot = pivot;
    this.pov = pov;
  }

  @Override
  public void execute() {
    PivotInput pi = new PivotInput();
    pi.setActivate(false);

    double pivotAngle = pivot.getOutputs().getCurrentPosition();

    if (pov.get() == 0) {
      pi.setRequestedPosition(pivotAngle + NUDGE_AMOUNT);
    }

    if (pov.get() == 180) {
      pi.setRequestedPosition(pivotAngle - NUDGE_AMOUNT);
    }

    pi.setRequestedPosition(pivotAngle);

    pivot.updateInputs(pi);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
