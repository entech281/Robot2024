package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.LogTable;
import entech.subsystems.SubsystemInput;

public class IntakeInput implements SubsystemInput {
  private boolean activate = false;
  private boolean brakeModeEnabled = false;
  private double speed = 0.0;

  @Override
  public void toLog(LogTable table) {
    table.put("activate", activate);
    table.put("brakeModeEnabled", brakeModeEnabled);
  }

  @Override
  public void fromLog(LogTable table) {
    activate = table.get("activate", false);
    brakeModeEnabled = table.get("brakeModeEnabled", false);
  }

  public boolean getActivate() {
    return this.activate;
  }

  public void setActivate(boolean activate) {
    this.activate = activate;
  }

  public boolean getBrakeModeEnabled() {
    return this.brakeModeEnabled;
  }

  public void setBrakeModeEnabled(boolean brakeModeEnabled) {
    this.brakeModeEnabled = brakeModeEnabled;
  }

  public double getSpeed() {
    return this.speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }
}
