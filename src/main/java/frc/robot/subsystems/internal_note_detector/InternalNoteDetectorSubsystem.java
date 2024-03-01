package frc.robot.subsystems.internal_note_detector;

import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class InternalNoteDetectorSubsystem
    extends EntechSubsystem<InternalNoteDetectorInput, InternalNoteDetectorOutput> {

  private boolean ENABLED = false;

  private DigitalInput internalSensorF;
  private DigitalInput internalSensorR;

  @Override
  public void initialize() {
    if (ENABLED) {
      internalSensorF = new DigitalInput(RobotConstants.PORTS.HAS_NOTE.INTERNAL_SENSOR_FORWARD);
      internalSensorR = new DigitalInput(RobotConstants.PORTS.HAS_NOTE.INTERNAL_SENSOR_REAR);
    }
  }

  @Override
  public boolean isEnabled() {
    return ENABLED;
  }

  @Override
  public void updateInputs(InternalNoteDetectorInput input) {}

  @Override
  public Command getTestCommand() {
    return Commands.none();
  }

  @Override
  public InternalNoteDetectorOutput toOutputs() {
    InternalNoteDetectorOutput output = new InternalNoteDetectorOutput();
    if (ENABLED) {
      output.setForwardNoteSensor(internalSensorF.get());
      output.setRearNoteSensor(internalSensorR.get());
    }
    return output;
  }

}
