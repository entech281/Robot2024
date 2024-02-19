package frc.robot.subsystems.transfer;

import org.littletonrobotics.junction.LogTable;
import entech.subsystems.SubsystemInput;

public class TransferInput implements SubsystemInput {
  private boolean activate = false;
  private boolean brakeModeEnabled = false;
  private TransferSubsystem.TransferStatus currentMode = TransferSubsystem.TransferStatus.Off;

  @Override
  public void toLog(LogTable table) {
    table.put("transferInput/active", activate);
    table.put("transferInput/currentMode", currentMode);
    table.put("transferInput/brakeModeEnabled", brakeModeEnabled);
  }

  @Override
  public void fromLog(LogTable table) {}

  public boolean isActivate() {
    return this.activate;
  }

  public boolean getActivate() {
    return this.activate;
  }

  public void setActivate(boolean activate) {
    this.activate = activate;
  }

  public boolean isBrakeModeEnabled() {
    return this.brakeModeEnabled;
  }

  public boolean getBrakeModeEnabled() {
    return this.brakeModeEnabled;
  }

  public void setBrakeModeEnabled(boolean brakeModeEnabled) {
    this.brakeModeEnabled = brakeModeEnabled;
  }

  public TransferSubsystem.TransferStatus getCurrentMode() {
    return this.currentMode;
  }

  public void setCurrentMode(TransferSubsystem.TransferStatus currentMode) {
    this.currentMode = currentMode;
  }
}
