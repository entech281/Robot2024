package frc.robot.subsystems.note_detector;

import org.opencv.core.Point;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;
import entech.subsystems.SubsystemOutput;
import frc.robot.RobotConstants;
import java.util.List;
import java.util.Optional;

public class NoteDetectorOutput implements SubsystemOutput {

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
  
}
