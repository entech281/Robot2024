package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.Logger;
import entech.subsystems.SubsystemOutputBasics;

public class ShooterOutput extends SubsystemOutputBasics {

  private boolean isAtSpeed = false;

  public boolean isAtSpeed() {
    return isAtSpeed;
  }

  @Override
  public void toLog() {
    super.toLog();
    Logger.recordOutput("ShooterOutput/isAtSpeed", isAtSpeed);
  }



  public void setIsAtSpeed(boolean isAtSpeed) {
    this.isAtSpeed = isAtSpeed;
  }
}
