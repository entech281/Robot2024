package frc.robot.subsystems.navx;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import entech.subsystems.SubsystemOutput;

public class NavXOutput extends SubsystemOutput {
  private double yaw;
  private double pitch;
  private double roll;
  private double yawRate;
  private double zVelocity;
  private double temperature;
  private double compassHeading;
  private double angleAdjustment;

  private boolean isCalibrating;
  private boolean isMoving;
  private boolean isRotating;
  private boolean isMagnetometerCalibrated;
  private boolean isMagneticDisturbance;

  private boolean isFaultDetected;

  private ChassisSpeeds chassisSpeeds;

  @Override
  public void toLog() {
    Logger.recordOutput("NavXOutput/yaw", yaw);
    Logger.recordOutput("NavXOutput/pitch", pitch);
    Logger.recordOutput("NavXOutput/roll", roll);
    Logger.recordOutput("NavXOutput/yawRate", yawRate);
    Logger.recordOutput("NavXOutput/zVelocity", zVelocity);
    Logger.recordOutput("NavXOutput/temperature", temperature);
    Logger.recordOutput("NavXOutput/compassHeading", compassHeading);
    Logger.recordOutput("NavXOutput/angleAdjustment", angleAdjustment);
    Logger.recordOutput("NavXOutput/isCalibrating", isCalibrating);
    Logger.recordOutput("NavXOutput/isMoving", isMoving);
    Logger.recordOutput("NavXOutput/isMoving", isRotating);
    Logger.recordOutput("NavXOutput/isMagnetometerCalibrated", isMagnetometerCalibrated);
    Logger.recordOutput("NavXOutput/isMagneticDisturbance", isMagneticDisturbance);
    Logger.recordOutput("NavXOutput/chassisSpeeds", chassisSpeeds);
    Logger.recordOutput("NavXOutput/ifFaultDetected", isFaultDetected);
  }


  public double getYaw() {
    return this.yaw;
  }

  public void setYaw(double yaw) {
    this.yaw = yaw;
  }

  public double getPitch() {
    return this.pitch;
  }

  public void setPitch(double pitch) {
    this.pitch = pitch;
  }

  public double getRoll() {
    return this.roll;
  }

  public void setRoll(double roll) {
    this.roll = roll;
  }

  public double getYawRate() {
    return this.yawRate;
  }

  public void setYawRate(double yawRate) {
    this.yawRate = yawRate;
  }

  public double getZVelocity() {
    return this.zVelocity;
  }

  public void setZVelocity(double zVelocity) {
    this.zVelocity = zVelocity;
  }

  public double getTemperature() {
    return this.temperature;
  }

  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  public double getCompassHeading() {
    return this.compassHeading;
  }

  public void setCompassHeading(double compassHeading) {
    this.compassHeading = compassHeading;
  }

  public double getAngleAdjustment() {
    return this.angleAdjustment;
  }

  public void setAngleAdjustment(double angleAdjustment) {
    this.angleAdjustment = angleAdjustment;
  }

  public boolean isIsCalibrating() {
    return this.isCalibrating;
  }

  public boolean getIsCalibrating() {
    return this.isCalibrating;
  }

  public void setIsCalibrating(boolean isCalibrating) {
    this.isCalibrating = isCalibrating;
  }

  public boolean isIsMoving() {
    return this.isMoving;
  }

  public boolean getIsMoving() {
    return this.isMoving;
  }

  public void setIsMoving(boolean isMoving) {
    this.isMoving = isMoving;
  }

  public boolean isIsRotating() {
    return this.isRotating;
  }

  public boolean getIsRotating() {
    return this.isRotating;
  }

  public void setIsRotating(boolean isRotating) {
    this.isRotating = isRotating;
  }

  public boolean isIsMagnetometerCalibrated() {
    return this.isMagnetometerCalibrated;
  }

  public boolean getIsMagnetometerCalibrated() {
    return this.isMagnetometerCalibrated;
  }

  public void setIsMagnetometerCalibrated(boolean isMagnetometerCalibrated) {
    this.isMagnetometerCalibrated = isMagnetometerCalibrated;
  }

  public boolean isIsMagneticDisturbance() {
    return this.isMagneticDisturbance;
  }

  public boolean getIsMagneticDisturbance() {
    return this.isMagneticDisturbance;
  }

  public void setIsMagneticDisturbance(boolean isMagneticDisturbance) {
    this.isMagneticDisturbance = isMagneticDisturbance;
  }

  public ChassisSpeeds getChassisSpeeds() {
    return this.chassisSpeeds;
  }

  public void setChassisSpeeds(ChassisSpeeds chassisSpeeds) {
    this.chassisSpeeds = chassisSpeeds;
  }

  public boolean isFaultDetected() {
    return this.isFaultDetected;
  }

  public void setIsFaultDetected(boolean isFaultDetected) {
    this.isFaultDetected = isFaultDetected;
  }
}
