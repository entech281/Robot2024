package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.Logger;
import entech.subsystems.SubsystemOutput;

public class ShooterOutput implements SubsystemOutput {
  private double speed;
  private boolean active;
  private boolean brakeModeEnabled;

  @Override
  public void log() {
    Logger.recordOutput("shooterOutputOutput/active", active);
    Logger.recordOutput("shooterOutputOutput/speed", speed);
    Logger.recordOutput("shooterOutputOutput/brakeModeEnabled", brakeModeEnabled);
  }

  public double getSpeed() {
    return this.speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
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

  public boolean isBrakeModeEnabled() {
    return this.brakeModeEnabled;
  }

  public boolean getBrakeModeEnabled() {
    return this.brakeModeEnabled;
  }

  public void setBrakeModeEnabled(boolean brakeModeEnabled) {
    this.brakeModeEnabled = brakeModeEnabled;
  }
}
