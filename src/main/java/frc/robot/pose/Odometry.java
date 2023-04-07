package frc.robot.pose;

import frc.robot.RobotConstants;

import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public class Odometry {
    private SwerveDriveOdometry swerveOdometry;

    public Odometry(double yaw, SwerveModulePosition[] modulePositions) {
        swerveOdometry = new SwerveDriveOdometry(RobotConstants.Swerve.swerveKinematics, Rotation2d.fromDegrees(yaw), modulePositions);
    }

    public Pose2d getPose() {
        return swerveOdometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose, double yaw, SwerveModulePosition[] modulePositions) {
        swerveOdometry.resetPosition(Rotation2d.fromDegrees(yaw), modulePositions, pose);
    }
}
