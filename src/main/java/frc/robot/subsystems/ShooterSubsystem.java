package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;

public class ShooterSubsystem extends EntechSubsystem<ShooterInput, ShooterOutput> {

    private final boolean ENABLED = false;

    private CANSparkMax shooterA;
    private CANSparkMax shooterB;

    SparkPIDController shooterAPID = null;
    SparkPIDController shooterBPID = null;

    private ShooterInput shooterInput = new ShooterInput();

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

    public void periodic() {

        SmartDashboard.putNumber("Shooter Target", shooterInput.speed);
        SmartDashboard.putNumber("Shooter Top", shooterA.getEncoder().getVelocity());
        SmartDashboard.putNumber("Shooter Bottom", shooterA.getEncoder().getVelocity());
        SmartDashboard.putNumber("Transfer", shooterA.getEncoder().getVelocity());

        if(ENABLED) {
            if(shooterInput.activate) {
                shooterAPID.setReference(shooterInput.speed, CANSparkMax.ControlType.kVelocity);
                shooterBPID.setReference(shooterInput.speed, CANSparkMax.ControlType.kVelocity);
            } else {
                shooterAPID.setReference(0, CANSparkMax.ControlType.kVelocity);
                shooterBPID.setReference(0, CANSparkMax.ControlType.kVelocity);
            }
        }

        if (shooterInput.brakeModeEnabled) {
            shooterA.setIdleMode(IdleMode.kBrake);
            shooterB.setIdleMode(IdleMode.kBrake);
        } else {
            shooterA.setIdleMode(IdleMode.kCoast);
            shooterB.setIdleMode(IdleMode.kCoast);
        }
    }

    @Override
    public boolean isEnabled() {
        return ENABLED;
    }

    @Override
    public void updateInputs(ShooterInput input) {
        this.shooterInput = input;
    }

    @Override
    public ShooterOutput getOutputs() {
        ShooterOutput shooterOutput = new ShooterOutput();
        shooterOutput.speed = (shooterA.getEncoder().getVelocity() + shooterB.getEncoder().getVelocity()) / 2;
        shooterOutput.active = shooterOutput.speed != 0;
        shooterOutput.brakeModeEnabled = IdleMode.kBrake == shooterA.getIdleMode();
        return shooterOutput;
    }

}
