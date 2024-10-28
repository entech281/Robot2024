package frc.robot.subsystems.drive;

import frc.robot.RobotConstants;

public class DynamicSwerveModuleSettings {
  private double turningP = RobotConstants.SwerveModuleConstants.TURNING_P;
  private double turningI = RobotConstants.SwerveModuleConstants.TURNING_I;
  private double turningD = RobotConstants.SwerveModuleConstants.TURNING_D;
  private double turningFF = RobotConstants.SwerveModuleConstants.TURNING_FF;
  private double driveP = RobotConstants.SwerveModuleConstants.DRIVING_P;
  private double driveI = RobotConstants.SwerveModuleConstants.DRIVING_I;
  private double driveD = RobotConstants.SwerveModuleConstants.DRIVING_D;

  private double freeSpeedRPM = RobotConstants.SwerveModuleConstants.FREE_SPEED_RPM;
  private double driveMotorReduction = RobotConstants.SwerveModuleConstants.DRIVING_MOTOR_REDUCTION;
  private double turningMotorReduction =
      RobotConstants.SwerveModuleConstants.TURNING_MOTOR_REDUCTION;
  private double wheelDiameter = RobotConstants.SwerveModuleConstants.WHEEL_DIAMETER_METERS;

  /**
   * Automatically grabs default values from {@link RobotConstants}.
   */
  public DynamicSwerveModuleSettings() {}

  public boolean isDynamicDataValid(DynamicSwerveModuleBaseData moduleData) {
    return getDynamicData().equals(moduleData);
  }

  public boolean isTurningPIDValid(DynamicSwerveModuleBaseData moduleData) {
    return moduleData.getTurningP() == turningP && moduleData.getTurningI() == turningI
        && moduleData.getTurningD() == turningD && moduleData.getTurningFF() == turningFF;
  }

  public boolean isDrivePIDValid(DynamicSwerveModuleBaseData moduleData) {
    return moduleData.getDriveP() == driveP && moduleData.getDriveI() == driveI
        && moduleData.getDriveD() == driveD;
  }

  public boolean isCalculationBlockValid(DynamicSwerveModuleBaseData moduleData) {
    return moduleData.getDriveMotorReduction() == driveMotorReduction
        && moduleData.getTurningMotorReduction() == turningMotorReduction
        && moduleData.getFreeSpeedRPM() == freeSpeedRPM
        && moduleData.getWheelDiameter() == wheelDiameter;
  }

  public DynamicSwerveModuleBaseData getDynamicData() {
    return new DynamicSwerveModuleBaseData(turningP, turningI, turningD, turningFF, driveP, driveI,
        driveD, getDriveFeedForward(), freeSpeedRPM, driveMotorReduction, turningMotorReduction,
        wheelDiameter, getDriveMotorFreeRPS(), getWheelCircumferenceMeters(),
        getDriveWheelFreeSpeed(), getDriveEncoderPositionFactorMetersPerRotation(),
        getDriveEncoderVelocityFactorMetersPerSecondPerRPM(),
        getTurningEncoderPositionFactorRadiansPerRotation(),
        getTurningEncoderPositionFactorRadiansPerSecondPerRPM());
  }

  public double getDriveMotorFreeRPS() {
    return freeSpeedRPM / 60;
  }

  public double getWheelCircumferenceMeters() {
    return wheelDiameter * Math.PI;
  }

  public double getDriveWheelFreeSpeed() {
    return (getDriveMotorFreeRPS() * getWheelCircumferenceMeters()) / driveMotorReduction;
  }

  public double getDriveEncoderPositionFactorMetersPerRotation() {
    return (wheelDiameter * Math.PI) / driveMotorReduction;
  }

  public double getDriveEncoderVelocityFactorMetersPerSecondPerRPM() {
    return ((wheelDiameter * Math.PI) / driveMotorReduction) / 60.0;
  }

  public double getDriveFeedForward() {
    return 1 / getDriveWheelFreeSpeed();
  }

  public double getTurningEncoderPositionFactorRadiansPerRotation() {
    return (2 * Math.PI) / turningMotorReduction;
  }

  public double getTurningEncoderPositionFactorRadiansPerSecondPerRPM() {
    return (2 * Math.PI) / turningMotorReduction / 60;
  }

  public double getTurningP() {
    return this.turningP;
  }

  public void setTurningP(double turningP) {
    this.turningP = turningP;
  }

  public double getTurningI() {
    return this.turningI;
  }

  public void setTurningI(double turningI) {
    this.turningI = turningI;
  }

  public double getTurningD() {
    return this.turningD;
  }

  public void setTurningD(double turningD) {
    this.turningD = turningD;
  }

  public double getTurningFF() {
    return this.turningFF;
  }

  public void setTurningFF(double turningFF) {
    this.turningFF = turningFF;
  }

  public double getDriveP() {
    return this.driveP;
  }

  public void setDriveP(double driveP) {
    this.driveP = driveP;
  }

  public double getDriveI() {
    return this.driveI;
  }

  public void setDriveI(double driveI) {
    this.driveI = driveI;
  }

  public double getDriveD() {
    return this.driveD;
  }

  public void setDriveD(double driveD) {
    this.driveD = driveD;
  }

  public double getFreeSpeedRPM() {
    return this.freeSpeedRPM;
  }

  public void setFreeSpeedRPM(double freeSpeedRPM) {
    this.freeSpeedRPM = freeSpeedRPM;
  }

  public double getDriveMotorReduction() {
    return this.driveMotorReduction;
  }

  public void setDriveMotorReduction(double driveMotorReduction) {
    this.driveMotorReduction = driveMotorReduction;
  }

  public double getTurningMotorReduction() {
    return this.turningMotorReduction;
  }

  public void setTurningMotorReduction(double turningMotorReduction) {
    this.turningMotorReduction = turningMotorReduction;
  }

  public double getWheelDiameter() {
    return this.wheelDiameter;
  }

  public void setWheelDiameter(double wheelDiameter) {
    this.wheelDiameter = wheelDiameter;
  }
}
