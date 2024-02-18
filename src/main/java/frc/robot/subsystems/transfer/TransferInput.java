package frc.robot.subsystems.transfer;

import entech.subsystems.SubsystemInput;
import org.littletonrobotics.junction.LogTable;

public class TransferInput implements SubsystemInput {

  public boolean activate = false;
  public boolean brakeModeEnabled = false;
  public TransferSubsystem.TransferStatus currentMode = TransferSubsystem.TransferStatus.Off;

  @Override
  public void toLog(LogTable table) {
    table.put("transferInput/active", activate);
    table.put("transferInput/currentMode", currentMode);
    table.put("transferInput/brakeModeEnabled", brakeModeEnabled);
  }

  @Override
  public void fromLog(LogTable table) {}

}
