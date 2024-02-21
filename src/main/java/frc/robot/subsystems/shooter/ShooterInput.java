package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.LogTable;
import entech.subsystems.SubsystemInput;

public class ShooterInput implements SubsystemInput {
  private boolean activate = false;
  private double speed = 0;
  private boolean brakeModeEnabled = false;

  @Override
  public void toLog(LogTable table) {
    table.put("activate", activate);
    table.put("speed", speed);
    table.put("brakeModeEnabled", brakeModeEnabled);
  }

  @Override
  public void fromLog(LogTable table) {
    activate = table.get("activate", false);
    speed = table.get("speed", 0.0);
    brakeModeEnabled = table.get("brakeModeEnabled", false);

  }
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
}
