package frc.robot.subsystems.transfer;

import entech.subsystems.SubsystemOutput;
import org.littletonrobotics.junction.Logger;

public class TransferOutput implements SubsystemOutput {

  public boolean active;
  public double currentSpeed;
  public boolean brakeModeEnabled;
  public TransferSubsystem.TransferStatus currentMode;

  @Override
  public void log() {
    Logger.recordOutput("transferOutput/active", active);
    Logger.recordOutput("transferOutput/currentMode", currentMode);
    Logger.recordOutput("transferOutput/brakeModeEnabled", brakeModeEnabled);
  }
}
