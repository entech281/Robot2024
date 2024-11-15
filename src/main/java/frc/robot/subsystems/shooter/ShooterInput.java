package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.LogTable;
import frc.entech.subsystems.SubsystemInput;

public class ShooterInput implements SubsystemInput {
  private boolean activate = false;
  private boolean brakeModeEnabled = false;
  private double speed = 0;

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

  public boolean isBrakeModeEnabled() {
    return this.brakeModeEnabled;
  }

  public void setBrakeModeEnabled(boolean setBrakeModeEnabled) {
    this.brakeModeEnabled = setBrakeModeEnabled;
  }
}
