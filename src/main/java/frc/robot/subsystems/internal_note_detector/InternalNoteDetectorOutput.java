package frc.robot.subsystems.internal_note_detector;

import org.littletonrobotics.junction.Logger;
import entech.subsystems.SubsystemOutput;

public class InternalNoteDetectorOutput extends SubsystemOutput {

  private boolean forwardNoteSensor = false;
  private boolean rearNoteSensor = false;

  public boolean forwardSensorHasNote() {
    return forwardNoteSensor;
  }

  public void setForwardNoteSensor(boolean forwardNoteSensor) {
    this.forwardNoteSensor = forwardNoteSensor;
  }

  public boolean rearSensorHasNote() {
    return rearNoteSensor;
  }

  public void setRearNoteSensor(boolean rearNoteSensor) {
    this.rearNoteSensor = rearNoteSensor;
  }

  @Override
  public void toLog() {
    Logger.recordOutput("ForwardInternalSensor", forwardNoteSensor);
    Logger.recordOutput("RearInternalSensor", rearNoteSensor);
  }

}
