package frc.robot.subsystems.shooter;

import entech.subsystems.SubsystemOutputBasics;

public class ShooterOutput extends SubsystemOutputBasics {

  private boolean isAtSpeed = false;

  public boolean isAtSpeed() {
    return isAtSpeed;
  }

  public void setIsAtSpeed(boolean isAtSpeed) {
    this.isAtSpeed = isAtSpeed;
  }
}
