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
  private Optional<Point> midpoint;
  private double yaw;

  public double getYaw() {
    return yaw;
  }

  public void setYaw(double yaw) {
    this.yaw = yaw;
  }

  public Optional<Point> getMidpoint() {
    return midpoint;
  }

  public void setMidpoint(Optional<Point> midpoint) {
    this.midpoint = midpoint;
  }


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

    if (midpoint.isPresent()) {
      Logger.recordOutput("NoteDetectorOutput/midpoint/x", midpoint.get().x);
      Logger.recordOutput("NoteDetectorOutput/midpoint/y", midpoint.get().y);
    }

    Logger.recordOutput("NoteDetectorOutput/yaw", yaw);
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
