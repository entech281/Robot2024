// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Optional;

import com.kauailabs.navx.frc.AHRS;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.HolonomicPathFollowerConfig;
import com.pathplanner.lib.util.PIDConstants;
import com.pathplanner.lib.util.ReplanningConfig;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import frc.robot.RobotConstants.DrivetrainConstants;
import frc.robot.swerve.SwerveModule;
import frc.robot.swerve.SwerveUtils;
import frc.robot.vision.VisionDataPacket;

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

    private SwerveModule frontLeft;
    private SwerveModule frontRight;
    private SwerveModule rearLeft;
    private SwerveModule rearRight;

    private AHRS gyro;

    private double currentTranslationDir = 0.0;
    private double currentTranslationMag = 0.0;

    private SlewRateLimiter magLimiter = new SlewRateLimiter(DrivetrainConstants.MAGNITUDE_SLEW_RATE);
    private SlewRateLimiter rotLimiter = new SlewRateLimiter(DrivetrainConstants.ROTATIONAL_SLEW_RATE);
    private double prevTime = WPIUtilJNI.now() * 1e-6;

    private SwerveDrivePoseEstimator odometry;

    Field2d field = new Field2d();

    /** Creates a new Drivetrain. */
    public DriveSubsystem() {
        AutoBuilder.configureHolonomic(
                odometry::getEstimatedPosition, // Robot pose supplier
                this::resetOdometry, // Method to reset odometry (will be called if your auto has a starting pose)
                this::getChassisSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
                this::pathFollowDrive,
                new HolonomicPathFollowerConfig( // HolonomicPathFollowerConfig, this should likely live in your Constants class
                        new PIDConstants(5.0, 0.0, 0.0), // Translation PID constants
                        new PIDConstants(5.0, 0.0, 0.0), // Rotation PID constants
                        4.5, // Max module speed, in m/s
                        0.4, // Drive base radius in meters. Distance from robot center to furthest module.
                        new ReplanningConfig() // Default path replanning config. See the API for the options here
                ),
                () -> {
                    // Boolean supplier that controls when the path will be mirrored for the red alliance
                    // This will flip the path being followed to the red side of the field.
                    // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

                    var alliance = DriverStation.getAlliance();
                    if (alliance.isPresent()) {
                        return alliance.get() == DriverStation.Alliance.Red;
                    }
                    return false;
                },
                this // Reference to this subsystem to set requirements
        );
    }

    private double getGyroAngle() {
        return gyro.getAngle() + 0;
    }

    @Override
    public void periodic() {
        if (ENABLED) {
            field.setRobotPose(odometry.getEstimatedPosition());
            SmartDashboard.putData("Odometry Pose Field", field);

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

            SmartDashboard.putData("NAVX", gyro);

            // Update the odometry in the periodic block
            odometry.update(
                    Rotation2d.fromDegrees(GYRO_ORIENTATION * gyro.getAngle()),
                    new SwerveModulePosition[] {
                            frontLeft.getPosition(),
                            frontRight.getPosition(),
                            rearLeft.getPosition(),
                            rearRight.getPosition()
                    });
        }
    }

    /**
     * Returns the currently-estimated pose of the robot.
     *
     * @return The pose.
     */
    public Optional<Pose2d> getPose() {
        return ENABLED ? Optional.of(odometry.getEstimatedPosition()) : Optional.empty();
    }

    private ChassisSpeeds getChassisSpeeds() {
        double radiansPerSecond = Units.degreesToRadians(gyro.getRate());
        return ChassisSpeeds.fromFieldRelativeSpeeds((double) gyro.getVelocityX(), (double) gyro.getVelocityY(), radiansPerSecond, gyro.getRotation2d());
    }

    /**
     * Resets the odometry to the specified pose.
     *
     * @param pose The pose to which to set the odometry.
     */
    public void resetOdometry(Pose2d pose) {
        if (ENABLED) {
            odometry.resetPosition(
                    Rotation2d.fromDegrees(GYRO_ORIENTATION * gyro.getAngle()),
                    new SwerveModulePosition[] {
                            frontLeft.getPosition(),
                            frontRight.getPosition(),
                            rearLeft.getPosition(),
                            rearRight.getPosition()
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
            double currentRotation;

            if (rateLimit) {
                // Convert XY to polar for rate limiting
                double inputTranslationDir = Math.atan2(ySpeed, xSpeed);
                double inputTranslationMag = Math.sqrt(Math.pow(xSpeed, 2) + Math.pow(ySpeed, 2));

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
                currentRotation = rotLimiter.calculate(rot);

            } else {
                xSpeedCommanded = xSpeed;
                ySpeedCommanded = ySpeed;
                currentRotation = rot;
            }

            // Convert the commanded speeds into the correct units for the drivetrain
            double xSpeedDelivered = xSpeedCommanded * DrivetrainConstants.MAX_SPEED_METERS_PER_SECOND;
            double ySpeedDelivered = ySpeedCommanded * DrivetrainConstants.MAX_SPEED_METERS_PER_SECOND;
            double rotDelivered = currentRotation * DrivetrainConstants.MAX_ANGULAR_SPEED_RADIANS_PER_SECOND;

            SwerveModuleState[] swerveModuleStates = DrivetrainConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                    fieldRelative
                            ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered,
                                    Rotation2d.fromDegrees(GYRO_ORIENTATION * getGyroAngle()))
                            : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered));

            setModuleStates(swerveModuleStates);
        }
    }

    private void pathFollowDrive(ChassisSpeeds speeds) {
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

    /** Zeroes the heading of the robot. */
    public void zeroHeading() {
        if (ENABLED) {
            gyro.reset();
            gyro.setAngleAdjustment(180);
            Optional<Pose2d> pose = getPose();
            if (pose.isPresent()) {
                Pose2d pose2 = new Pose2d(pose.get().getTranslation(), Rotation2d.fromDegrees(0));
                resetOdometry(pose2);
            } else {
                DriverStation.reportWarning("Not possible.", false);
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

    public void addVisionData(VisionDataPacket data) {
        Optional<Pose2d> pose = data.getEstimatedPose();
        Optional<Double> timeStamp = data.getTimeStamp();
        if (pose.isPresent() && timeStamp.isPresent()) {
            odometry.addVisionMeasurement(pose.get(), timeStamp.get());
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

            gyro = new AHRS(Port.kMXP);
            gyro.reset();
            gyro.zeroYaw();

            Translation2d initialTranslation = new Translation2d(Units.inchesToMeters(FIELD_LENGTH_INCHES / 2),
                    Units.inchesToMeters(FIELD_WIDTH_INCHES / 2)); // mid field
            Rotation2d initialRotation = Rotation2d.fromDegrees(180);

            odometry = new SwerveDrivePoseEstimator(
                    DrivetrainConstants.DRIVE_KINEMATICS,
                    Rotation2d.fromDegrees(GYRO_ORIENTATION * gyro.getAngle()),
                    new SwerveModulePosition[] {
                            frontLeft.getPosition(),
                            frontRight.getPosition(),
                            rearLeft.getPosition(),
                            rearRight.getPosition()
                    }, new Pose2d(initialTranslation, initialRotation));

            frontLeft.calibrateVirtualPosition(FRONT_LEFT_VIRTUAL_OFFSET_RADIANS);
            frontRight.calibrateVirtualPosition(FRONT_RIGHT_VIRTUAL_OFFSET_RADIANS);
            rearLeft.calibrateVirtualPosition(REAR_LEFT_VIRTUAL_OFFSET_RADIANS);
            rearRight.calibrateVirtualPosition(REAR_RIGHT_VIRTUAL_OFFSET_RADIANS);

            resetEncoders();
            zeroHeading();
            gyro.setAngleAdjustment(0);
        }
    }
}
