package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;

public class ShooterSubsystem extends EntechSubsystem<ShooterInput, ShooterOutput> {

    private CANSparkMax shooterTop;
    private CANSparkMax shooterBottom;

    SparkPIDController shooterTopPID = null;
    SparkPIDController shooterBottomPID = null;

    private boolean active = false;

    private static double maxSpeed = 5500;

    private final boolean ENABLED = true;

    @Override
    public void initialize() {

        shooterTop = new CANSparkMax(RobotConstants.Ports.CAN.SHOOTER_TOP, MotorType.kBrushless);
        shooterBottom = new CANSparkMax(RobotConstants.Ports.CAN.SHOOTER_BOTTOM, MotorType.kBrushless);

        shooterTop.setIdleMode(IdleMode.kCoast);
        shooterBottom.setIdleMode(IdleMode.kCoast);

        shooterTop.setInverted(true);
        shooterBottom.setInverted(true);

        shooterTopPID = shooterTop.getPIDController();
        shooterTopPID.setP(RobotConstants.PID.Shooter.KP);
        shooterTopPID.setD(RobotConstants.PID.Shooter.KD);
        shooterTopPID.setI(RobotConstants.PID.Shooter.KI);
        shooterTopPID.setFF(RobotConstants.PID.Shooter.KFF);

        shooterBottomPID = shooterBottom.getPIDController();
        shooterBottomPID.setP(RobotConstants.PID.Shooter.KP);
        shooterBottomPID.setD(RobotConstants.PID.Shooter.KD);
        shooterBottomPID.setI(RobotConstants.PID.Shooter.KI);
        shooterBottomPID.setFF(RobotConstants.PID.Shooter.KFF);
    }

    public void startShooter() {
        active = true;
    }

    public void stopShooter() {
        active = false;
    }

    public void periodic() {

        SmartDashboard.putNumber("Shooter Target", maxSpeed);
        SmartDashboard.putNumber("Shooter Top", shooterTop.getEncoder().getVelocity());
        SmartDashboard.putNumber("Shooter Bottom", shooterTop.getEncoder().getVelocity());
        SmartDashboard.putNumber("Transfer", shooterTop.getEncoder().getVelocity());

        if(ENABLED) {
            if(active) {
                shooterTopPID.setReference(maxSpeed, CANSparkMax.ControlType.kVelocity);
                shooterBottomPID.setReference(maxSpeed, CANSparkMax.ControlType.kVelocity);
            } else {
                shooterTopPID.setReference(0, CANSparkMax.ControlType.kVelocity);
                shooterBottomPID.setReference(0, CANSparkMax.ControlType.kVelocity);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ENABLED;
    }

    @Override
    public void updateInputs(ShooterInput input) {
        maxSpeed = input.maxSpeed;
        active = input.shooterStatus;
    }

    @Override
    public ShooterOutput getOutputs() {
        return new ShooterOutput();
    }

}
