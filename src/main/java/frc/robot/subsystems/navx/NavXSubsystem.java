package frc.robot.subsystems.navx;

import org.ejml.simple.UnsupportedOperation;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import entech.subsystems.EntechSubsystem;
import entech.util.StoppingCounter;

public class NavXSubsystem extends EntechSubsystem<NavXInput, NavXOutput> {
  private static final boolean ENABLED = true;
  private AHRS gyro;
  private final StoppingCounter faultCounter = new StoppingCounter(3.5);
  private boolean faultDetected = false;

  @Override
  public NavXOutput toOutputs() {
    NavXOutput output = new NavXOutput();

    output.setYaw(gyro.getAngle());
    output.setPitch(gyro.getPitch());
    output.setRoll(gyro.getRoll());
    output.setYawRate(gyro.getRate());
    output.setChassisSpeeds(getChassisSpeeds());
    output.setZVelocity(gyro.getVelocityZ());
    output.setTemperature(gyro.getTempC());
    output.setAngleAdjustment(gyro.getAngleAdjustment());
    output.setCompassHeading(gyro.getCompassHeading());
    output.setIsCalibrating(gyro.isCalibrating());
    output.setIsMagneticDisturbance(gyro.isMagneticDisturbance());
    output.setIsMagnetometerCalibrated(gyro.isMagnetometerCalibrated());
    output.setIsMoving(gyro.isMoving());
    output.setIsRotating(gyro.isRotating());
    output.setIsFaultDetected(faultDetected);

    return output;
  }

  @Override
  public void periodic() {
    SmartDashboard.putData(gyro);
    faultDetected = faultCounter.isFinished(gyro.isCalibrating());
  }

  @Override
  public void initialize() {
    if (ENABLED) {
      gyro = new AHRS(SPI.Port.kMXP);

      gyro.reset();

      while (gyro.isCalibrating()) {
        ;
      }

      gyro.zeroYaw();
    }
  }

  private ChassisSpeeds getChassisSpeeds() {
    double radiansPerSecond = Units.degreesToRadians(gyro.getRate());
    return ChassisSpeeds.fromRobotRelativeSpeeds(gyro.getVelocityX(), gyro.getVelocityY(),
        radiansPerSecond, gyro.getRotation2d());
  }

  @Override
  public boolean isEnabled() {
    return ENABLED;
  }

  @Override
  public void updateInputs(NavXInput input) {
    throw new UnsupportedOperation();
  }

  public void zeroYaw() {
    gyro.zeroYaw();
  }

  @Override
  public Command getTestCommand() {
    return Commands.none();
  }

  public void setAngleAdjustment(double angleAdjustment) {
    gyro.setAngleAdjustment(angleAdjustment);
  }
}
