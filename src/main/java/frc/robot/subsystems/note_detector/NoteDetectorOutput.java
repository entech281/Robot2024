package frc.robot.subsystems.note_detector;

import java.util.List;
import java.util.Optional;
import org.littletonrobotics.junction.Logger;
import org.opencv.core.Point;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;
import entech.subsystems.SubsystemOutput;
import frc.robot.RobotConstants;
import java.util.List;
import java.util.Optional;

public class NoteDetectorOutput extends SubsystemOutput {

  private Optional<Point> midpoint;
  private double yaw;

  public double getYaw() {
    return yaw;
  }

  public void setYaw(double yaw) {
    this.yaw = yaw;
  }

  @Override
  public void log() {

  }

  public Optional<Point> getMidpoint() {
    return midpoint;
  }

  public void setMidpoint(Optional<Point> midpoint) {
    this.midpoint = midpoint;
  }
  

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
    }
  }

