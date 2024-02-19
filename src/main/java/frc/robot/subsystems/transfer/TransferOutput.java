package frc.robot.subsystems.transfer;

import org.littletonrobotics.junction.Logger;
import entech.subsystems.SubsystemOutput;

public class TransferOutput implements SubsystemOutput {
  private boolean active;
  private double currentSpeed;
  private boolean brakeModeEnabled;
  private TransferSubsystem.TransferStatus currentMode;

  @Override
  public void log() {
    Logger.recordOutput("transferOutput/active", active);
    Logger.recordOutput("transferOutput/currentMode", currentMode);
    Logger.recordOutput("transferOutput/brakeModeEnabled", brakeModeEnabled);
  }

  public boolean isActive() {
    return this.active;
  }

  public boolean getActive() {
    return this.active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public double getCurrentSpeed() {
    return this.currentSpeed;
  }

  public void setCurrentSpeed(double currentSpeed) {
    this.currentSpeed = currentSpeed;
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
