package frc.robot.processors;

import java.util.Optional;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;

public class OdometryProcessor {
  private SwerveDrivePoseEstimator estimator;
  private boolean integrateVision = false;
  private Field2d field = new Field2d();

  public Pose2d getEstimatedPose() {
    return estimator.getEstimatedPosition();
  }

  public void createEstimator() {
    estimator = new SwerveDrivePoseEstimator(RobotConstants.DrivetrainConstants.DRIVE_KINEMATICS,
        Rotation2d.fromDegrees(RobotIO.getInstance().getNavXOutput().getYaw()),
        RobotIO.getInstance().getDriveOutput().getModulePositions(),
        RobotConstants.ODOMETRY.INITIAL_POSE);

    estimator.setVisionMeasurementStdDevs(RobotConstants.Vision.VISION_STD_DEVS);
    field.setRobotPose(getEstimatedPose());
    SmartDashboard.putData(field);
  }

  public void update() {
    estimator.update(Rotation2d.fromDegrees(RobotIO.getInstance().getNavXOutput().getYaw()),
        RobotIO.getInstance().getDriveOutput().getModulePositions());

    if (RobotIO.getInstance().getVisionOutput() != null && integrateVision) {
      Optional<Pose2d> visionPose = RobotIO.getInstance().getVisionOutput().getEstimatedPose();
      Optional<Double> visionTimeStamp = RobotIO.getInstance().getVisionOutput().getTimeStamp();

      if (visionPose.isPresent() && visionTimeStamp.isPresent()) {
        addVisionEstimatedPose(visionPose.get(), visionTimeStamp.get(),
            getEstimatedPose().getRotation());
      }
    }

    RobotIO.getInstance().updateOdometryPose(getEstimatedPose());
    field.setRobotPose(getEstimatedPose());

    Pose2d target = null;
    Optional<Alliance> team = DriverStation.getAlliance();
    if (team.isPresent() && team.get() == Alliance.Blue) {
      target = new Pose2d(0.0, 5.31, new Rotation2d());
    }

    if (target == null) {
      target = new Pose2d(16.54, 5.54, new Rotation2d());
    }

    RobotIO.getInstance().setDistanceFromTarget(Optional.of(calculateDistanceFromTarget(target)));
  }

  public void addVisionEstimatedPose(Pose2d visionPose, double timeStamp, Rotation2d yaw) {
    Pose2d fixedVisionPose = new Pose2d(visionPose.getTranslation(), yaw);
    estimator.addVisionMeasurement(fixedVisionPose, timeStamp);
  }

  public double calculateDistanceFromTarget(Pose2d target) {
    double xDist = getEstimatedPose().getX() - target.getX();
    double yDist = getEstimatedPose().getY() - target.getY();

    return Math.sqrt(xDist * xDist + yDist * yDist);
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    estimator.resetPosition(Rotation2d.fromDegrees(RobotIO.getInstance().getNavXOutput().getYaw()),
        RobotIO.getInstance().getDriveOutput().getModulePositions(), pose);
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   * @param gyroAngle the latest gyro angle.
   */
  public void resetOdometry(Pose2d pose, Rotation2d gyroAngle) {
    estimator.resetPosition(gyroAngle, RobotIO.getInstance().getDriveOutput().getModulePositions(),
        pose);
  }

  public boolean isIntegratingVision() {
    return this.integrateVision;
  }

  public void setIntegrateVision(boolean integrateVision) {
    this.integrateVision = integrateVision;
  }
}
