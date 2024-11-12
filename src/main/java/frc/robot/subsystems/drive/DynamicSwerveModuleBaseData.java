package frc.robot.subsystems.drive;

import java.util.Objects;

public class DynamicSwerveModuleBaseData {
  private double turningP;
  private double turningI;
  private double turningD;
  private double turningFF;
  private double driveP;
  private double driveI;
  private double driveD;
  private double driveFF;

  private double freeSpeedRPM;
  private double driveMotorReduction;
  private double turningMotorReduction;
  private double wheelDiameter;

  private double driveMotorFreeRPS;
  private double wheelCircumferenceMeters;
  private double driveWheelFreeSpeed;
  private double driveEncoderPositionFactorMetersPerRotation;
  private double driveEncoderVelocityFactorMetersPerSecondPerRPM;
  private double turningEncoderPositionFactorRadiansPerRotation;
  private double turningEncoderPositionFactorRadiansPerSecondPerRPM;

  public DynamicSwerveModuleBaseData(double turningP, double turningI, double turningD,
      double turningFF, double driveP, double driveI, double driveD, double driveFF,
      double freeSpeedRPM, double driveMotorReduction, double turningMotorReduction,
      double wheelDiameter, double driveMotorFreeRPS, double wheelCircumferenceMeters,
      double driveWheelFreeSpeed, double driveEncoderPositionFactorMetersPerRotation,
      double driveEncoderVelocityFactorMetersPerSecondPerRPM,
      double turningEncoderPositionFactorRadiansPerRotation,
      double turningEncoderPositionFactorRadiansPerSecondPerRPM) {
    this.turningP = turningP;
    this.turningI = turningI;
    this.turningD = turningD;
    this.turningFF = turningFF;
    this.driveP = driveP;
    this.driveI = driveI;
    this.driveD = driveD;
    this.driveFF = driveFF;
    this.freeSpeedRPM = freeSpeedRPM;
    this.driveMotorReduction = driveMotorReduction;
    this.turningMotorReduction = turningMotorReduction;
    this.wheelDiameter = wheelDiameter;
    this.driveMotorFreeRPS = driveMotorFreeRPS;
    this.wheelCircumferenceMeters = wheelCircumferenceMeters;
    this.driveWheelFreeSpeed = driveWheelFreeSpeed;
    this.driveEncoderPositionFactorMetersPerRotation = driveEncoderPositionFactorMetersPerRotation;
    this.driveEncoderVelocityFactorMetersPerSecondPerRPM =
        driveEncoderVelocityFactorMetersPerSecondPerRPM;
    this.turningEncoderPositionFactorRadiansPerRotation =
        turningEncoderPositionFactorRadiansPerRotation;
    this.turningEncoderPositionFactorRadiansPerSecondPerRPM =
        turningEncoderPositionFactorRadiansPerSecondPerRPM;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof DynamicSwerveModuleBaseData)) {
      return false;
    }
    DynamicSwerveModuleBaseData dynamicSwerveModuleBaseData = (DynamicSwerveModuleBaseData) o;
    return turningP == dynamicSwerveModuleBaseData.turningP
        && turningI == dynamicSwerveModuleBaseData.turningI
        && turningD == dynamicSwerveModuleBaseData.turningD
        && turningFF == dynamicSwerveModuleBaseData.turningFF
        && driveP == dynamicSwerveModuleBaseData.driveP
        && driveI == dynamicSwerveModuleBaseData.driveI
        && driveD == dynamicSwerveModuleBaseData.driveD
        && driveFF == dynamicSwerveModuleBaseData.driveFF
        && freeSpeedRPM == dynamicSwerveModuleBaseData.freeSpeedRPM
        && driveMotorReduction == dynamicSwerveModuleBaseData.driveMotorReduction
        && turningMotorReduction == dynamicSwerveModuleBaseData.turningMotorReduction
        && wheelDiameter == dynamicSwerveModuleBaseData.wheelDiameter
        && driveMotorFreeRPS == dynamicSwerveModuleBaseData.driveMotorFreeRPS
        && wheelCircumferenceMeters == dynamicSwerveModuleBaseData.wheelCircumferenceMeters
        && driveWheelFreeSpeed == dynamicSwerveModuleBaseData.driveWheelFreeSpeed
        && driveEncoderPositionFactorMetersPerRotation == dynamicSwerveModuleBaseData.driveEncoderPositionFactorMetersPerRotation
        && driveEncoderVelocityFactorMetersPerSecondPerRPM == dynamicSwerveModuleBaseData.driveEncoderVelocityFactorMetersPerSecondPerRPM
        && turningEncoderPositionFactorRadiansPerRotation == dynamicSwerveModuleBaseData.turningEncoderPositionFactorRadiansPerRotation
        && turningEncoderPositionFactorRadiansPerSecondPerRPM == dynamicSwerveModuleBaseData.turningEncoderPositionFactorRadiansPerSecondPerRPM;
  }

  @Override
  public int hashCode() {
    return Objects.hash(turningP, turningI, turningD, turningFF, driveP, driveI, driveD, driveFF,
        freeSpeedRPM, driveMotorReduction, turningMotorReduction, wheelDiameter, driveMotorFreeRPS,
        wheelCircumferenceMeters, driveWheelFreeSpeed, driveEncoderPositionFactorMetersPerRotation,
        driveEncoderVelocityFactorMetersPerSecondPerRPM,
        turningEncoderPositionFactorRadiansPerRotation,
        turningEncoderPositionFactorRadiansPerSecondPerRPM);
  }

  public double getTurningP() {
    return this.turningP;
  }

  public double getTurningI() {
    return this.turningI;
  }

  public double getTurningD() {
    return this.turningD;
  }

  public double getTurningFF() {
    return this.turningFF;
  }

  public double getDriveP() {
    return this.driveP;
  }

  public double getDriveI() {
    return this.driveI;
  }

  public double getDriveD() {
    return this.driveD;
  }

  public double getDriveFF() {
    return this.driveFF;
  }

  public double getFreeSpeedRPM() {
    return this.freeSpeedRPM;
  }

  public double getDriveMotorReduction() {
    return this.driveMotorReduction;
  }

  public double getTurningMotorReduction() {
    return this.turningMotorReduction;
  }

  public double getWheelDiameter() {
    return this.wheelDiameter;
  }

  public double getDriveMotorFreeRPS() {
    return this.driveMotorFreeRPS;
  }

  public double getWheelCircumferenceMeters() {
    return this.wheelCircumferenceMeters;
  }

  public double getDriveWheelFreeSpeed() {
    return this.driveWheelFreeSpeed;
  }

  public double getDriveEncoderPositionFactorMetersPerRotation() {
    return this.driveEncoderPositionFactorMetersPerRotation;
  }

  public double getDriveEncoderVelocityFactorMetersPerSecondPerRPM() {
    return this.driveEncoderVelocityFactorMetersPerSecondPerRPM;
  }

  public double getTurningEncoderPositionFactorRadiansPerRotation() {
    return this.turningEncoderPositionFactorRadiansPerRotation;
  }

  public double getTurningEncoderPositionFactorRadiansPerSecondPerRPM() {
    return this.turningEncoderPositionFactorRadiansPerSecondPerRPM;
  }
}
