package frc.robot.subsystems.internalnotedetector;

import org.littletonrobotics.junction.Logger;
import frc.entech.subsystems.SubsystemOutput;

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

  public boolean hasNote() {
    return forwardNoteSensor || rearNoteSensor;
  }

  @Override
  public void toLog() {
    Logger.recordOutput("InternalNoteDetectorOutput/ForwardInternalSensor", forwardNoteSensor);
    Logger.recordOutput("InternalNoteDetectorOutput/RearInternalSensor", rearNoteSensor);
    Logger.recordOutput("InternalNoteDetectorOutput/HasNote", hasNote());
  }

}
