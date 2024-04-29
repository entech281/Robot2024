package frc.robot.subsystems.transfer;

import org.littletonrobotics.junction.LogTable;
import entech.subsystems.SubsystemInput;

public class TransferInput implements SubsystemInput {
  private boolean activate = false;
  private TransferSubsystem.TransferPreset speedPreset = TransferSubsystem.TransferPreset.OFF;

  @Override
  public void toLog(LogTable table) {
    table.put("activate", activate);
    table.put("currentMode", speedPreset);
  }

  @Override
  public void fromLog(LogTable table) {
    activate = table.get("activate", false);
    speedPreset = table.get("currentMode", TransferSubsystem.TransferPreset.OFF);
  }

  public boolean getActivate() {
    return this.activate;
  }

  public void setActivate(boolean activate) {
    this.activate = activate;
  }

  public TransferSubsystem.TransferPreset getSpeedPreset() {
    return this.speedPreset;
  }

  public void setSpeedPreset(TransferSubsystem.TransferPreset currentMode) {
    this.speedPreset = currentMode;
  }
}
