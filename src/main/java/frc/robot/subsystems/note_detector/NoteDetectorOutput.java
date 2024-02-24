package frc.robot.subsystems.note_detector;

import java.util.List;
import java.util.Optional;
import org.littletonrobotics.junction.Logger;
import org.opencv.core.Point;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;
import entech.subsystems.SubsystemOutput;

public class NoteDetectorOutput extends SubsystemOutput {

  public boolean hasNotes;
  public Optional<PhotonTrackedTarget> selectedNote;
  public List<PhotonTrackedTarget> notes;

  public Point getNoteMidpoint(TargetCorner bottomLeft, TargetCorner topRight) {
    Point midpoint = new Point((bottomLeft.x + topRight.x) / 2, (bottomLeft.y + topRight.y) / 2);
    return midpoint;
  }

  public Point getCenterOfClosestNote(PhotonTrackedTarget closestNote) {
    List<TargetCorner> corners = closestNote.getDetectedCorners();
    TargetCorner bottomLeft = corners.get(0);
    TargetCorner topRight = corners.get(2);
    return getNoteMidpoint(bottomLeft, topRight);
  }

  @Override
  public void toLog() {
    Logger.recordOutput("NoteDetectorOutput/hasNotes", hasNotes);

    if (selectedNote.isPresent()) {
      Logger.recordOutput("NoteDetectorOutput/selectedNote", selectedNote.get());
    }

    for (int i = 0; i < notes.size(); i++) {
      Logger.recordOutput("NoteDetectorOutput/note" + i, notes.get(i));
    }
  }
}
