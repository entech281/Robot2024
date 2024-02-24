package frc.robot.subsystems.note_detector;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;
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
    return ENABLED ? Optional.of(chosenNote) : Optional.empty();
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

  @Override
  public Command getTestCommand() {
    return new TestNoteDetectorCommand(this);
  }

  private void updateNotes() {
    notes = colorCamera.getLatestResult().targets;
  }
}
