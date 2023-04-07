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
            RobotConstants.Swerve.angleEnableCurrentLimit, 
            RobotConstants.Swerve.angleContinuousCurrentLimit, 
            RobotConstants.Swerve.anglePeakCurrentLimit, 
            RobotConstants.Swerve.anglePeakCurrentDuration);

        swerveAngleFXConfig.slot0.kP = RobotConstants.Swerve.angleKP;
        swerveAngleFXConfig.slot0.kI = RobotConstants.Swerve.angleKI;
        swerveAngleFXConfig.slot0.kD = RobotConstants.Swerve.angleKD;
        swerveAngleFXConfig.slot0.kF = RobotConstants.Swerve.angleKF;
        swerveAngleFXConfig.supplyCurrLimit = angleSupplyLimit;

        /* Swerve Drive Motor Configuration */
        SupplyCurrentLimitConfiguration driveSupplyLimit = new SupplyCurrentLimitConfiguration(
            RobotConstants.Swerve.driveEnableCurrentLimit, 
            RobotConstants.Swerve.driveContinuousCurrentLimit, 
            RobotConstants.Swerve.drivePeakCurrentLimit, 
            RobotConstants.Swerve.drivePeakCurrentDuration);

        swerveDriveFXConfig.slot0.kP = RobotConstants.Swerve.driveKP;
        swerveDriveFXConfig.slot0.kI = RobotConstants.Swerve.driveKI;
        swerveDriveFXConfig.slot0.kD = RobotConstants.Swerve.driveKD;
        swerveDriveFXConfig.slot0.kF = RobotConstants.Swerve.driveKF;        
        swerveDriveFXConfig.supplyCurrLimit = driveSupplyLimit;
        swerveDriveFXConfig.openloopRamp = RobotConstants.Swerve.openLoopRamp;
        swerveDriveFXConfig.closedloopRamp = RobotConstants.Swerve.closedLoopRamp;
        
        /* Swerve CANCoder Configuration */
        swerveCanCoderConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        swerveCanCoderConfig.sensorDirection = RobotConstants.Swerve.canCoderInvert;
        swerveCanCoderConfig.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
        swerveCanCoderConfig.sensorTimeBase = SensorTimeBase.PerSecond;
    }
}