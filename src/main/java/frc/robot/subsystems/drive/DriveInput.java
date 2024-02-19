package frc.robot.subsystems.drive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import entech.subsystems.SubsystemInput;
import frc.robot.RobotConstants;
import org.littletonrobotics.junction.LogTable;

public class DriveInput implements SubsystemInput {
  public double xSpeed;
  public double ySpeed;
  public double rot;
  public Rotation2d gyroAngle;
  public Pose2d latestOdometryPose;
  public String key = "driveInput";

  @Override
  public void fromLog(LogTable table) {
    xSpeed = table.get(key + "/xSpeed", 0.0);
    ySpeed = table.get(key + "/ySpeed", 0.0);
    rot = table.get(key + "/rot", 0.0);
    gyroAngle = table.get(key + "/gyroAngle", Rotation2d.fromDegrees(0));
    latestOdometryPose = table.get(key + "/pose", RobotConstants.ODOMETRY.INITIAL_POSE);
  }

  @Override
  public void toLog(LogTable table) {
    table.put(key + "/xSpeed", xSpeed);
    table.put(key + "/ySpeed", ySpeed);
    table.put(key + "/rot", rot);
    table.put(key + "/gyroAngle", gyroAngle);
    table.put(key + "/pose", latestOdometryPose);
  }
}
