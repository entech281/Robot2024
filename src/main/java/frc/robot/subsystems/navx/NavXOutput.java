package frc.robot.subsystems.navx;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import entech.subsystems.SubsystemOutput;
import org.littletonrobotics.junction.Logger;

public class NavXOutput implements SubsystemOutput {
  public double yaw;
  public double pitch;
  public double roll;
  public double yawRate;
  public double zVelocity;
  public double temperature;
  public double compassHeading;
  public double angleAdjustment;

  public boolean isCalibrating;
  public boolean isMoving;
  public boolean isRotating;
  public boolean isMagnetometerCalibrated;
  public boolean isMagneticDisturbance;

  public ChassisSpeeds chassisSpeeds;

  @Override
  public void log() {
    Logger.recordOutput("navXOutput/yaw", yaw);
    Logger.recordOutput("navXOutput/pitch", pitch);
    Logger.recordOutput("navXOutput/roll", roll);
    Logger.recordOutput("navXOutput/yawRate", yawRate);
    Logger.recordOutput("navXOutput/zVelocity", zVelocity);
    Logger.recordOutput("navXOutput/temperature", temperature);
    Logger.recordOutput("navXOutput/compassHeading", compassHeading);
    Logger.recordOutput("navXOutput/angleAdjustment", angleAdjustment);
    Logger.recordOutput("navXOutput/isCalibrating", isCalibrating);
    Logger.recordOutput("navXOutput/isMoving", isMoving);
    Logger.recordOutput("navXOutput/isMoving", isRotating);
    Logger.recordOutput("navXOutput/isMagnetometerCalibrated", isMagnetometerCalibrated);
    Logger.recordOutput("navXOutput/isMagneticDisturbance", isMagneticDisturbance);
    Logger.recordOutput("navXOutput/chassisSpeeds", compassHeading);
  }
}
