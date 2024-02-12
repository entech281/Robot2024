package frc.robot.processors;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import frc.robot.RobotConstants;

public class OdomtryProcessor {
    private SwerveDrivePoseEstimator estimator;

    private Rotation2d latestGyroAngle;
    private SwerveModulePosition[] latestSwerveModulesPositions;

    public Pose2d getEstimatedPose() {
        return estimator.getEstimatedPosition();
    }

    public void createEstimator(SwerveModulePosition[] modulePositions, Rotation2d yaw) {
        estimator = new SwerveDrivePoseEstimator(
            RobotConstants.DrivetrainConstants.DRIVE_KINEMATICS,
            yaw,
            modulePositions,
            RobotConstants.ODOMETRY.INITIAL_POSE
        );

        latestGyroAngle = yaw;
        latestSwerveModulesPositions = modulePositions;

        estimator.setVisionMeasurementStdDevs(RobotConstants.Vision.VISION_STD_DEVS);
    }

    public void updateInputs(SwerveModulePosition[] modulePositions, Rotation2d yaw) {
        estimator.update(yaw, modulePositions);

        latestGyroAngle = yaw;
        latestSwerveModulesPositions = modulePositions;
    }

    public void addVisionEstimatedPose(Pose2d visionPose, double timeStamp, Rotation2d yaw) {
        Pose2d fixedVisionPose = new Pose2d(visionPose.getTranslation(), yaw);
        estimator.addVisionMeasurement(fixedVisionPose, timeStamp);
    }

    /**
     * Resets the odometry to the specified pose.
     *
     * @param pose The pose to which to set the odometry.
     */
    public void resetOdometry(Pose2d pose) {
        estimator.resetPosition(
                latestGyroAngle,
                latestSwerveModulesPositions,
                pose);
    }

    /**
     * Resets the odometry to the specified pose.
     *
     * @param pose The pose to which to set the odometry.
     * @param gyroAngle the latest gyro angle.
     */
    public void resetOdometry(Pose2d pose, Rotation2d gyroAngle) {
        estimator.resetPosition(
            gyroAngle,
            latestSwerveModulesPositions,
            pose
        );
        latestGyroAngle = gyroAngle;
    }
}
