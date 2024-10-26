// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.drive;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.RobotConstants.SwerveModuleConstants;
import frc.robot.sensors.ThriftyEncoder;

/**
 * The {@code SwerveModule} class contains fields and methods pertaining to the function of a swerve
 * module.
 */
public class SwerveModule {
  private final CANSparkMax drivingSparkMax;
  private final CANSparkMax turningSparkMax;

  private final RelativeEncoder drivingEncoder;
  private final RelativeEncoder turningEncoder;
  private final ThriftyEncoder turningAbsoluteEncoder;

  private final SparkPIDController drivingPIDController;
  private final SparkPIDController turningPIDController;

  private DynamicSwerveModuleBaseData dynamicConfigData;
  private SwerveModuleState desiredState = new SwerveModuleState(0.0, new Rotation2d());

  /**
   * Constructs a SwerveModule and configures the driving and turning motor, encoder, and PID
   * controller.
   */
  public SwerveModule(int drivingCANId, int turningCANId, int turningAnalogPort,
      boolean drivingInverted) {
    drivingSparkMax = new CANSparkMax(drivingCANId, MotorType.kBrushless);
    turningSparkMax = new CANSparkMax(turningCANId, MotorType.kBrushless);

    // Factory reset, so we get the SPARKS MAX to a known state before configuring
    // them. This is useful in case a SPARK MAX is swapped out.
    drivingSparkMax.restoreFactoryDefaults();
    turningSparkMax.restoreFactoryDefaults();

    // Setup encoders and PID controllers for the driving and turning SPARKS MAX.
    drivingEncoder = drivingSparkMax.getEncoder();
    turningEncoder = turningSparkMax.getEncoder();
    turningAbsoluteEncoder = new ThriftyEncoder(turningAnalogPort);

    drivingPIDController = drivingSparkMax.getPIDController();
    turningPIDController = turningSparkMax.getPIDController();
    drivingPIDController.setFeedbackDevice(drivingEncoder);
    turningPIDController.setFeedbackDevice(turningEncoder);

    // Apply position and velocity conversion factors for the driving encoder. The
    // native units for position and velocity are rotations and RPM, respectively,
    // but we want meters and meters per second to use with WPILib's swerve APIs.
    drivingEncoder.setPositionConversionFactor(
        SwerveModuleConstants.DRIVING_ENCODER_POSITION_FACTOR_METERS_PER_ROTATION);
    drivingEncoder.setVelocityConversionFactor(
        SwerveModuleConstants.DRIVING_ENCODER_VELOCITY_FACTOR_METERS_PER_SECOND_PER_RPM);

    // Apply position and velocity conversion factors for the turning encoder. We
    // want these in radians and radians per second to use with WPILib's swerve
    // APIs.
    turningEncoder.setPositionConversionFactor(
        SwerveModuleConstants.TURNING_ENCODER_POSITION_FACTOR_RADIANS_PER_ROTATION);
    turningEncoder.setVelocityConversionFactor(
        SwerveModuleConstants.TURNING_ENCODER_VELOCITY_FACTOR_RADIANS_PER_SECOND_PER_RPM);

    // Invert the turning controller, since the output shaft rotates in the opposite
    // direction of
    // the steering motor.
    turningSparkMax.setInverted(true);
    drivingSparkMax.setInverted(drivingInverted);

    // Enable PID wrap around for the turning motor. This will allow the PID
    // controller to go through 0 to get to the setpoint i.e. going from 350 degrees
    // to 10 degrees will go through 0 rather than the other direction which is a
    // longer route.
    turningPIDController.setPositionPIDWrappingEnabled(true);
    turningPIDController.setPositionPIDWrappingMinInput(
        SwerveModuleConstants.TURNING_ENCODER_POSITION_PID_MIN_INPUT_RADIANS);
    turningPIDController.setPositionPIDWrappingMaxInput(
        SwerveModuleConstants.TURNING_ENCODER_POSITION_PID_MAX_INPUT_RADIANS);

    // Set the PID gains for the driving motor.
    drivingPIDController.setP(SwerveModuleConstants.DRIVING_P);
    drivingPIDController.setI(SwerveModuleConstants.DRIVING_I);
    drivingPIDController.setD(SwerveModuleConstants.DRIVING_D);
    drivingPIDController.setFF(SwerveModuleConstants.DRIVING_FF);
    drivingPIDController.setOutputRange(SwerveModuleConstants.DRIVING_MIN_OUTPUT_NORMALIZED,
        SwerveModuleConstants.DRIVING_MAX_OUTPUT_NORMALIZED);

    // Set the PID gains for the turning motor.
    turningPIDController.setP(SwerveModuleConstants.TURNING_P);
    turningPIDController.setI(SwerveModuleConstants.TURNING_I);
    turningPIDController.setD(SwerveModuleConstants.TURNING_D);
    turningPIDController.setFF(SwerveModuleConstants.TURNING_FF);
    turningPIDController.setOutputRange(SwerveModuleConstants.TURNING_MIN_OUTPUT_NORMALIZED,
        SwerveModuleConstants.TURNING_MAX_OUTPUT_NORMALIZED);

    drivingSparkMax.setIdleMode(SwerveModuleConstants.DRIVING_MOTOR_IDLE_MODE);
    turningSparkMax.setIdleMode(SwerveModuleConstants.TURNING_MOTOR_IDLE_MODE);
    drivingSparkMax.setSmartCurrentLimit(SwerveModuleConstants.DRIVING_MOTOR_CURRENT_LIMIT_AMPS);
    turningSparkMax.setSmartCurrentLimit(SwerveModuleConstants.TURNING_MOTOR_CURRENT_LIMIT_AMPS);

    // Save the SPARK MAX configurations. If a SPARK MAX browns out during
    // operation, it will maintain the above configurations.
    drivingSparkMax.burnFlash();
    turningSparkMax.burnFlash();

    desiredState.angle = new Rotation2d(turningEncoder.getPosition());
    drivingEncoder.setPosition(0);
    dynamicConfigData = SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getDynamicData();
  }

