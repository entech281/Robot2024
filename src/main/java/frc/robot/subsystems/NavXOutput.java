package frc.robot.subsystems;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import entech.subsystems.SubsystemOutput;

public class NavXOutput implements SubsystemOutput {
    public double yaw;
    public double pitch;
    public double roll;
    public double yawRate;
    public double zVelocity;
    public double temperature;
    public double compassHeading;
    public double angleAdjustment;

    public boolean isCalibrating;
    public boolean isMoving;
    public boolean isRotating;
    public boolean isMagnetometerCalibrated;
    public boolean isMagneticDisturbance;

    public ChassisSpeeds chassisSpeeds;
}
