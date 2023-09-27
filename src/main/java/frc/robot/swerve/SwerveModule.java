package frc.robot.swerve;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

public class SwerveModule implements Sendable {
    private final String name;

    private CANSparkMax driveMotor;
    private CANSparkMax angleMotor;
    private ThriftyEncoder encoder;

    private final double encoderOffset;

    private double angleSetPoint = 0;
    private PIDController anglePID = new PIDController(0.001, 0, 0); // default

    private double drivePower = 0;
    private double anglePower = 0;

    public SwerveModule(SwerveModuleConfig constants) {
        this(constants.name, constants.driveMotorID, constants.angleMotorID, constants.encoderPort,
                constants.encoderOffset);
        setPID(constants.kP, constants.kI, constants.kD, constants.tolerance);
    }

    public SwerveModule(String name, int driveID, int angleID, int encoderPort, double encoderOffset, double kP,
            double kI, double kD, double tolerance) {
        this(name, driveID, angleID, encoderPort, encoderOffset);
        setPID(kP, kI, kD, tolerance);
    }

    public SwerveModule(String name, int driveID, int angleID, int encoderPort, double encoderOffset) {
        this.name = name;
        driveMotor = new CANSparkMax(driveID, MotorType.kBrushless);
        angleMotor = new CANSparkMax(angleID, MotorType.kBrushless);
        encoder = new ThriftyEncoder(encoderPort);
        this.encoderOffset = encoderOffset;
    }

    public void setPID(double p, double i, double d, double tolerance) {
        anglePID.setPID(p, i, d);
        anglePID.setTolerance(tolerance);
    }

    public double getAngle() {
        return encoder.getPosition() - encoderOffset;
    }

    public void periodic() {
        driveMotor.set(drivePower);

        if (!anglePID.atSetpoint()) {
            angleMotor.set(anglePID.calculate(getAngle(), angleSetPoint));
        } else {
            anglePID.reset();
            angleMotor.stopMotor();
        }
    }

    public void setDrivePower(double power) {
        drivePower = power;
    }

    public void setDesiredAngle(double angle) {
        angleSetPoint = angle;
    }

    @Override
    public void initSendable(SendableBuilder sendable) {
        sendable.addDoubleProperty(name + " Angle SetPoint", this::getAngleSetPoint, null);
        sendable.addDoubleProperty(name + " Encoder Angle", this::getAngle, null);
        sendable.addDoubleProperty(name + " Drive Power", this::getDrivePower, null);
        sendable.addDoubleProperty(name + " Angle Power", this::getAnglePower, null);
    }

    /**
     * @return double return the encoderOffset
     */
    public double getEncoderOffset() {
        return encoderOffset;
    }

    /**
     * @return double return the angleSetPoint
     */
    public double getAngleSetPoint() {
        return angleSetPoint;
    }

    /**
     * @return double return the drivePower
     */
    public double getDrivePower() {
        return drivePower;
    }

    /**
     * @return double return the anglePower
     */
    public double getAnglePower() {
        return anglePower;
    }
}
