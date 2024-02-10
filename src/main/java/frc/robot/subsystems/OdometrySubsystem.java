package frc.robot.subsystems;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;

public class OdometrySubsystem extends EntechSubsystem<OdometryInput, OdometryOutput> {
    private static final boolean ENABLED = true;
    private SwerveDrivePoseEstimator estimator;

    private ChassisSpeeds latestChassisSpeeds;

    private Rotation2d latestGyroAngle;
    private SwerveModulePosition[] latestSwerveModulesPositions;

    private Field2d field;

    @Override
    public OdometryOutput getOutputs() {
        if (ENABLED) {
            OdometryOutput output = new OdometryOutput();

            output.chassisSpeeds = latestChassisSpeeds;
            output.pose = estimator.getEstimatedPosition();

            return output;
        }
        return null;
    }

    @Override
    public void initialize() {
        field = new Field2d();
    }

    @Override
    public void periodic() {
        Logger.recordOutput("Odometry pose2d", getOutputs().pose);
        field.setRobotPose(getOutputs().pose);
        SmartDashboard.putData("Odometry Pose Field", field);
    }

    @Override
    public boolean isEnabled() {
        return ENABLED;
    }

    private void createEstimator(OdometryInput startingStates) {
        estimator = new SwerveDrivePoseEstimator(
            RobotConstants.DrivetrainConstants.DRIVE_KINEMATICS,
            startingStates.yaw,
            startingStates.modulePositions,
            RobotConstants.ODOMETRY.INITIAL_POSE
        );
        estimator.setVisionMeasurementStdDevs(RobotConstants.Vision.VISION_STD_DEVS);
    }

    @Override
    public void updateInputs(OdometryInput input) {
        if (ENABLED) {
            if (estimator == null){
                createEstimator(input);
            }

            estimator.update(input.yaw, input.modulePositions);

            if (input.visionEstimate.isPresent() && input.visionTimeStamp.isPresent()) {
                estimator.addVisionMeasurement(input.visionEstimate.get(), input.visionTimeStamp.get());
            }

            latestChassisSpeeds = input.chassisSpeeds;
            latestGyroAngle = input.yaw;
            latestSwerveModulesPositions = input.modulePositions;
        }
    }

    /**
     * Resets the odometry to the specified pose.
     *
     * @param pose The pose to which to set the odometry.
     */
    public void resetOdometry(Pose2d pose) {
        if (ENABLED) {
            estimator.resetPosition(
                    latestGyroAngle,
                    latestSwerveModulesPositions,
                    pose);
        }
    }

    /**
     * Resets the odometry to the specified pose.
     *
     * @param pose The pose to which to set the odometry.
     * @param gyroAngle the latest gyro angle.
     */
    public void resetOdometry(Pose2d pose, Rotation2d gyroAngle) {
        if (ENABLED) {
            estimator.resetPosition(
                    gyroAngle,
                    latestSwerveModulesPositions,
                    pose);
            latestGyroAngle = gyroAngle;
        }
    }
}
