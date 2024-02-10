package frc.robot.subsystems;

import java.util.Optional;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import entech.subsystems.SubsystemInput;

public class OdometryInput implements SubsystemInput {
    public SwerveModulePosition[] modulePositions;
    public Rotation2d yaw;
    public ChassisSpeeds chassisSpeeds;
    public Optional<Pose2d> visionEstimate;
    public Optional<Double> visionTimeStamp;
}
