package frc.robot.subsystems.note_detector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;

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
  public NoteDetectorOutput toOutputs() {
    NoteDetectorOutput output = new NoteDetectorOutput();

    output.hasNotes = !notes.isEmpty();
    if (output.hasNotes) {
      output.selectedNote = chooseNote();
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

}
