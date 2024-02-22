package frc.robot.subsystems.pivot;

import org.littletonrobotics.junction.Logger;
import entech.subsystems.SubsystemOutput;

public class PivotOutput extends SubsystemOutput {
  private boolean moving = false;
  private boolean leftBrakeModeEnabled = false;
  private boolean rightBrakeModeEnabled = false;
  private double currentPosition = 0.0;

  @Override
  public void toLog() {
    Logger.recordOutput("PivotOutput/moving", moving);
    Logger.recordOutput("PivotOutput/leftBrakeModeEnabled", leftBrakeModeEnabled);
    Logger.recordOutput("PivotOutput/rightBrakeModeEnabled", rightBrakeModeEnabled);
    Logger.recordOutput("PivotOutput/currentPosition", currentPosition);
  }

  public boolean isMoving() {
    return this.moving;
  }

  public void setMoving(boolean moving) {
    this.moving = moving;
  }

  public boolean isLeftBrakeModeEnabled() {
    return this.leftBrakeModeEnabled;
  }

  public void setLeftBrakeModeEnabled(boolean leftBrakeModeEnabled) {
    this.leftBrakeModeEnabled = leftBrakeModeEnabled;
  }

  public boolean isRightBrakeModeEnabled() {
    return this.rightBrakeModeEnabled;
  }

  public void setRightBrakeModeEnabled(boolean rightBrakeModeEnabled) {
    this.rightBrakeModeEnabled = rightBrakeModeEnabled;
  }

  public double getCurrentPosition() {
    return this.currentPosition;
  }

  public void setCurrentPosition(double currentPosition) {
    this.currentPosition = currentPosition;
  }
}
