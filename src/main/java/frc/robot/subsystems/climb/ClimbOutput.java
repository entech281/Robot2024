package frc.robot.subsystems.climb;

import org.littletonrobotics.junction.Logger;
import frc.entech.subsystems.SubsystemOutput;

public class ClimbOutput extends SubsystemOutput {

  private boolean active;
  private double currentPosition;
  private boolean brakeModeEnabled;
  private boolean extended;

  @Override
  public void toLog() {
    Logger.recordOutput("ClimbOutput/active", active);
    Logger.recordOutput("ClimbOutput/currentPosition", currentPosition);
    Logger.recordOutput("ClimbOutput/brakeModeEnabled", brakeModeEnabled);
    Logger.recordOutput("ClimbOutput/extended", extended);
  }

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
