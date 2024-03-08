package frc.robot.subsystems.climb;

import org.littletonrobotics.junction.LogTable;
import entech.subsystems.SubsystemInput;

public class ClimbInput implements SubsystemInput {

  private boolean activate = false;
  private boolean brakeModeEnabled = false;
  private boolean feeze = false;
  private double speed = 0.0;
  // private double currentRoll = 0.0;

  @Override
  public void toLog(LogTable table) {
    table.put("activate", activate);
    table.put("speed", speed);
    // table.put("currentRoll", currentRoll);
  }

  @Override
  public void fromLog(LogTable table) {}

  public boolean getActivate() {
    return this.activate;
  }

  public void setActivate(boolean activate) {
    this.activate = activate;
  }

  public double getSpeed() {
    return this.speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  public boolean getBrakeModeEnabled() {
    return this.brakeModeEnabled;
  }

  public void setBrakeModeEnabled(boolean brakeModeEnabled) {
    this.brakeModeEnabled = brakeModeEnabled;
  }

  public boolean getFeeze() {
    return this.feeze;
  }

  public void setFeeze(boolean feeze) {
    this.feeze = feeze;
  }

  // public double getCurrentAngle() {
  // return this.currentRoll;
  // }

  // public void setCurrentAngle(double currentRoll) {
  // this.currentRoll = currentRoll;
  // }

}
