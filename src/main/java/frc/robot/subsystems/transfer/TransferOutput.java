package frc.robot.subsystems.transfer;

import org.littletonrobotics.junction.Logger;
import entech.subsystems.SpinMotorSubsystemOutput;

public class TransferOutput extends SpinMotorSubsystemOutput {
  private TransferSubsystem.TransferStatus currentMode;

  public TransferOutput() {
    super("transferOutput");
  }

  @Override
  public void log() {
    super.log();
    Logger.recordOutput(key + "currentMode", currentMode);
  }

  public TransferSubsystem.TransferStatus getCurrentMode() {
    return this.currentMode;
  }

  public void setCurrentMode(TransferSubsystem.TransferStatus currentMode) {
    this.currentMode = currentMode;
  }
}
