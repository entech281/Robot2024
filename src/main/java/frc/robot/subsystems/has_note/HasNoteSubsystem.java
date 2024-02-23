package frc.robot.subsystems.has_note;

import entech.subsystems.EntechSubsystem;
import edu.wpi.first.wpilibj.DigitalInput;

public class HasNoteSubsystem extends EntechSubsystem<HasNoteInput, HasNoteOutput> {

  private boolean ENABLED = false;

  private DigitalInput hasNoteSensor;

  @Override
  public void initialize() {
    if (ENABLED) {
      hasNoteSensor = new DigitalInput(0);
    }
  }

  @Override
  public boolean isEnabled() {
    return ENABLED;
  }

  @Override
  public void updateInputs(HasNoteInput input) {}

  @Override
  public HasNoteOutput getOutputs() {
    HasNoteOutput output = new HasNoteOutput();
    output.setHasNote(hasNoteSensor.get());
    return output;
  }

}
