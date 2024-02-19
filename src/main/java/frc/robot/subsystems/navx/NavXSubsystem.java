package frc.robot.subsystems.navx;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import entech.subsystems.EntechSubsystem;

public class NavXSubsystem extends EntechSubsystem<NavXInput, NavXOutput> {
  private static final boolean ENABLED = true;
  private AHRS gyro;

  @Override
  public NavXOutput getOutputs() {
    NavXOutput output = new NavXOutput();

    output.yaw = gyro.getAngle();
    output.pitch = gyro.getPitch();
    output.roll = gyro.getRoll();
    output.yawRate = gyro.getRate();
    output.chassisSpeeds = getChassisSpeeds();
    output.zVelocity = gyro.getVelocityZ();
    output.temperature = gyro.getTempC();
    output.angleAdjustment = gyro.getAngleAdjustment();
    output.compassHeading = gyro.getCompassHeading();
    output.isCalibrating = gyro.isCalibrating();
    output.isMagneticDisturbance = gyro.isMagneticDisturbance();
    output.isMagnetometerCalibrated = gyro.isMagnetometerCalibrated();
    output.isMoving = gyro.isMoving();
    output.isRotating = gyro.isRotating();

    return output;
  }

  @Override
  public void periodic() {
    SmartDashboard.putData(gyro);
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
    return ChassisSpeeds.fromRobotRelativeSpeeds(gyro.getVelocityX(), gyro.getVelocityY(), radiansPerSecond, gyro.getRotation2d());
  }

  @Override
  public boolean isEnabled() {
    return ENABLED;
  }

  @Override
  public void updateInputs(NavXInput input) {
    return;
  }

  public void zeroYaw() {
    gyro.zeroYaw();
  }
}
