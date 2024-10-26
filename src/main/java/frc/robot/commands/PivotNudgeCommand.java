package frc.robot.commands;

import java.util.function.Supplier;
import frc.entech.commands.EntechCommand;
import frc.robot.RobotConstants;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.pivot.PivotSubsystem;

public class PivotNudgeCommand extends EntechCommand {
  private final PivotSubsystem pivot;

  private static final double NUDGE_AMOUNT = 0.4;
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

    double pivotAngle = pivot.getOutputs().getRequestedPosition();

    if (pov.get() == 0) {
      double angle = pivotAngle + NUDGE_AMOUNT;
      if (angle > RobotConstants.PIVOT.UPPER_SOFT_LIMIT_DEG) {
        angle = RobotConstants.PIVOT.UPPER_SOFT_LIMIT_DEG;
      }
      pi.setRequestedPosition(angle);
      pivot.updateInputs(pi);
    }

    if (pov.get() == 180) {
      double angle = pivotAngle - NUDGE_AMOUNT;
      if (angle < RobotConstants.PIVOT.LOWER_SOFT_LIMIT_DEG) {
        angle = RobotConstants.PIVOT.LOWER_SOFT_LIMIT_DEG;
      }
      pi.setRequestedPosition(angle);
      pivot.updateInputs(pi);
    }
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
