package frc.robot.subsystems.has_note;

import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class HasNoteSubsystem extends EntechSubsystem<HasNoteInput, HasNoteOutput> {

  private boolean ENABLED = false;

  private DigitalInput hasNoteSensor;

  @Override
  public void initialize() {
    if (ENABLED) {
      hasNoteSensor = new DigitalInput(RobotConstants.Ports.HAS_NOTE.HAS_NOTE_SENSOR);
    }
  }

  @Override
  public boolean isEnabled() {
    return ENABLED;
  }

  @Override
  public void updateInputs(HasNoteInput input) {}

  @Override
  public Command getTestCommand() {
    return Commands.none();
  }

  @Override
  public HasNoteOutput toOutputs() {
    HasNoteOutput output = new HasNoteOutput();
    if (ENABLED) {
      output.setHasNote(hasNoteSensor.get());
    }
    return output;
  }

}
