package frc.robot.commands;

import java.util.Optional;
import org.littletonrobotics.junction.Logger;
import org.opencv.core.Point;
import org.photonvision.targeting.PhotonTrackedTarget;
import entech.commands.EntechCommand;
import frc.robot.RobotConstants;
import frc.robot.subsystems.note_detector.NoteDetectorOutput;
import frc.robot.subsystems.note_detector.NoteDetectorSubsystem;

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

    Optional<PhotonTrackedTarget> selectedNote = out.getSelectedNote();
    Point centerOfNote = null;

    if (selectedNote.isPresent()) {
      centerOfNote = out.getCenterOfClosestNote(selectedNote.get());
    } else if (out.hasNotes()) {
      centerOfNote = out.getCenterOfClosestNote(out.getNotes().get(0));
    }

    if (centerOfNote == null) {
      Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST,
          "Put note in front of colored camera.");
    } else {
      if (centerOfNote.x < 200) {
        Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST, "Move note right.");
        return;
      }

      if (centerOfNote.x > 232) {
        Logger.recordOutput(RobotConstants.OperatorMessages.SUBSYSTEM_TEST, "Move note left.");
        return;
      }

      complete = true;
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
