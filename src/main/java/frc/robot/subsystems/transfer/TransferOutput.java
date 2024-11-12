package frc.robot.subsystems.transfer;

import org.littletonrobotics.junction.Logger;
import frc.entech.subsystems.SubsystemOutputBasics;

public class TransferOutput extends SubsystemOutputBasics {
  private TransferSubsystem.TransferPreset currentMode;

  @Override
  public void toLog() {
    super.toLog();
    Logger.recordOutput(key + "currentMode", currentMode);
  }

  public TransferSubsystem.TransferPreset getCurrentMode() {
    return this.currentMode;
  }

  public void setCurrentMode(TransferSubsystem.TransferPreset currentMode) {
    this.currentMode = currentMode;
  }
}
