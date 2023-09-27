package frc.robot.swerve;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;

public class SwerveModule {
    private CANSparkMax driveMotor;
    private CANSparkMax angleMotor;
    private ThriftyEncoder encoder;

    private double encoderOffset;

    private double angleSetPoint = 0;
    private PIDController anglePID = new PIDController(0.001, 0, 0); // default

    private double drivePower = 0;

    public SwerveModule(int driveID, int angleID, int encoderPort, double encoderOffset) {
        driveMotor = new CANSparkMax(driveID, MotorType.kBrushless);
        angleMotor = new CANSparkMax(angleID, MotorType.kBrushless);
        encoder = new ThriftyEncoder(encoderPort);
        this.encoderOffset = encoderOffset;
    }

    public void periodic() {
        driveMotor.set(drivePower);
    }

    public void setDrivePower(double power) {
        drivePower = power;
    }

    public void setAngle(double angle) {
        angleSetPoint = angle;
    }
}
