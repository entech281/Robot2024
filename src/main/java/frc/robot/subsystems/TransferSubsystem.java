package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;

public class TransferSubsystem extends EntechSubsystem<TransferInput, TransferOutput>{

    private final boolean ENABLED = false;

    public enum TransferStatus{
        Shooting, Transfering, Intaking, Off
    }

    private TransferInput currentInput = new TransferInput();

    private CANSparkMax transferMotor;

    @Override
    public void initialize() {

        transferMotor = new CANSparkMax(RobotConstants.Ports.CAN.TRANSFER, MotorType.kBrushless);

        transferMotor.setInverted(false);
    }

    public void periodic() {
        if(ENABLED) {
            if(currentInput.activate) {
                if(currentInput.currentMode == TransferStatus.Shooting) {
                    transferMotor.set(RobotConstants.TRANSFER.SHOOTING_SPEED);
                } else if (currentInput.currentMode == TransferStatus.Transfering) {
                    transferMotor.set(RobotConstants.TRANSFER.TRANSFERING_SPEED);
                } else if (currentInput.currentMode == TransferStatus.Intaking) {
                    transferMotor.set(RobotConstants.TRANSFER.INTAKING_SPEED);
                }
            } else {
                transferMotor.set(0.0);
            }

            if (currentInput.brakeModeEnabled) {
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
        this.currentInput = input;
    }

    @Override
    public TransferOutput getOutputs() {
        TransferOutput transferOutput = new TransferOutput();
        transferOutput.active = transferMotor.getEncoder().getVelocity() != 0;
        transferOutput.brakeModeEnabled = IdleMode.kBrake == transferMotor.getIdleMode();
        transferOutput.currentMode = currentInput.currentMode;
        return transferOutput;
    }

}
