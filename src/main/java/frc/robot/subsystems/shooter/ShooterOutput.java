package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.Logger;
import entech.subsystems.SubsystemOutputBasics;

public class ShooterOutput extends SubsystemOutputBasics {

  private boolean isAtSpeed = false;
  private double speedA;
  private double speedB;

  public boolean isAtSpeed() {
    return isAtSpeed;
  }

  @Override
  public void toLog() {
    Logger.recordOutput("ShooterOutput/isAtSpeed", isAtSpeed);
    Logger.recordOutput("ShooterOutput/speedA", speedA);
    Logger.recordOutput("ShooterOutput/speedB", speedB);
  }

  public void setIsAtSpeed(boolean isAtSpeed) {
    this.isAtSpeed = isAtSpeed;
  }

  public double getSpeedA() {
    return this.speedA;
  }

  public void setSpeedA(double speedA) {
    this.speedA = speedA;
  }

  public double getSpeedB() {
    return this.speedB;
  }

  public void setSpeedB(double speedB) {
    this.speedB = speedB;
  }
}
