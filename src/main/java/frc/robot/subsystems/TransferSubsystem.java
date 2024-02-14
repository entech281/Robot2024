package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;

public class TransferSubsystem extends EntechSubsystem<TransferInput, TransferOutput>{

    private final boolean ENABLED = true;

    public boolean active = false;

    private TransferStatus currentMode = TransferStatus.Off;

    public enum TransferStatus{
        Shooting, Transfering, Intaking, Off
    }

    private boolean coastModeEnabled = true;

    private CANSparkMax transferMotor;

    @Override
    public void initialize() {

        transferMotor = new CANSparkMax(RobotConstants.Ports.CAN.TRANSFER, MotorType.kBrushless);

        transferMotor.setInverted(false);
    }

    public void periodic() {
        if(ENABLED) {
            if(active) {
                if(currentMode == TransferStatus.Shooting) {
                    transferMotor.set(RobotConstants.TRANSFER.SHOOTING_SPEED);
                } else if (currentMode == TransferStatus.Transfering) {
                    transferMotor.set(RobotConstants.TRANSFER.TRANSFERING_SPEED);
                } else if (currentMode == TransferStatus.Intaking) {
                    transferMotor.set(RobotConstants.TRANSFER.INTAKING_SPEED);
                }
            } else {
                transferMotor.set(0.0);
            }

            if (coastModeEnabled) {
                transferMotor.setIdleMode(IdleMode.kCoast);
            } else {
                transferMotor.setIdleMode(IdleMode.kBrake);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ENABLED;
    }

    @Override
    public void updateInputs(TransferInput input) {
        active = input.active;
        coastModeEnabled = input.coastModeEnabled;
        currentMode = input.mode;
    }

    @Override
    public TransferOutput getOutputs() {
        return new TransferOutput();
    }

}
