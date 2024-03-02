package frc.robot.commands.test;

import java.util.Optional;
import org.littletonrobotics.junction.Logger;
import org.opencv.core.Point;
import entech.commands.EntechCommand;
import frc.robot.RobotConstants;
import frc.robot.subsystems.noteDetector.NoteDetectorOutput;
import frc.robot.subsystems.noteDetector.NoteDetectorSubsystem;

public class TestNoteDetectorCommand extends EntechCommand {
  private final NoteDetectorSubsystem noteDetector;
  private boolean complete = false;

  public TestNoteDetectorCommand(NoteDetectorSubsystem noteDetector) {
    super(noteDetector);
    this.noteDetector = noteDetector;
  }

  @Override
  public void end(boolean interrupted) {
    Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST, "No Current Test");
  }

  @Override
  public void execute() {
    NoteDetectorOutput out = noteDetector.getOutputs();

    Optional<Point> midpoint = out.getMidpoint();

    if (midpoint.isPresent()) {
      if (midpoint.get().x < -0.1) {
        Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST, "Move note right.");
        return;
      }

      if (midpoint.get().x > 0.1) {
        Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST, "Move note left.");
        return;
      }

      complete = true;
    } else {
      Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST,
          "Put note in front of colored camera.");
    }
  }

  @Override
  public void initialize() {
    complete = false;
  }

  @Override
  public boolean isFinished() {
    return complete;
  }
}
