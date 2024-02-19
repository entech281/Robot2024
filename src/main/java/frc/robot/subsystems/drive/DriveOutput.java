package frc.robot.subsystems.drive;


import org.littletonrobotics.junction.Logger;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import entech.subsystems.SubsystemOutput;

public class DriveOutput implements SubsystemOutput {
  private SwerveModulePosition[] modulePositions;
  private double[] rawAbsoluteEncoders;
  private double[] virtualAbsoluteEncoders;

  @Override
  public void log() {
    Logger.recordOutput("driveOutput/modulePositions", modulePositions);
    Logger.recordOutput("driveOutput/rawAbsoluteEncoders", rawAbsoluteEncoders);
    Logger.recordOutput("driveOutput/virtualAbsoluteEncoders", virtualAbsoluteEncoders);
  }

  public SwerveModulePosition[] getModulePositions() {
    return this.modulePositions;
  }

  public void setModulePositions(SwerveModulePosition[] modulePositions) {
    this.modulePositions = modulePositions;
  }

  public double[] getRawAbsoluteEncoders() {
    return this.rawAbsoluteEncoders;
  }

  public void setRawAbsoluteEncoders(double[] rawAbsoluteEncoders) {
    this.rawAbsoluteEncoders = rawAbsoluteEncoders;
  }

  public double[] getVirtualAbsoluteEncoders() {
    return this.virtualAbsoluteEncoders;
  }

  public void setVirtualAbsoluteEncoders(double[] virtualAbsoluteEncoders) {
    this.virtualAbsoluteEncoders = virtualAbsoluteEncoders;
  }
}
