package frc.robot.subsystems.note_detector;

import org.opencv.core.Point;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;
import entech.subsystems.SubsystemOutput;
import frc.robot.RobotConstants;
import java.util.List;
import java.util.Optional;

public class NoteDetectorOutput implements SubsystemOutput {

  private boolean hasNotes;
  private Optional<PhotonTrackedTarget> selectedNote;
  private List<PhotonTrackedTarget> notes;

  public boolean isHasNotes() {
    return hasNotes;
  }

  public Optional<PhotonTrackedTarget> getSelectedNote() {
    return selectedNote;
  }

  public List<PhotonTrackedTarget> getNotes() {
    return notes;
  }

  public void setHasNotes(boolean hasNotes) {
    this.hasNotes = hasNotes;
  }

  public void setSelectedNote(Optional<PhotonTrackedTarget> selectedNote) {
    this.selectedNote = selectedNote;
  }

  public void setNotes(List<PhotonTrackedTarget> notes) {
    this.notes = notes;
  }

  private Point getNoteMidpoint(Point bottomLeft, Point topRight) {
    Point midpoint = new Point((bottomLeft.x+topRight.x)/2, (bottomLeft.y+topRight.y)/2);
    return midpoint;
  }

  private Point transformedPoint(Point p) {
    double cameraCenterX = RobotConstants.Vision.Cameras.COLOR_RESOLUTION[0]/2;
    return new Point(p.x-cameraCenterX, p.y);
  }

  public Optional<Point> getCenterOfClosestNote(){
    Optional<Point> center = Optional.empty();
    if (selectedNote.isPresent()) {
      List<TargetCorner> corners = selectedNote.get().getDetectedCorners();
      Point bottomLeft = transformedPoint(new Point(corners.get(0).x, corners.get(0).y));
      Point topRight = transformedPoint(new Point(corners.get(2).x, corners.get(2).y));
      center = Optional.of(getNoteMidpoint(bottomLeft, topRight));
    }
    return center;
  }  
  
  @Override
  public void log() {}
  
}
