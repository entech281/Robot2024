package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.Logger;
import entech.subsystems.SubsystemOutput;

public class IntakeOutput implements SubsystemOutput {

  private boolean active;
  private double currentSpeed;
  private boolean brakeModeEnabled;

  @Override
  public void log() {
    Logger.recordOutput("intakeOutput/active", active);
    Logger.recordOutput("intakeOutput/currentSpeed", currentSpeed);
    Logger.recordOutput("intakeOutput/brakeModeEnabled", brakeModeEnabled);

  }

  public boolean isActive() {
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

  public void setBrakeModeEnabled(boolean brakeModeEnabled) {
    this.brakeModeEnabled = brakeModeEnabled;
  }
}
