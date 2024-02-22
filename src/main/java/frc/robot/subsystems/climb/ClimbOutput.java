package frc.robot.subsystems.climb;

import entech.subsystems.SubsystemOutput;

public class ClimbOutput implements SubsystemOutput {

  private boolean active;
  private double currentPosition;
  private boolean brakeModeEnabled;
  private boolean extended;

  @Override
  public void log() {}

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public double getCurrentPosition() {
    return currentPosition;
  }

  public void setCurrentPosition(double currentPosition) {
    this.currentPosition = currentPosition;
  }

  public boolean isBrakeModeEnabled() {
    return brakeModeEnabled;
  }

  public void setBrakeModeEnabled(boolean brakeModeEnabled) {
    this.brakeModeEnabled = brakeModeEnabled;
  }

  public boolean isExtended() {
    return extended;
  }

  public void setExtended(boolean extended) {
    this.extended = extended;
  }

}
