// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import frc.robot.RobotConstants.DrivetrainConstants;
import frc.robot.swerve.SwerveModule;
import frc.robot.swerve.SwerveUtils;

/**
 * The {@code Drivetrain} class contains fields and methods pertaining to the
 * function of the drivetrain.
 */
public class DriveSubsystem extends EntechSubsystem<DriveInput, DriveOutput> {
    private static final boolean ENABLED = true;

    public static final double FRONT_LEFT_VIRTUAL_OFFSET_RADIANS = -0.6493782167825488;
    public static final double FRONT_RIGHT_VIRTUAL_OFFSET_RADIANS = -0.44890044829984976;
    public static final double REAR_LEFT_VIRTUAL_OFFSET_RADIANS = -1.3180966246712007;
    public static final double REAR_RIGHT_VIRTUAL_OFFSET_RADIANS = -0.832907157277369;

    public static final int GYRO_ORIENTATION = 1; // might be able to merge with kGyroReversed

    
    private SwerveModule frontLeft;
    private SwerveModule frontRight;
    private SwerveModule rearLeft;
    private SwerveModule rearRight;

    private double currentTranslationDir = 0.0;
    private double currentTranslationMag = 0.0;

    private SlewRateLimiter magLimiter = new SlewRateLimiter(DrivetrainConstants.MAGNITUDE_SLEW_RATE);
    private SlewRateLimiter rotLimiter = new SlewRateLimiter(DrivetrainConstants.ROTATIONAL_SLEW_RATE);
    private double prevTime = WPIUtilJNI.now() * 1e-6;

    @Override
    public void updateInputs(DriveInput input){
        if (ENABLED) {
            SmartDashboard.putNumberArray("Input Data", new Double[] {input.xSpeed, input.ySpeed, input.rot});
            double xSpeedCommanded;
            double ySpeedCommanded;
            double currentRotation;

            if (RobotConstants.DrivetrainConstants.RATE_LIMITING) {
                // Convert XY to polar for rate limiting
                double inputTranslationDir = Math.atan2(input.ySpeed, input.xSpeed);
                double inputTranslationMag = Math.sqrt(Math.pow(input.xSpeed, 2) + Math.pow(input.ySpeed, 2));

                // Calculate the direction slew rate based on an estimate of the lateral
                // acceleration
                double directionSlewRate;

                if (currentTranslationMag != 0.0) {
                    directionSlewRate = Math.abs(DrivetrainConstants.DIRECTION_SLEW_RATE / currentTranslationMag);
                } else {
                    directionSlewRate = 500.0; // some high number that means the slew rate is effectively instantaneous
                }

                double currentTime = WPIUtilJNI.now() * 1e-6;
                double elapsedTime = currentTime - prevTime;
                double angleDif = SwerveUtils.AngleDifference(inputTranslationDir, currentTranslationDir);

                if (angleDif < 0.45 * Math.PI) {
                    currentTranslationDir = SwerveUtils.StepTowardsCircular(currentTranslationDir,
                            inputTranslationDir,
                            directionSlewRate * elapsedTime);
                    currentTranslationMag = magLimiter.calculate(inputTranslationMag);
                } else if (angleDif > 0.85 * Math.PI) {
                    if (currentTranslationMag > 1e-4) {
                        currentTranslationMag = magLimiter.calculate(0.0);
                    } else {
                        currentTranslationDir = SwerveUtils.WrapAngle(currentTranslationDir + Math.PI);
                        currentTranslationMag = magLimiter.calculate(inputTranslationMag);
                    }
                } else {
                    currentTranslationDir = SwerveUtils.StepTowardsCircular(currentTranslationDir,
                            inputTranslationDir,
                            directionSlewRate * elapsedTime);
                    currentTranslationMag = magLimiter.calculate(0.0);
                }

                prevTime = currentTime;

                xSpeedCommanded = currentTranslationMag * Math.cos(currentTranslationDir);
                ySpeedCommanded = currentTranslationMag * Math.sin(currentTranslationDir);
                currentRotation = rotLimiter.calculate(input.rot);

            } else {
                xSpeedCommanded = input.xSpeed;
                ySpeedCommanded = input.ySpeed;
                currentRotation = input.rot;
            }

            // Convert the commanded speeds into the correct units for the drivetrain
            double xSpeedDelivered = xSpeedCommanded * DrivetrainConstants.MAX_SPEED_METERS_PER_SECOND;
            double ySpeedDelivered = ySpeedCommanded * DrivetrainConstants.MAX_SPEED_METERS_PER_SECOND;
            double rotDelivered = currentRotation * DrivetrainConstants.MAX_ANGULAR_SPEED_RADIANS_PER_SECOND;

            SwerveModuleState[] swerveModuleStates = DrivetrainConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered,
                                    input.gyroAngle));

