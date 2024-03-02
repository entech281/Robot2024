package frc.robot.commands.test;

import org.littletonrobotics.junction.Logger;
import entech.commands.EntechCommand;
import frc.robot.RobotConstants;
import frc.robot.subsystems.internalNoteDetector.InternalNoteDetectorSubsystem;

public class TestInternalNoteDetector extends EntechCommand {
  private final InternalNoteDetectorSubsystem detector;

  private int stage = 0;

  public TestInternalNoteDetector(InternalNoteDetectorSubsystem internalNoteDetectorSubsystem) {
    super(internalNoteDetectorSubsystem);
    detector = internalNoteDetectorSubsystem;
  }

  @Override
  public void execute() {
    switch (stage) {
      case 0:
        if (detector.getOutputs().forwardSensorHasNote()) {
          stage++;
        } else {
          Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST,
              "Trigger the forward sensor.");
        }
        break;
      case 1:
        if (detector.getOutputs().rearSensorHasNote()) {
          stage++;
        } else {
          Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST,
              "Trigger the rear sensor.");
        }
        break;
      default:
        break;
    }
  }

  @Override
  public void initialize() {
    stage = 0;
  }

  @Override
  public boolean isFinished() {
    return stage >= 2;
  }
}
