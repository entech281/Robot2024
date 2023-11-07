// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Optional;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj.I2C.Port;
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
public class DriveSubsystem extends EntechSubsystem {
    private static final boolean ENABLED = true;

    public static final double FRONT_LEFT_VIRTUAL_OFFSET_RADIANS = 2.3084534854898795;
    public static final double FRONT_RIGHT_VIRTUAL_OFFSET_RADIANS = 1.8754174966340216;
    public static final double REAR_LEFT_VIRTUAL_OFFSET_RADIANS = 2.6789867521760486;
    public static final double REAR_RIGHT_VIRTUAL_OFFSET_RADIANS = 2.467314041927964;

    public static final int GYRO_ORIENTATION = 1; // might be able to merge with kGyroReversed

    public static final double FIELD_LENGTH_INCHES = 54 * 12 + 1; // 54ft 1in
    public static final double FIELD_WIDTH_INCHES = 26 * 12 + 7; // 26ft 7in

    private SwerveModule m_frontLeft;
    private SwerveModule m_frontRight;
    private SwerveModule m_rearLeft;
    private SwerveModule m_rearRight;

    private AHRS m_gyro;

    private double m_currentRotation = 0.0;
    private double m_currentTranslationDir = 0.0;
    private double m_currentTranslationMag = 0.0;

    private SlewRateLimiter m_magLimiter = new SlewRateLimiter(DrivetrainConstants.MAGNITUDE_SLEW_RATE);
    private SlewRateLimiter m_rotLimiter = new SlewRateLimiter(DrivetrainConstants.ROTATIONAL_SLEW_RATE);
    private double m_prevTime = WPIUtilJNI.now() * 1e-6;

    private SwerveDriveOdometry m_odometry;

    Field2d field = new Field2d();

    /** Creates a new Drivetrain. */
    public DriveSubsystem() {

    }

    // Bad look on scope needs to be fixed
    // public Pose3d createPose3d() {
    // Pose2d initial = m_odometry.getPoseMeters();

    // return new Pose3d(new Translation3d(initial.getX(), initial.getY(), 0.0),
    // new Rotation3d(Units.degreesToRadians(m_gyro.getRoll()),
    // Units.degreesToRadians(m_gyro.getPitch()),
    // Units.degreesToRadians(m_gyro.getYaw() * -1)));
    // }

    private double getGyroAngle() {
        return m_gyro.getAngle() + 0;
    }

    @Override
    public void periodic() {
        if (ENABLED) {
            field.setRobotPose(m_odometry.getPoseMeters());
            SmartDashboard.putData("Odometry Pose Field", field);

            SmartDashboard.putNumberArray("modules pose angles", new double[] {
                    m_frontLeft.getPosition().angle.getDegrees(),
                    m_frontRight.getPosition().angle.getDegrees(),
                    m_rearLeft.getPosition().angle.getDegrees(),
                    m_rearRight.getPosition().angle.getDegrees()
            });
            SmartDashboard.putNumberArray("modules pose meters", new double[] {
                    m_frontLeft.getPosition().distanceMeters,
                    m_frontRight.getPosition().distanceMeters,
                    m_rearLeft.getPosition().distanceMeters,
                    m_rearRight.getPosition().distanceMeters
            });

            SmartDashboard.putNumberArray("Virtual abs encoders", new double[] {
                    m_frontLeft.getTurningAbsoluteEncoder().getVirtualPosition(),
                    m_frontRight.getTurningAbsoluteEncoder().getVirtualPosition(),
                    m_rearLeft.getTurningAbsoluteEncoder().getVirtualPosition(),
                    m_rearRight.getTurningAbsoluteEncoder().getVirtualPosition()
            });
            SmartDashboard.putNumberArray("Raw abs encoders", new double[] {
                    m_frontLeft.getTurningAbsoluteEncoder().getPosition(),
                    m_frontRight.getTurningAbsoluteEncoder().getPosition(),
                    m_rearLeft.getTurningAbsoluteEncoder().getPosition(),
                    m_rearRight.getTurningAbsoluteEncoder().getPosition()
            });

            SmartDashboard.putData("NAVX", m_gyro);

            // Update the odometry in the periodic block
            m_odometry.update(
                    Rotation2d.fromDegrees(GYRO_ORIENTATION * m_gyro.getAngle()),
                    new SwerveModulePosition[] {
                            m_frontLeft.getPosition(),
                            m_frontRight.getPosition(),
                            m_rearLeft.getPosition(),
                            m_rearRight.getPosition()
                    });
        }
    }

