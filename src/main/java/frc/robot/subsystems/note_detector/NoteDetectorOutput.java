package frc.robot.subsystems.note_detector;

import java.util.List;
import java.util.Optional;
import org.littletonrobotics.junction.Logger;
import org.opencv.core.Point;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;
import entech.subsystems.SubsystemOutput;

public class NoteDetectorOutput extends SubsystemOutput {

  private boolean hasNotes;
  private Optional<PhotonTrackedTarget> selectedNote;
  private List<PhotonTrackedTarget> notes;

  private Point getNoteMidpoint(TargetCorner bottomLeft, TargetCorner topRight) {
    Point midpoint = new Point((bottomLeft.x + topRight.x) / 2, (bottomLeft.y + topRight.y) / 2);
    return midpoint;
  }

  public Point getCenterOfClosestNote(PhotonTrackedTarget closestNote) {
    List<TargetCorner> corners = closestNote.getMinAreaRectCorners();
    if (corners.isEmpty())
      return null;
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

  public boolean hasNotes() {
    return this.hasNotes;
  }

  public void setHasNotes(boolean hasNotes) {
    this.hasNotes = hasNotes;
  }

  public Optional<PhotonTrackedTarget> getSelectedNote() {
    return this.selectedNote;
  }

  public void setSelectedNote(Optional<PhotonTrackedTarget> selectedNote) {
    this.selectedNote = selectedNote;
  }

  public List<PhotonTrackedTarget> getNotes() {
    return this.notes;
  }

  public void setNotes(List<PhotonTrackedTarget> notes) {
    this.notes = notes;
  }
}
