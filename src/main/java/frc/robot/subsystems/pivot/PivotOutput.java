package frc.robot.subsystems.pivot;

import org.littletonrobotics.junction.Logger;
import entech.subsystems.SubsystemOutput;
import frc.robot.RobotConstants;

public class PivotOutput extends SubsystemOutput {
  private boolean moving = false;
  private boolean leftBrakeModeEnabled = false;
  private boolean rightBrakeModeEnabled = false;
  private boolean isAtRequestedPosition = false;
  private boolean isAtUpperLimit = false;
  private boolean isAtLowerLimit = false;
  private double currentPosition = RobotConstants.PIVOT.INITIAL_POSITION;

  @Override
  public void toLog() {
    Logger.recordOutput("PivotOutput/moving", moving);
    Logger.recordOutput("PivotOutput/leftBrakeModeEnabled", leftBrakeModeEnabled);
    Logger.recordOutput("PivotOutput/rightBrakeModeEnabled", rightBrakeModeEnabled);
    Logger.recordOutput("PivotOutput/currentPosition", currentPosition);
    Logger.recordOutput("PivotOutput/isAtUpperLimit", isAtUpperLimit);
    Logger.recordOutput("PivotOutput/isAtLowerLimit", isAtLowerLimit);
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

  public boolean isAtRequestedPosition() {
    return this.isAtRequestedPosition;
  }

  public void setAtRequestedPosition(boolean isAtRequestedPosition) {
    this.isAtRequestedPosition = isAtRequestedPosition;
  }

  public double getCurrentPosition() {
    return this.currentPosition;
  }

  public void setCurrentPosition(double currentPosition) {
    this.currentPosition = currentPosition;
  }

  public boolean isAtUpperLimit() {
    return this.isAtUpperLimit;
  }

  public void setAtUpperLimit(boolean isAtUpperLimit) {
    this.isAtUpperLimit = isAtUpperLimit;
  }

  public boolean isAtLowerLimit() {
    return this.isAtLowerLimit;
  }

  public void setAtLowerLimit(boolean isAtLowerLimit) {
    this.isAtLowerLimit = isAtLowerLimit;
  }
}
