package frc.robot.subsystems.drive;


import edu.wpi.first.math.kinematics.SwerveModulePosition;
import entech.subsystems.SubsystemOutput;
import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.Logger;

@AutoLog
public class DriveOutput implements SubsystemOutput {
  public SwerveModulePosition[] modulePositions;
  public double[] rawAbsoluteEncoders;
  public double[] virtualAbsoluteEncoders;

  @Override
  public void log() {
    Logger.recordOutput("driveOutput", modulePositions);
    Logger.recordOutput("rawAbsoluteEncoders", rawAbsoluteEncoders);
    Logger.recordOutput("virtualAbsoluteEncoders", virtualAbsoluteEncoders);
  }
}