    /**
     * Returns the currently-estimated pose of the robot.
     *
     * @return The pose.
     */
    public Optional<Pose2d> getPose() {
        return ENABLED ? Optional.of(m_odometry.getPoseMeters()) : Optional.empty();
    }

    /**
     * Resets the odometry to the specified pose.
     *
     * @param pose The pose to which to set the odometry.
     */
    public void resetOdometry(Pose2d pose) {
        if (ENABLED) {
            m_odometry.resetPosition(
                    Rotation2d.fromDegrees(GYRO_ORIENTATION * m_gyro.getAngle()),
                    new SwerveModulePosition[] {
                            m_frontLeft.getPosition(),
                            m_frontRight.getPosition(),
                            m_rearLeft.getPosition(),
                            m_rearRight.getPosition()
                    },
                    pose);
        }
    }

    /**
     * Method to drive the robot using joystick info.
     *
     * @param xSpeed        Speed of the robot in the x direction (forward).
     * @param ySpeed        Speed of the robot in the y direction (sideways).
     * @param rot           Angular rate of the robot.
     * @param fieldRelative Whether the provided x and y speeds are relative to the
     *                      field.
     * @param rateLimit     Whether to enable rate limiting for smoother control.
     */
    public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative, boolean rateLimit) {
        if (ENABLED) {
            double xSpeedCommanded;
            double ySpeedCommanded;

            if (rateLimit) {
                // Convert XY to polar for rate limiting
                double inputTranslationDir = Math.atan2(ySpeed, xSpeed);
                double inputTranslationMag = Math.sqrt(Math.pow(xSpeed, 2) + Math.pow(ySpeed, 2));

                // Calculate the direction slew rate based on an estimate of the lateral
                // acceleration
                double directionSlewRate;

                if (m_currentTranslationMag != 0.0) {
                    directionSlewRate = Math.abs(DrivetrainConstants.DIRECTION_SLEW_RATE / m_currentTranslationMag);
                } else {
                    directionSlewRate = 500.0; // some high number that means the slew rate is effectively instantaneous
                }

                double currentTime = WPIUtilJNI.now() * 1e-6;
                double elapsedTime = currentTime - m_prevTime;
                double angleDif = SwerveUtils.AngleDifference(inputTranslationDir, m_currentTranslationDir);

                if (angleDif < 0.45 * Math.PI) {
                    m_currentTranslationDir = SwerveUtils.StepTowardsCircular(m_currentTranslationDir,
                            inputTranslationDir,
                            directionSlewRate * elapsedTime);
                    m_currentTranslationMag = m_magLimiter.calculate(inputTranslationMag);
                } else if (angleDif > 0.85 * Math.PI) {
                    if (m_currentTranslationMag > 1e-4) {
                        m_currentTranslationMag = m_magLimiter.calculate(0.0);
                    } else {
                        m_currentTranslationDir = SwerveUtils.WrapAngle(m_currentTranslationDir + Math.PI);
                        m_currentTranslationMag = m_magLimiter.calculate(inputTranslationMag);
                    }
                } else {
                    m_currentTranslationDir = SwerveUtils.StepTowardsCircular(m_currentTranslationDir,
                            inputTranslationDir,
                            directionSlewRate * elapsedTime);
                    m_currentTranslationMag = m_magLimiter.calculate(0.0);
                }

                m_prevTime = currentTime;

                xSpeedCommanded = m_currentTranslationMag * Math.cos(m_currentTranslationDir);
                ySpeedCommanded = m_currentTranslationMag * Math.sin(m_currentTranslationDir);
                m_currentRotation = m_rotLimiter.calculate(rot);

            } else {
                xSpeedCommanded = xSpeed;
                ySpeedCommanded = ySpeed;
                m_currentRotation = rot;
            }

            // Convert the commanded speeds into the correct units for the drivetrain
            double xSpeedDelivered = xSpeedCommanded * DrivetrainConstants.MAX_SPEED_METERS_PER_SECOND;
            double ySpeedDelivered = ySpeedCommanded * DrivetrainConstants.MAX_SPEED_METERS_PER_SECOND;
            double rotDelivered = m_currentRotation * DrivetrainConstants.MAX_ANGULAR_SPEED_RADIANS_PER_SECOND;

            var swerveModuleStates = DrivetrainConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                    fieldRelative
                            ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered,
                                    Rotation2d.fromDegrees(GYRO_ORIENTATION * getGyroAngle()))
                            : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered));

            SwerveDriveKinematics.desaturateWheelSpeeds(
                    swerveModuleStates, DrivetrainConstants.MAX_SPEED_METERS_PER_SECOND);

            m_frontLeft.setDesiredState(swerveModuleStates[0]);
            m_frontRight.setDesiredState(swerveModuleStates[1]);
            m_rearLeft.setDesiredState(swerveModuleStates[2]);
            m_rearRight.setDesiredState(swerveModuleStates[3]);
        }
    }

    /**
     * Sets the wheels into an X formation to prevent movement.
     */
    public void setX() {
        if (ENABLED) {
            m_frontLeft.setDesiredState(new SwerveModuleState(0,
                    Rotation2d.fromDegrees(45)));
            m_frontRight.setDesiredState(new SwerveModuleState(0,
                    Rotation2d.fromDegrees(-45)));
            m_rearLeft.setDesiredState(new SwerveModuleState(0,
                    Rotation2d.fromDegrees(-45)));
            m_rearRight.setDesiredState(new SwerveModuleState(0,
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

            m_frontLeft.setDesiredState(desiredStates[0]);
            m_frontRight.setDesiredState(desiredStates[1]);
            m_rearLeft.setDesiredState(desiredStates[2]);
            m_rearRight.setDesiredState(desiredStates[3]);
        }
    }

    /**
     * Resets the drive encoders to currently read a position of 0 and seeds the
     * turn encoders using the absolute encoders.
     */
    public void resetEncoders() {
        if (ENABLED) {
            m_frontLeft.resetEncoders();
            m_rearLeft.resetEncoders();
            m_frontRight.resetEncoders();
            m_rearRight.resetEncoders();
        }
    }

    /** Zeroes the heading of the robot. */
    public void zeroHeading() {
        if (ENABLED) {
            m_gyro.reset();
            m_gyro.setAngleAdjustment(0);
            Pose2d pose = getPose().get();
            Pose2d pose2 = new Pose2d(pose.getTranslation(), Rotation2d.fromDegrees(180));
            resetOdometry(pose2);
            // m_gyro.setAngleAdjustment(180);
        }
    }

    /** Calibrates the gyro. */
    public void calculateHeading() {
        if (ENABLED) {
            m_gyro.calibrate();
            while (!m_gyro.isCalibrating()) {
                ;
            }
        }
    }

    /**
     * Returns the heading of the robot.
     *
     * @return the robot's heading in degrees, from -180 to 180
     */
    public Optional<Double> getHeading() {
        return ENABLED ? Optional.of(Rotation2d.fromDegrees(GYRO_ORIENTATION * getGyroAngle()).getDegrees())
                : Optional.empty();
    }

    /**
     * Returns the turn rate of the robot.
     *
     * @return The turn rate of the robot, in degrees per second
     */
    public Optional<Double> getTurnRate() {
        return ENABLED ? Optional.of(m_gyro.getRate() * (DrivetrainConstants.kGyroReversed ? -1.0 : 1.0))
                : Optional.empty();
    }

    public Optional<SwerveModule> getFrontLeftModule() {
        return ENABLED ? Optional.of(m_frontLeft) : Optional.empty();
    }

    public Optional<SwerveModule> getFrontRightModule() {
        return ENABLED ? Optional.of(m_frontRight) : Optional.empty();
    }

    public Optional<SwerveModule> getRearLeftModule() {
        return ENABLED ? Optional.of(m_rearLeft) : Optional.empty();
    }

    public Optional<SwerveModule> getRearRightModule() {
        return ENABLED ? Optional.of(m_rearRight) : Optional.empty();
    }

    public Optional<AHRS> getImu() {
        return ENABLED ? Optional.of(m_gyro) : Optional.empty();
    }

    @Override
    public boolean isEnabled() {
        return ENABLED;
    }

    @Override
    public void initialize() {
        if (ENABLED) {
            m_frontLeft = new SwerveModule(
                    RobotConstants.Ports.CAN.FRONT_LEFT_DRIVING,
                    RobotConstants.Ports.CAN.FRONT_LEFT_TURNING,
                    RobotConstants.Ports.ANALOG.FRONT_LEFT_TURNING_ABSOLUTE_ENCODER, false);

            m_frontRight = new SwerveModule(
                    RobotConstants.Ports.CAN.FRONT_RIGHT_DRIVING,
                    RobotConstants.Ports.CAN.FRONT_RIGHT_TURNING,
                    RobotConstants.Ports.ANALOG.FRONT_RIGHT_TURNING_ABSOLUTE_ENCODER, false);

            m_rearLeft = new SwerveModule(
                    RobotConstants.Ports.CAN.REAR_LEFT_DRIVING,
                    RobotConstants.Ports.CAN.REAR_LEFT_TURNING,
                    RobotConstants.Ports.ANALOG.REAR_LEFT_TURNING_ABSOLUTE_ENCODER, false);

            m_rearRight = new SwerveModule(
                    RobotConstants.Ports.CAN.REAR_RIGHT_DRIVING,
                    RobotConstants.Ports.CAN.REAR_RIGHT_TURNING,
                    RobotConstants.Ports.ANALOG.REAR_RIGHT_TURNING_ABSOLUTE_ENCODER, false);

            m_gyro = new AHRS(Port.kMXP);

            m_gyro.zeroYaw();

            m_odometry = new SwerveDriveOdometry(
                    DrivetrainConstants.DRIVE_KINEMATICS,
                    Rotation2d.fromDegrees(GYRO_ORIENTATION * m_gyro.getAngle()),
                    new SwerveModulePosition[] {
                            m_frontLeft.getPosition(),
                            m_frontRight.getPosition(),
                            m_rearLeft.getPosition(),
                            m_rearRight.getPosition()
                    });

            m_frontLeft.calibrateVirtualPosition(FRONT_LEFT_VIRTUAL_OFFSET_RADIANS);
            m_frontRight.calibrateVirtualPosition(FRONT_RIGHT_VIRTUAL_OFFSET_RADIANS);
            m_rearLeft.calibrateVirtualPosition(REAR_LEFT_VIRTUAL_OFFSET_RADIANS);
            m_rearRight.calibrateVirtualPosition(REAR_RIGHT_VIRTUAL_OFFSET_RADIANS);

            resetEncoders();

            calculateHeading();
            zeroHeading();

            Translation2d initialTranslation = new Translation2d(Units.inchesToMeters(FIELD_LENGTH_INCHES / 2),
                    Units.inchesToMeters(FIELD_WIDTH_INCHES / 2)); // mid field
            Rotation2d initialRotation = Rotation2d.fromDegrees(0);
            m_gyro.setAngleAdjustment(180);
            Pose2d initialPose = new Pose2d(initialTranslation, initialRotation);
            resetOdometry(initialPose);
        }
    }
}