  /**
   * Returns the current state of the module.
   *
   * @return The current state of the module.
   */
  public SwerveModuleState getState() {
    return new SwerveModuleState(drivingEncoder.getVelocity(),
        new Rotation2d(turningEncoder.getPosition()));
  }

  /**
   * Returns the current position of the module.
   *
   * @return The current position of the module.
   */
  public SwerveModulePosition getPosition() {
    return new SwerveModulePosition(drivingEncoder.getPosition(),
        new Rotation2d(turningEncoder.getPosition()));
  }

  public void periodicConfigurationCheck() {
    if (!SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.isDynamicDataValid(dynamicConfigData)) {
      if (!SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.isDrivePIDValid(dynamicConfigData)) {
        drivingPIDController.setP(SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getDriveP());
        drivingPIDController.setI(SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getDriveI());
        drivingPIDController.setD(SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getDriveD());
      }
      if (!SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.isTurningPIDValid(dynamicConfigData)) {
        turningPIDController.setP(SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getTurningP());
        turningPIDController.setI(SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getTurningI());
        turningPIDController.setD(SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getTurningD());
        turningPIDController.setFF(SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getTurningFF());
      }
      if (!SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS
          .isCalculationBlockValid(dynamicConfigData)) {
        drivingPIDController
            .setFF(SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS.getDriveFeedForward());
        drivingEncoder.setPositionConversionFactor(SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS
            .getDriveEncoderPositionFactorMetersPerRotation());
        drivingEncoder.setVelocityConversionFactor(SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS
            .getDriveEncoderVelocityFactorMetersPerSecondPerRPM());
        turningEncoder.setPositionConversionFactor(SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS
            .getTurningEncoderPositionFactorRadiansPerRotation());
        turningEncoder.setVelocityConversionFactor(SwerveModuleConstants.DYNAMIC_MODULE_SETTINGS
            .getTurningEncoderPositionFactorRadiansPerSecondPerRPM());
      }
    }
  }

  /**
   * Sets the desired state for the module.
   *
   * @param desiredState Desired state with speed and angle.
   */
  public void setDesiredState(SwerveModuleState desiredState) {
    // Apply chassis angular offset to the desired state.
    SwerveModuleState correctedDesiredState = new SwerveModuleState();
    correctedDesiredState.speedMetersPerSecond = desiredState.speedMetersPerSecond;
    correctedDesiredState.angle = desiredState.angle;

    // Optimize the reference state to avoid spinning further than 90 degrees.
    SwerveModuleState optimizedDesiredState = SwerveModuleState.optimize(correctedDesiredState,
        new Rotation2d(turningEncoder.getPosition()));

    if (Math.abs(optimizedDesiredState.speedMetersPerSecond) < 0.001 // less than 1 mm per sec
        && Math.abs(optimizedDesiredState.angle.getRadians() - turningEncoder.getPosition()) < 0.1) // 10%
                                                                                                    // of
    // a
    // radian
    {
      drivingSparkMax.set(0); // no point in doing anything
      turningSparkMax.set(0);
    } else {
      // Command driving and turning SPARKS MAX towards their respective setpoints.
      drivingPIDController.setReference(optimizedDesiredState.speedMetersPerSecond,
          ControlType.kVelocity);
      turningPIDController.setReference(optimizedDesiredState.angle.getRadians(),
          ControlType.kPosition);
    }

    this.desiredState = desiredState;
  }

  /** Zeroes all the SwerveModule relative encoders. */
  public void resetEncoders() {

    drivingEncoder.setPosition(0); // arbitrarily set driving encoder to zero

    resetTurningEncoder();
  }

  public void resetTurningEncoder() {
    turningSparkMax.set(0); // no moving during reset of relative turning encoder

    turningEncoder.setPosition(turningAbsoluteEncoder.getVirtualPosition()); // set relative
                                                                             // position based
                                                                             // on
    // virtual absolute position
  }

  /**
   * Calibrates the virtual position (i.e. sets position offset) of the absolute encoder.
   */
  public void calibrateVirtualPosition(double angle) {
    turningAbsoluteEncoder.setPositionOffset(angle);
  }

  public RelativeEncoder getDrivingEncoder() {
    return drivingEncoder;
  }

  public RelativeEncoder getTurningEncoder() {
    return turningEncoder;
  }

  public ThriftyEncoder getTurningAbsoluteEncoder() {
    return turningAbsoluteEncoder;
  }

  public SwerveModuleState getDesiredState() {
    return desiredState;
  }

}
