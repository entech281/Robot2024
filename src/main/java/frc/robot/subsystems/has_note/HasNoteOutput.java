package frc.robot.subsystems.has_note;

import entech.subsystems.SubsystemOutput;

public class HasNoteOutput extends SubsystemOutput {

  private boolean hasNote = false;

  public boolean hasNote() {
    return hasNote;
  }

  public void setHasNote(boolean hasNoteSensor) {
    this.hasNote = hasNoteSensor;
  }

  @Override
  public void toLog() {}

}
