package frc.robot.swerve;

public class SwerveModuleConfig {
    public final String name;

    public final int driveMotorID;
    public final int angleMotorID;
    public final int encoderPort;

    public final double encoderOffset;

    public final double kP, kI, kD, tolerance;

    public SwerveModuleConfig(String name, int driveMotorID, int angleMotorID, int encoderPort, double encoderOffset,
            double kP,
            double kI, double kD, double tolerance) {
        this.name = name;

        this.angleMotorID = angleMotorID;
        this.driveMotorID = driveMotorID;
        this.encoderPort = encoderPort;

        this.encoderOffset = encoderOffset;

        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.tolerance = tolerance;
    }
}
