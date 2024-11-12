package frc.robot.subsystems.drive;


import org.littletonrobotics.junction.Logger;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.entech.subsystems.SubsystemOutput;
import frc.robot.RobotConstants.SwerveModuleConstants;

public class DriveOutput extends SubsystemOutput {
  private SwerveModulePosition[] modulePositions;
  private double[] rawAbsoluteEncoders;
  private double[] virtualAbsoluteEncoders;
  private SwerveModuleState[] moduleStates;
  private ChassisSpeeds speeds;

  @Override
  public void toLog() {
    Logger.recordOutput("DriveOutput/modulePositions", modulePositions);
    Logger.recordOutput("DriveOutput/rawAbsoluteEncoders", rawAbsoluteEncoders);
    Logger.recordOutput("DriveOutput/virtualAbsoluteEncoders", virtualAbsoluteEncoders);
    Logger.recordOutput("DriveOutput/moduleStates", moduleStates);
    Logger.recordOutput("DriveOutput/chassisSpeed", speeds);
    Logger.recordOutput("DriveOutput/dynamicModuleSettings/driveP",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getDriveP());
    Logger.recordOutput("DriveOutput/dynamicModuleSettings/driveI",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getDriveI());
    Logger.recordOutput("DriveOutput/dynamicModuleSettings/driveD",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getDriveD());
    Logger.recordOutput("DriveOutput/dynamicModuleSettings/driveFF",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getDriveFeedForward());
    Logger.recordOutput("DriveOutput/dynamicModuleSettings/turningP",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getTurningP());
    Logger.recordOutput("DriveOutput/dynamicModuleSettings/turningI",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getTurningI());
    Logger.recordOutput("DriveOutput/dynamicModuleSettings/turningD",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getTurningD());
    Logger.recordOutput("DriveOutput/dynamicModuleSettings/turningFF",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getTurningFF());
    Logger.recordOutput("DriveOutput/dynamicModuleSettings/wheelDiameter",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getWheelDiameter());
    Logger.recordOutput("DriveOutput/dynamicModuleSettings/motorFreeRPM",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getFreeSpeedRPM());
    Logger.recordOutput("DriveOutput/dynamicModuleSettings/driveMotorFreeRPS",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getDriveMotorFreeRPS());
    Logger.recordOutput("DriveOutput/dynamicModuleSettings/turningMotorReduction",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getTurningMotorReduction());
    Logger.recordOutput("DriveOutput/dynamicModuleSettings/driveMotorReduction",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getDriveMotorReduction());
    Logger.recordOutput("DriveOutput/dynamicModuleSettings/wheelCircumference",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getWheelCircumferenceMeters());
    Logger.recordOutput(
        "DriveOutput/dynamicModuleSettings/driveEncoderPositionFactorMetersPerRotation",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS
            .getDriveEncoderPositionFactorMetersPerRotation());
    Logger.recordOutput(
        "DriveOutput/dynamicModuleSettings/driveEncoderVelocityFactorMetersPerSecondPerRPM",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS
            .getDriveEncoderVelocityFactorMetersPerSecondPerRPM());
    Logger.recordOutput(
        "DriveOutput/dynamicModuleSettings/turningEncoderPositionFactorRadiansPerRotation",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS
            .getTurningEncoderPositionFactorRadiansPerRotation());
    Logger.recordOutput(
        "DriveOutput/dynamicModuleSettings/turningEncoderPositionFactorRadiansPerSecondPerRPM",
        SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS
            .getTurningEncoderPositionFactorRadiansPerSecondPerRPM());
  }

  public SwerveModulePosition[] getModulePositions() {
    return this.modulePositions;
  }

  public void setModulePositions(SwerveModulePosition[] modulePositions) {
    this.modulePositions = modulePositions;
  }

  public double[] getRawAbsoluteEncoders() {
    return this.rawAbsoluteEncoders;
  }

  public void setRawAbsoluteEncoders(double[] rawAbsoluteEncoders) {
    this.rawAbsoluteEncoders = rawAbsoluteEncoders;
  }

  public double[] getVirtualAbsoluteEncoders() {
    return this.virtualAbsoluteEncoders;
  }

  public void setVirtualAbsoluteEncoders(double[] virtualAbsoluteEncoders) {
    this.virtualAbsoluteEncoders = virtualAbsoluteEncoders;
  }

  public SwerveModuleState[] getModuleStates() {
    return this.moduleStates;
  }

  public void setModuleStates(SwerveModuleState[] moduleStates) {
    this.moduleStates = moduleStates;
  }


  public ChassisSpeeds getSpeeds() {
    return this.speeds;
  }

  public void setSpeeds(ChassisSpeeds speeds) {
    this.speeds = speeds;
  }
}
