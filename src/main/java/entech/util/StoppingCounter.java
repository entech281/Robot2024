package entech.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StoppingCounter {

  private double targetCounts = 0;
  private double currentCounts = 0;
  private String name;

  public StoppingCounter(String name, double targetCounts) {
    this.name = name;
    this.targetCounts = targetCounts;
  }

  public boolean isFinished(boolean conditionToCheck) {
    if (conditionToCheck) {
      currentCounts++;
    } else {
      currentCounts = 0;
    }
    SmartDashboard.putNumber("StopCounter::" + name, currentCounts);
    return currentCounts > PeriodicLoopsPerSecond.getLoopsPerSecond(targetCounts);
  }

  public void reset() {
    this.currentCounts = 0;
  }
}
