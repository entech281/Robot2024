package frc.robot.processors;

import java.util.Optional;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;

public class OdomtryProcessor {
    private SwerveDrivePoseEstimator estimator;

    public Pose2d getEstimatedPose() {
        return estimator.getEstimatedPosition();
    }

    public void createEstimator() {
        estimator = new SwerveDrivePoseEstimator(
            RobotConstants.DrivetrainConstants.DRIVE_KINEMATICS,
            Rotation2d.fromDegrees(RobotIO.getInstance().getNavXOutput().yaw),
            RobotIO.getInstance().getDriveOutput().modulePositions,
            RobotConstants.ODOMETRY.INITIAL_POSE
        );

        estimator.setVisionMeasurementStdDevs(RobotConstants.Vision.VISION_STD_DEVS);
    }

    public void update() {
        estimator.update(
            Rotation2d.fromDegrees(RobotIO.getInstance().getNavXOutput().yaw),
            RobotIO.getInstance().getDriveOutput().modulePositions
        );

        Optional<Pose2d> visionPose = RobotIO.getInstance().getVisionOutput().estimatedPose;
        Optional<Double> visionTimeStamp = RobotIO.getInstance().getVisionOutput().timeStamp;

        if (visionPose.isPresent() && visionTimeStamp.isPresent()) {
            addVisionEstimatedPose(visionPose.get(), visionTimeStamp.get(), getEstimatedPose().getRotation());
        }
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
                Rotation2d.fromDegrees(RobotIO.getInstance().getNavXOutput().yaw),
                RobotIO.getInstance().getDriveOutput().modulePositions,
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
            RobotIO.getInstance().getDriveOutput().modulePositions,
            pose
        );
    }
}
