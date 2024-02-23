package frc.robot.subsystems.has_note;

import entech.subsystems.SubsystemOutput;

public class HasNoteOutput implements SubsystemOutput {

  private boolean hasNote = false;

  @Override
  public void log() {}

  public boolean hasNote() {
    return hasNote;
  }

  public void setHasNote(boolean hasNoteSensor) {
    this.hasNote = hasNoteSensor;
  }

}
