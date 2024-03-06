package frc.robot.subsystems.climb;

import org.littletonrobotics.junction.LogTable;
import entech.subsystems.SubsystemInput;

public class ClimbInput implements SubsystemInput {

  private boolean activate = false;
  private double requestedPosition = 0.0;
  // private double currentRoll = 0.0;

  @Override
  public void toLog(LogTable table) {
    table.put("activate", activate);
    table.put("requestedPosition", requestedPosition);
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

  public double getRequestedPosition() {
    return this.requestedPosition;
  }

  public void setRequestedPosition(double requestedPosition) {
    this.requestedPosition = requestedPosition;
  }

  // public double getCurrentAngle() {
  // return this.currentRoll;
  // }

  // public void setCurrentAngle(double currentRoll) {
  // this.currentRoll = currentRoll;
  // }

}
