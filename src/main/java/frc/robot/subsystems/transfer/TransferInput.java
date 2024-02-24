package frc.robot.subsystems.transfer;

import org.littletonrobotics.junction.LogTable;
import entech.subsystems.SubsystemInput;

public class TransferInput implements SubsystemInput {
  private boolean activate = false;
  private boolean brakeModeEnabled = false;
  private TransferSubsystem.TransferPreset speedPreset = TransferSubsystem.TransferPreset.Off;

  @Override
  public void toLog(LogTable table) {
    table.put("activate", activate);
    table.put("currentMode", speedPreset);
    table.put("brakeModeEnabled", brakeModeEnabled);
  }

  @Override
  public void fromLog(LogTable table) {
    activate = table.get("activate", false);
    speedPreset = table.get("currentMode", TransferSubsystem.TransferPreset.Off);
    brakeModeEnabled = table.get("brakeModeEnabled", false);
  }

  public boolean getActivate() {
    return this.activate;
  }

  public void setActivate(boolean activate) {
    this.activate = activate;
  }

  public boolean getBrakeModeEnabled() {
    return this.brakeModeEnabled;
  }

  public void setBrakeModeEnabled(boolean brakeModeEnabled) {
    this.brakeModeEnabled = brakeModeEnabled;
  }

  public TransferSubsystem.TransferPreset getSpeedPreset() {
    return this.speedPreset;
  }

  public void setSpeedPreset(TransferSubsystem.TransferPreset currentMode) {
    this.speedPreset = currentMode;
  }
}
