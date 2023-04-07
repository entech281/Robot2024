package frc.lib;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.sensors.SensorTimeBase;

import frc.robot.RobotConstants;

public final class CTREConfigs {
    public TalonFXConfiguration swerveAngleFXConfig;
    public TalonFXConfiguration swerveDriveFXConfig;
    public CANCoderConfiguration swerveCanCoderConfig;

    public CTREConfigs(){
        swerveAngleFXConfig = new TalonFXConfiguration();
        swerveDriveFXConfig = new TalonFXConfiguration();
        swerveCanCoderConfig = new CANCoderConfiguration();

        /* Swerve Angle Motor Configurations */
        SupplyCurrentLimitConfiguration angleSupplyLimit = new SupplyCurrentLimitConfiguration(
            RobotConstants.Swerve.ANGLE_ENABLE_CURRENT_LIMIT, 
            RobotConstants.Swerve.ANGLE_CONTINUOUS_CURRENT_LIMIT, 
            RobotConstants.Swerve.ANGLE_PEAK_CURRENT_LIMIT, 
            RobotConstants.Swerve.ANGLE_PEAK_CURRENT_DURATION);

        swerveAngleFXConfig.slot0.kP = RobotConstants.Swerve.ANGLE_KP;
        swerveAngleFXConfig.slot0.kI = RobotConstants.Swerve.ANGLE_KI;
        swerveAngleFXConfig.slot0.kD = RobotConstants.Swerve.ANGLE_KD;
        swerveAngleFXConfig.slot0.kF = RobotConstants.Swerve.ANGLE_KF;
        swerveAngleFXConfig.supplyCurrLimit = angleSupplyLimit;

        /* Swerve Drive Motor Configuration */
        SupplyCurrentLimitConfiguration driveSupplyLimit = new SupplyCurrentLimitConfiguration(
            RobotConstants.Swerve.DRIVE_ENABLE_CURRENT_LIMIT, 
            RobotConstants.Swerve.DRIVE_CONTINUOUS_CURRENT_LIMIT, 
            RobotConstants.Swerve.DRIVE_PEAK_CURRENT_LIMIT, 
            RobotConstants.Swerve.DRIVE_PEAK_CURRENT_DURATION);

        swerveDriveFXConfig.slot0.kP = RobotConstants.Swerve.DRIVE_KP;
        swerveDriveFXConfig.slot0.kI = RobotConstants.Swerve.DRIVE_KI;
        swerveDriveFXConfig.slot0.kD = RobotConstants.Swerve.DRIVE_KD;
        swerveDriveFXConfig.slot0.kF = RobotConstants.Swerve.DRIVE_KF;        
        swerveDriveFXConfig.supplyCurrLimit = driveSupplyLimit;
        swerveDriveFXConfig.openloopRamp = RobotConstants.Swerve.OPEN_LOOP_RAMP;
        swerveDriveFXConfig.closedloopRamp = RobotConstants.Swerve.CLOSED_LOOP_RAMP;
        
        /* Swerve CANCoder Configuration */
        swerveCanCoderConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        swerveCanCoderConfig.sensorDirection = RobotConstants.Swerve.CAN_CODER_INVERT;
        swerveCanCoderConfig.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
        swerveCanCoderConfig.sensorTimeBase = SensorTimeBase.PerSecond;
    }
}