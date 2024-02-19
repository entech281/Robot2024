package frc.robot.subsystems.drive;


import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.Logger;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import entech.subsystems.SubsystemOutput;

@AutoLog
public class DriveOutput implements SubsystemOutput {
  public SwerveModulePosition[] modulePositions;
  public double[] rawAbsoluteEncoders;
  public double[] virtualAbsoluteEncoders;

  @Override
  public void log() {
    Logger.recordOutput("driveOutput/modulePositions", modulePositions);
    Logger.recordOutput("driveOutput/rawAbsoluteEncoders", rawAbsoluteEncoders);
    Logger.recordOutput("driveOutput/virtualAbsoluteEncoders", virtualAbsoluteEncoders);
  }
}
