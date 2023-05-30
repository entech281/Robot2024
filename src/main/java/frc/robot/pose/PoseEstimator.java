package frc.robot.pose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import entech.util.EntechGeometryUtils;

public class PoseEstimator {

    public enum PoseStratagy {
        COMPLETE,
        ODOMETRY,
        VISION_NAVX,
        VISION,
        NULL
    }
    
    private Odometry odometry;
    private PoseStratagy stratagy;
    private Pose2d latestPose;

    public PoseEstimator(PoseStratagy stratagy) {
        this.stratagy = stratagy;
    }

    public PoseEstimator(PoseStratagy stratagy, double yaw, SwerveModulePosition[] modulePositions) {
        this.stratagy = stratagy;
        odometry = new Odometry(yaw, modulePositions);
    }

    public Pose2d updateEstimatedPose(double yaw, SwerveModulePosition[] modulePositions, Pose2d visionPose) {
        if (stratagy == PoseStratagy.COMPLETE || stratagy == PoseStratagy.ODOMETRY) {
            if (odometry == null) {
                odometry = new Odometry(yaw, modulePositions);
            }
        } 
        switch(stratagy) {
            case COMPLETE:
                latestPose = EntechGeometryUtils.averagePose2d(odometry.updateOdometry(yaw, modulePositions), visionPose);
                break;
            case ODOMETRY:
                latestPose = odometry.updateOdometry(yaw, modulePositions);
                break;
            case VISION_NAVX:
                latestPose = new Pose2d(visionPose.getX(), visionPose.getY(), Rotation2d.fromDegrees(yaw));
                break;
            case VISION:
                latestPose = visionPose;
                break;
            default:
                latestPose = null;
                break;
        }
        return latestPose;
    }

    public Pose2d updateEstimatedPose(double yaw, Pose2d visionPose) {
        return updateEstimatedPose(yaw, null, visionPose);
    }

    public Pose2d updateEstimatedPose(Pose2d visionPose) {
        return updateEstimatedPose(0, null, visionPose);
    }

    public Pose2d updateEstimatedPose(double yaw, SwerveModulePosition[] modulePositions) {
        return updateEstimatedPose(yaw, modulePositions, null);
    }

    public Pose2d getLatestPose() {
        return latestPose;
    }
}
