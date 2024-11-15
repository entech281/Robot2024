package frc.robot.subsystems.visionnotedetector;

import java.util.List;
import java.util.Optional;
import org.littletonrobotics.junction.Logger;
import org.opencv.core.Point;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;
import frc.entech.subsystems.SubsystemOutput;

public class NoteDetectorOutput extends SubsystemOutput {

  private boolean hasNotes;
  private Optional<PhotonTrackedTarget> selectedNote;
  private List<PhotonTrackedTarget> notes;
  private Optional<Point> midpoint;
  private double yaw;
  private double latency;
  private boolean driverMode;

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
    return new Point((bottomLeft.x + topRight.x) / 2, (bottomLeft.y + topRight.y) / 2);
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
    Logger.recordOutput("NoteDetectorOutput/latency", latency);

    Logger.recordOutput("NoteDetectorOutput/isDriverMode", driverMode);
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

  public double getLatency() {
    return this.latency;
  }

  public void setLatency(double latency) {
    this.latency = latency;
  }

  public boolean isDriverMode() {
    return this.driverMode;
  }

  public void setDriverMode(boolean driverMode) {
    this.driverMode = driverMode;
  }
}
