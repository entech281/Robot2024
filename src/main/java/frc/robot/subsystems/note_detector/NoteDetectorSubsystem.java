package frc.robot.subsystems.note_detector;

import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import org.opencv.core.Point;
import org.photonvision.PhotonCamera;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

public class NoteDetectorSubsystem extends EntechSubsystem<NoteDetectorInput, NoteDetectorOutput> {
  private static final boolean ENABLED = false;

  private PhotonCamera colorCamera;
  private PhotonTrackedTarget chosenNote;

  private List<PhotonTrackedTarget> notes = new ArrayList<>();

  @Override
  public void initialize() {
    if (ENABLED) {
      colorCamera = new PhotonCamera(RobotConstants.Vision.Cameras.COLOR);
    }
  }

  @Override
  public boolean isEnabled() {
    return ENABLED;
  }

  @Override
  public void updateInputs(NoteDetectorInput input) {
    
  }

  public Optional<PhotonTrackedTarget> getChosenNote() {
    if (chooseNote() == null)
      return Optional.empty();
    return ENABLED ? Optional.of(chosenNote) : Optional.empty();
  }

  public Optional<PhotonTrackedTarget> chooseNote() {
    double highestArea = 0;
    PhotonTrackedTarget curatedNote;
    for (PhotonTrackedTarget currentNote : notes) {
      if (currentNote.getArea() > highestArea) {
        highestArea = currentNote.getArea();
        curatedNote = currentNote;
      }
    }
    return ENABLED ? Optional.of(curatedNote) : Optional.empty();
  }

  @Override
  public NoteDetectorOutput getOutputs() {
    NoteDetectorOutput output = new NoteDetectorOutput();
    if (getChosenNote().isPresent()) {
      output.setMidpoint(getCenterOfClosestNote());
      output.setYaw(getChosenNote().get().getYaw());
    }

    return output;
  }

  private void updateNoteDetectorData() {
    Optional<PhotonTrackedTarget> pickedNote = chooseNote();
    if (pickedNote.isPresent()) {
      chosenNote = pickedNote.get();
    }
  }

  @Override
  public void periodic() {
    if (ENABLED) {
      updateNoteDetectorData();
    }
  }

  
  private Point getNoteMidpoint(Point bottomLeft, Point topRight) {
    Point midpoint = new Point((bottomLeft.x+topRight.x)/2, (bottomLeft.y+topRight.y)/2);
    return midpoint;
  }

  private static Point transformedPoint(Point p) {
    double cameraCenterX = RobotConstants.Vision.Cameras.COLOR_RESOLUTION[0]/2;
    return new Point(p.x-cameraCenterX, p.y);
  }

  public Optional<Point> getCenterOfClosestNote(){
    Optional<Point> center = Optional.empty();
    if (getChosenNote().isPresent()) {
      List<TargetCorner> corners = getChosenNote().get().getMinAreaRectCorners();
      Point bottomLeft = transformedPoint(new Point(corners.get(0).x, corners.get(0).y));
      Point topRight = transformedPoint(new Point(corners.get(2).x, corners.get(2).y));
      center = Optional.of(getNoteMidpoint(bottomLeft, topRight));
    }
    return center;
  }  

}
