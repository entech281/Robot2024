package frc.robot.subsystems.note_detector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.opencv.core.Point;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;
import edu.wpi.first.wpilibj2.command.Command;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import frc.robot.commands.TestNoteDetectorCommand;

public class NoteDetectorSubsystem extends EntechSubsystem<NoteDetectorInput, NoteDetectorOutput> {
  private static final boolean ENABLED = true;

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
    return ENABLED && chosenNote != null ? Optional.of(chosenNote) : Optional.empty();
  }

  public Optional<PhotonTrackedTarget> chooseNote() {
    double highestArea = 0;
    PhotonTrackedTarget curatedNote = null;
    for (PhotonTrackedTarget currentNote : notes) {
      if (currentNote.getArea() > highestArea) {
        highestArea = currentNote.getArea();
        curatedNote = currentNote;
      }
    }
    return ENABLED && curatedNote != null ? Optional.of(curatedNote) : Optional.empty();
  }

  @Override
  public NoteDetectorOutput toOutputs() {
    NoteDetectorOutput output = new NoteDetectorOutput();

    output.setHasNotes(!notes.isEmpty());
    output.setSelectedNote(chooseNote());
    output.setNotes(notes);
    output.setMidpoint(getCenterOfClosestNote());
    if (getChosenNote().isPresent()) {
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
      updateNotes();
      updateNoteDetectorData();
    }
  }


  private Point getNoteMidpoint(Point bottomLeft, Point topRight) {
    Point midpoint = new Point((bottomLeft.x + topRight.x) / 2, (bottomLeft.y + topRight.y) / 2);
    return midpoint;
  }

  private static Point transformedPoint(Point p) {
    double cameraCenterX = RobotConstants.Vision.Resolution.COLOR_RESOLUTION[0] / 2;
    return new Point(p.x - cameraCenterX, p.y);
  }

  public Optional<Point> getCenterOfClosestNote() {
    Optional<Point> center = Optional.empty();
    if (getChosenNote().isPresent()) {
      List<TargetCorner> corners = getChosenNote().get().getMinAreaRectCorners();
      Point bottomLeft = transformedPoint(new Point(corners.get(0).x, corners.get(0).y));
      Point topRight = transformedPoint(new Point(corners.get(2).x, corners.get(2).y));
      center = Optional.of(getNoteMidpoint(bottomLeft, topRight));
    }
    return center;
  }

  @Override
  public Command getTestCommand() {
    return new TestNoteDetectorCommand(this);
  }

  private void updateNotes() {
    notes = colorCamera.getLatestResult().targets;
  }
}
