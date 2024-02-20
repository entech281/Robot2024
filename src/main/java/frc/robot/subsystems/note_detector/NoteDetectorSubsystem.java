package frc.robot.subsystems.note_detector;

import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import org.photonvision.PhotonCamera;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

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
    if (chosenNote == null)
      return Optional.empty();
      return ENABLED ? Optional.of(chosenNote) : Optional.empty();
  }

  public PhotonTrackedTarget chooseNote() {
    double highestArea = 0;
    for (PhotonTrackedTarget currentNote : notes) {
      if (currentNote.getArea() > highestArea) {
        highestArea = currentNote.getArea();
        chosenNote = currentNote;
      }
    }
    return chosenNote;
  }

  @Override
  public NoteDetectorOutput getOutputs() {
    NoteDetectorOutput output = new NoteDetectorOutput();

    output.hasNotes = !notes.isEmpty();
    if (output.hasNotes) {
      output.selectedNote = chooseNote();
    }

    return output;
  }

  private void updateNoteDetectorData() {
    PhotonPipelineResult result = colorCamera.getLatestResult();
    notes = result.getTargets();
  }

  @Override
  public void periodic() {
    if (ENABLED) {
      updateNoteDetectorData();
    }
  }
  
}
