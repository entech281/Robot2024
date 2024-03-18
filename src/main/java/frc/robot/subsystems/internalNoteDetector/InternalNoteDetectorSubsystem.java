package frc.robot.subsystems.internalNoteDetector;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import frc.robot.commands.test.TestInternalNoteDetectorCommand;
import frc.robot.io.RobotIO;

public class InternalNoteDetectorSubsystem
    extends EntechSubsystem<InternalNoteDetectorInput, InternalNoteDetectorOutput> {

  private boolean ENABLED = true;

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
  public void updateInputs(InternalNoteDetectorInput input) {
    RobotIO.processInput(input);
  }

  @Override
  public Command getTestCommand() {
    return new TestInternalNoteDetectorCommand(this);
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
