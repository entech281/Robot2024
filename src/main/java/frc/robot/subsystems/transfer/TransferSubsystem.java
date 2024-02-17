package frc.robot.subsystems.transfer;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;

public class TransferSubsystem extends EntechSubsystem<TransferInput, TransferOutput>{

    private final boolean ENABLED = false;

    public enum TransferStatus{
        Shooting, Transfering, Intaking, Off
    }

    private TransferInput transferInput = new TransferInput();

    private CANSparkMax transferMotor;

    @Override
    public void initialize() {
        if (ENABLED) {
            transferMotor = new CANSparkMax(RobotConstants.Ports.CAN.TRANSFER, MotorType.kBrushless);
            transferMotor.setInverted(false);
        }
    }

    public void periodic() {
        if(ENABLED) {
            if(transferInput.activate) {
                if(transferInput.currentMode == TransferStatus.Shooting) {
                    transferMotor.set(RobotConstants.TRANSFER.SHOOTING_SPEED);
                } else if (transferInput.currentMode == TransferStatus.Transfering) {
                    transferMotor.set(RobotConstants.TRANSFER.TRANSFERING_SPEED);
                } else if (transferInput.currentMode == TransferStatus.Intaking) {
                    transferMotor.set(RobotConstants.TRANSFER.INTAKING_SPEED);
                }
            } else {
                transferMotor.set(0.0);
            }

            if (transferInput.brakeModeEnabled) {
                transferMotor.setIdleMode(IdleMode.kBrake);
            } else {
                transferMotor.setIdleMode(IdleMode.kCoast);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return ENABLED;
    }

    @Override
    public void updateInputs(TransferInput input) {
        this.transferInput = input;
    }

    @Override
    public TransferOutput getOutputs() {
        TransferOutput transferOutput = new TransferOutput();
        transferOutput.active = transferMotor.getEncoder().getVelocity() != 0;
        transferOutput.brakeModeEnabled = IdleMode.kBrake == transferMotor.getIdleMode();
        transferOutput.currentMode = transferInput.currentMode;
        return transferOutput;
    }

}