            setModuleStates(swerveModuleStates);
        }
    }

    @Override
    public DriveOutput getOutputs() {
        DriveOutput output = new DriveOutput();
        output.modulePositions = getModulePositions();
        return output;
    }

    private SwerveModulePosition[] getModulePositions() {
        return new SwerveModulePosition[] {
            frontLeft.getPosition(),
            frontRight.getPosition(),
            rearLeft.getPosition(),
            rearRight.getPosition()
        };
    }

    Field2d field = new Field2d();

    @Override
    public void periodic() {
        if (ENABLED) {
            SmartDashboard.putNumberArray("modules pose angles", new double[] {
                    frontLeft.getPosition().angle.getDegrees(),
                    frontRight.getPosition().angle.getDegrees(),
                    rearLeft.getPosition().angle.getDegrees(),
                    rearRight.getPosition().angle.getDegrees()
            });
            SmartDashboard.putNumberArray("modules pose meters", new double[] {
                    frontLeft.getPosition().distanceMeters,
                    frontRight.getPosition().distanceMeters,
                    rearLeft.getPosition().distanceMeters,
                    rearRight.getPosition().distanceMeters
            });

            SmartDashboard.putNumberArray("Virtual abs encoders", new double[] {
                    frontLeft.getTurningAbsoluteEncoder().getVirtualPosition(),
                    frontRight.getTurningAbsoluteEncoder().getVirtualPosition(),
                    rearLeft.getTurningAbsoluteEncoder().getVirtualPosition(),
                    rearRight.getTurningAbsoluteEncoder().getVirtualPosition()
            });
            SmartDashboard.putNumberArray("Raw abs encoders", new double[] {
                    frontLeft.getTurningAbsoluteEncoder().getPosition(),
                    frontRight.getTurningAbsoluteEncoder().getPosition(),
                    rearLeft.getTurningAbsoluteEncoder().getPosition(),
                    rearRight.getTurningAbsoluteEncoder().getPosition()
            });

            Logger.recordOutput("moduleStates", new SwerveModuleState[] {
                frontLeft.getState(),
                frontRight.getState(),
                rearLeft.getState(),
                rearRight.getState()
            });
        }
    }

    public void pathFollowDrive(ChassisSpeeds speeds) {
        SwerveModuleState[] swerveModuleStates = DrivetrainConstants.DRIVE_KINEMATICS.toSwerveModuleStates(speeds);

        setModuleStates(swerveModuleStates);
    }

    /**
     * Sets the wheels into an X formation to prevent movement.
     */
    public void setX() {
        if (ENABLED) {
            frontLeft.setDesiredState(new SwerveModuleState(0,
                    Rotation2d.fromDegrees(45)));
            frontRight.setDesiredState(new SwerveModuleState(0,
                    Rotation2d.fromDegrees(-45)));
            rearLeft.setDesiredState(new SwerveModuleState(0,
                    Rotation2d.fromDegrees(-45)));
            rearRight.setDesiredState(new SwerveModuleState(0,
                    Rotation2d.fromDegrees(45)));
        }
    }

    /**
     * Sets the swerve ModuleStates.
     *
     * @param desiredStates The desired SwerveModule states.
     */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        if (ENABLED) {
            SwerveDriveKinematics.desaturateWheelSpeeds(
                    desiredStates, DrivetrainConstants.MAX_SPEED_METERS_PER_SECOND);

            frontLeft.setDesiredState(desiredStates[0]);
            frontRight.setDesiredState(desiredStates[1]);
            rearLeft.setDesiredState(desiredStates[2]);
            rearRight.setDesiredState(desiredStates[3]);
        }
    }

    /**
     * Resets the drive encoders to currently read a position of 0 and seeds the
     * turn encoders using the absolute encoders.
     */
    public void resetEncoders() {
        if (ENABLED) {
            frontLeft.resetEncoders();
            rearLeft.resetEncoders();
            frontRight.resetEncoders();
            rearRight.resetEncoders();
        }
    }

    @Override
    public boolean isEnabled() {
        return ENABLED;
    }


    @Override
    public void initialize() {
        if (ENABLED) {
            frontLeft = new SwerveModule(
                    RobotConstants.Ports.CAN.FRONT_LEFT_DRIVING,
                    RobotConstants.Ports.CAN.FRONT_LEFT_TURNING,
                    RobotConstants.Ports.ANALOG.FRONT_LEFT_TURNING_ABSOLUTE_ENCODER, false);

            frontRight = new SwerveModule(
                    RobotConstants.Ports.CAN.FRONT_RIGHT_DRIVING,
                    RobotConstants.Ports.CAN.FRONT_RIGHT_TURNING,
                    RobotConstants.Ports.ANALOG.FRONT_RIGHT_TURNING_ABSOLUTE_ENCODER, false);

            rearLeft = new SwerveModule(
                    RobotConstants.Ports.CAN.REAR_LEFT_DRIVING,
                    RobotConstants.Ports.CAN.REAR_LEFT_TURNING,
                    RobotConstants.Ports.ANALOG.REAR_LEFT_TURNING_ABSOLUTE_ENCODER, false);

            rearRight = new SwerveModule(
                    RobotConstants.Ports.CAN.REAR_RIGHT_DRIVING,
                    RobotConstants.Ports.CAN.REAR_RIGHT_TURNING,
                    RobotConstants.Ports.ANALOG.REAR_RIGHT_TURNING_ABSOLUTE_ENCODER, false);

            frontLeft.calibrateVirtualPosition(FRONT_LEFT_VIRTUAL_OFFSET_RADIANS);
            frontRight.calibrateVirtualPosition(FRONT_RIGHT_VIRTUAL_OFFSET_RADIANS);
            rearLeft.calibrateVirtualPosition(REAR_LEFT_VIRTUAL_OFFSET_RADIANS);
            rearRight.calibrateVirtualPosition(REAR_RIGHT_VIRTUAL_OFFSET_RADIANS);

            resetEncoders();
        }
    }
}
