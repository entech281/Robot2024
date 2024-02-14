package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;

public class ShooterSubsystem extends EntechSubsystem<ShooterInput, ShooterOutput> {

    private CANSparkMax shooterA;
    private CANSparkMax shooterB;

    SparkPIDController shooterAPID = null;
    SparkPIDController shooterBPID = null;

    private boolean active = false;
    private boolean coastModeEnabled;

    static double maxSpeed = 5500;

    private final boolean ENABLED = true;

    @Override
    public void initialize() {

        shooterA = new CANSparkMax(RobotConstants.Ports.CAN.SHOOTER_A, MotorType.kBrushless);
        shooterB = new CANSparkMax(RobotConstants.Ports.CAN.SHOOTER_B, MotorType.kBrushless);

        shooterA.setIdleMode(IdleMode.kBrake);
        shooterB.setIdleMode(IdleMode.kBrake);

        shooterA.setInverted(true);
        shooterB.setInverted(true);

        shooterAPID = shooterA.getPIDController();
        shooterAPID.setP(RobotConstants.PID.Shooter.KP);
        shooterAPID.setD(RobotConstants.PID.Shooter.KD);
        shooterAPID.setI(RobotConstants.PID.Shooter.KI);
        shooterAPID.setFF(RobotConstants.PID.Shooter.KFF);

        shooterBPID = shooterB.getPIDController();
        shooterBPID.setP(RobotConstants.PID.Shooter.KP);
        shooterBPID.setD(RobotConstants.PID.Shooter.KD);
        shooterBPID.setI(RobotConstants.PID.Shooter.KI);
        shooterBPID.setFF(RobotConstants.PID.Shooter.KFF);
    }

    public void startShooter() {
        active = true;
    }

    public void stopShooter() {
        active = false;
    }

    public void periodic() {

        SmartDashboard.putNumber("Shooter Target", maxSpeed);
        SmartDashboard.putNumber("Shooter Top", shooterA.getEncoder().getVelocity());
        SmartDashboard.putNumber("Shooter Bottom", shooterA.getEncoder().getVelocity());
        SmartDashboard.putNumber("Transfer", shooterA.getEncoder().getVelocity());

        if(ENABLED) {
            if(active) {
                shooterAPID.setReference(maxSpeed, CANSparkMax.ControlType.kVelocity);
                shooterBPID.setReference(maxSpeed, CANSparkMax.ControlType.kVelocity);
            } else {
                shooterAPID.setReference(0, CANSparkMax.ControlType.kVelocity);
                shooterBPID.setReference(0, CANSparkMax.ControlType.kVelocity);
            }
        }

        if (coastModeEnabled) {
            shooterA.setIdleMode(IdleMode.kCoast);
            shooterB.setIdleMode(IdleMode.kCoast);
        } else {
            shooterA.setIdleMode(IdleMode.kBrake);
            shooterB.setIdleMode(IdleMode.kBrake);
        }
    }

    @Override
    public boolean isEnabled() {
        return ENABLED;
    }

    @Override
    public void updateInputs(ShooterInput input) {
        maxSpeed = input.maxSpeed;
        active = input.active;
        coastModeEnabled = input.coastModeEnabled;
    }

    @Override
    public ShooterOutput getOutputs() {
        return new ShooterOutput();
    }

}
