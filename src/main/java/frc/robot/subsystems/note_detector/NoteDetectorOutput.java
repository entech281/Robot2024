package frc.robot.subsystems.note_detector;

import org.opencv.core.Point;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;
import entech.subsystems.SubsystemOutput;
import java.util.List;

public class NoteDetectorOutput implements SubsystemOutput {

  public boolean hasNotes;
  public PhotonTrackedTarget selectedNote;
  public List<PhotonTrackedTarget> notes;

  public Point getNoteMidpoint(TargetCorner bottomLeft, TargetCorner topRight) {
    Point midpoint = new Point((bottomLeft.x+topRight.x)/2, (bottomLeft.y+topRight.y)/2);
    return midpoint;
  }

  public Point getCenterOfClosestNote(PhotonTrackedTarget closestNote){
    List<TargetCorner> corners = closestNote.getDetectedCorners();
    TargetCorner bottomLeft = corners.get(0);
    TargetCorner topRight = corners.get(2);
    return getNoteMidpoint(bottomLeft, topRight);
  }
  
  @Override
  public void log() {}
  
}
