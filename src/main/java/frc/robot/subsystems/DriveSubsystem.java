// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.util.sendable.SendableBuilder;

import java.io.File;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import swervelib.SwerveController;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveControllerConfiguration;
import swervelib.parser.SwerveDriveConfiguration;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;
import edu.wpi.first.wpilibj.Filesystem;

public class DriveSubsystem extends EntechSubsystem {

  /**
   * Swerve drive object.
   */
  private final SwerveDrive swerveDrive;

  /**
   * Initialize {@link SwerveDrive} with the directory provided.
   *
   * @param directory Directory of swerve drive config files.
   */
  public DriveSubsystem() {
    SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;
    try {
      swerveDrive = new SwerveParser(new File(Filesystem.getDeployDirectory(), "swerve")).createSwerveDrive();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Construct the swerve drive.
   *
   * @param driveCfg      SwerveDriveConfiguration for the swerve.
   * @param controllerCfg Swerve Controller.
   */
  public DriveSubsystem(SwerveDriveConfiguration driveCfg, SwerveControllerConfiguration controllerCfg) {
    swerveDrive = new SwerveDrive(driveCfg, controllerCfg);
  }

  /**
   * The primary method for controlling the drivebase.  Takes a {@link Translation2d} and a rotation rate, and
   * calculates and commands module states accordingly.  Can use either open-loop or closed-loop velocity control for
   * the wheel velocities.  Also has field- and robot-relative modes, which affect how the translation vector is used.
   *
   * @param translation   {@link Translation2d} that is the commanded linear velocity of the robot, in meters per
   *                      second. In robot-relative mode, positive x is towards the bow (front) and positive y is
   *                      towards port (left).  In field-relative mode, positive x is away from the alliance wall
   *                      (field North) and positive y is towards the left wall when looking through the driver station
   *                      glass (field West).
   * @param rotation      Robot angular rate, in radians per second. CCW positive.  Unaffected by field/robot
   *                      relativity.
   * @param fieldRelative Drive mode.  True for field-relative, false for robot-relative.
   * @param isOpenLoop    Whether to use closed-loop velocity control.  Set to true to disable closed-loop.
   */
  public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
    swerveDrive.drive(translation, rotation, fieldRelative, isOpenLoop);
  }

  @Override
  public void periodic() {
    swerveDrive.updateOdometry();
  }

  @Override
  public void simulationPeriodic() {
  }

  /**
   * Resets odometry to the given pose. Gyro angle and module positions do not need to be reset when calling this
   * method.  However, if either gyro angle or module position is reset, this must be called in order for odometry to
   * keep working.
   *
   * @param initialHolonomicPose The pose to set the odometry to
   */
  public void resetOdometry(Pose2d initialHolonomicPose) {
    swerveDrive.resetOdometry(initialHolonomicPose);
  }

  /**
   * Gets the current pose (position and rotation) of the robot, as reported by odometry.
   *
   * @return The robot's pose
   */
  public Pose2d getPose() {
    return swerveDrive.getPose();
  }

  /**
   * Set field-relative chassis speeds with closed-loop velocity control.
   *
   * @param chassisSpeeds Field-relative.
   */
  public void setChassisSpeeds(ChassisSpeeds chassisSpeeds) {
    swerveDrive.setChassisSpeeds(chassisSpeeds);
  }

  /**
   * Resets the gyro angle to zero and resets odometry to the same position, but facing toward 0.
   */
  public void zeroGyro() {
    swerveDrive.zeroGyro();
  }

  /**
   * Sets the drive motors to brake/coast mode.
   *
   * @param brake True to set motors to brake mode, false for coast.
   */
  public void setMotorBrake(boolean brake) {
    swerveDrive.setMotorIdleMode(brake);
  }

  /**
   * Gets the current yaw angle of the robot, as reported by the imu.  CCW positive, not wrapped.
   *
   * @return The yaw angle
   */
  public Rotation2d getYaw() {
    return swerveDrive.getYaw();
  }

  public Rotation2d getRoll() {
    return swerveDrive.getRoll();
  }

  public Rotation2d getPitch() {
    return swerveDrive.getPitch();
  }

  /**
   * Get the chassis speeds based on controller input of 2 joysticks. One for speeds in which direction. The other for
   * the angle of the robot.
   *
   * @param xInput                     X joystick input for the robot to move in the X direction.
   * @param yInput                     Y joystick input for the robot to move in the Y direction.
   * @param headingX                   X joystick which controls the angle of the robot.
   * @param headingY                   Y joystick which controls the angle of the robot.
   * @param currentHeadingAngleRadians The current robot heading in radians.
   * @return {@link ChassisSpeeds} which can be sent to th Swerve Drive.
   */
  public ChassisSpeeds getTargetSpeeds(double xInput, double yInput, double headingX, double headingY,
                                        double currentHeadingAngleRadians) {
    return swerveDrive.swerveController.getTargetSpeeds(xInput, yInput, headingX, headingY, currentHeadingAngleRadians);
  }

  /**
   * Gets the current field-relative velocity (x, y and omega) of the robot
   *
   * @return A ChassisSpeeds object of the current field-relative velocity
   */
  public ChassisSpeeds getFieldVelocity() {
    return swerveDrive.getFieldVelocity();
  }

  /**
   * Get the {@link SwerveController} in the swerve drive.
   *
   * @return {@link SwerveController} from the {@link SwerveDrive}.
   */
  public SwerveController getSwerveController() {
    return swerveDrive.swerveController;
  }

  /**
   * Get the {@link SwerveDriveConfiguration} object.
   *
   * @return The {@link SwerveDriveConfiguration} fpr the current drive.
   */
  public SwerveDriveConfiguration getSwerveDriveConfiguration() {
    return swerveDrive.swerveDriveConfiguration;
  }


  public void lock() {
    swerveDrive.lockPose();
  }

  @Override
  public void initialize() {
  }

  @Override
  public boolean isEnabled() {
      return true;
  }

  public void addVisionToEstimator(Pose3d visionEstimate, double timeStamp) {
    swerveDrive.addVisionMeasurement(visionEstimate.toPose2d(), timeStamp, true, RobotConstants.Vision.TRUST);
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType(getName());
    swerveDrive.buildDataLogger(builder);
    super.initSendable(builder);
  }
}
