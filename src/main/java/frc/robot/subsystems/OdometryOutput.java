package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import entech.subsystems.SubsystemOutput;

public class OdometryOutput implements SubsystemOutput {
    public ChassisSpeeds chassisSpeeds;
    public Pose2d pose;
}
