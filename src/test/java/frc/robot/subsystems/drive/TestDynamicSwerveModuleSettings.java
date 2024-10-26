package frc.robot.subsystems.drive;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import frc.robot.RobotConstants;

class TestDynamicSwerveModuleSettings {
  @Test
  void testCalculations() {
    assertEquals(
        RobotConstants.SwerveModuleConstants.TURNING_ENCODER_POSITION_FACTOR_RADIANS_PER_ROTATION,
        RobotConstants.SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS
            .getTurningEncoderPositionFactorRadiansPerRotation());
    assertEquals(
        RobotConstants.SwerveModuleConstants.TURNING_ENCODER_VELOCITY_FACTOR_RADIANS_PER_SECOND_PER_RPM,
        RobotConstants.SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS
            .getTurningEncoderPositionFactorRadiansPerSecondPerRPM());
    assertEquals(
        RobotConstants.SwerveModuleConstants.DRIVING_ENCODER_POSITION_FACTOR_METERS_PER_ROTATION,
        RobotConstants.SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS
            .getDriveEncoderPositionFactorMetersPerRotation());
    assertEquals(
        RobotConstants.SwerveModuleConstants.DRIVING_ENCODER_VELOCITY_FACTOR_METERS_PER_SECOND_PER_RPM,
        RobotConstants.SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS
            .getDriveEncoderVelocityFactorMetersPerSecondPerRPM());
    assertEquals(RobotConstants.SwerveModuleConstants.DRIVE_WHEEL_FREE_SPEED_RPS,
        RobotConstants.SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getDriveWheelFreeSpeed());
    assertEquals(RobotConstants.SwerveModuleConstants.DRIVING_MOTOR_FREE_SPEED_RPS,
        RobotConstants.SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getDriveMotorFreeRPS());
    assertEquals(RobotConstants.SwerveModuleConstants.DRIVING_FF,
        RobotConstants.SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getDriveFeedForward());
    assertEquals(RobotConstants.SwerveModuleConstants.WHEEL_CIRCUMFERENCE_METERS,
        RobotConstants.SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getWheelCircumferenceMeters());
  }
}
