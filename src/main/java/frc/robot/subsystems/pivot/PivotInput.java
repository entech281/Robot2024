package frc.robot.subsystems.pivot;

import org.littletonrobotics.junction.LogTable;
import entech.subsystems.SubsystemInput;

public class PivotInput implements SubsystemInput {
  private double requestedPosition = 0.0;
  private boolean brakeModeEnabled = false;

  @Override
  public void toLog(LogTable table) {
    table.put("Requested position", requestedPosition);
    table.put("Brake Mode Enabled", brakeModeEnabled);
  }

  @Override
  public void fromLog(LogTable table) {
    requestedPosition = table.get("Requested position", 0.0);
    brakeModeEnabled = table.get("Brake Mode Enabled", true);
  }

  public double getRequestedPosition() {
    return this.requestedPosition;
  }

  public void setRequestedPosition(double requestedPosition) {
    this.requestedPosition = requestedPosition;
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
