package frc.robot.subsystems.transfer;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import frc.robot.commands.test.TestTransferCommand;

public class TransferSubsystem extends EntechSubsystem<TransferInput, TransferOutput> {

  private static final boolean ENABLED = true;


  public enum TransferPreset {
    Shooting, Transferring, Intaking1, Intaking2, Retracting, Ejecting, Testing, Off
  }

  private TransferInput currentInput = new TransferInput();

  private CANSparkMax transferMotor;

  @Override
  public void initialize() {
    if (ENABLED) {
      transferMotor = new CANSparkMax(RobotConstants.PORTS.CAN.TRANSFER, MotorType.kBrushless);
      transferMotor.setInverted(false);
      transferMotor.setIdleMode(IdleMode.kCoast);
    }
  }

  @Override
  public void periodic() {
    if (ENABLED) {
      if (currentInput.getActivate()) {
        if (currentInput.getSpeedPreset() == TransferPreset.Shooting) {
          transferMotor.set(RobotConstants.TRANSFER.SHOOTING_SPEED);
        } else if (currentInput.getSpeedPreset() == TransferPreset.Transferring) {
          transferMotor.set(RobotConstants.TRANSFER.TRANSFERRING_SPEED);
        } else if (currentInput.getSpeedPreset() == TransferPreset.Intaking1) {
          transferMotor.set(RobotConstants.TRANSFER.INTAKING_SPEED_FAST);
        } else if (currentInput.getSpeedPreset() == TransferPreset.Intaking2) {
          transferMotor.set(RobotConstants.TRANSFER.INTAKING_SPEED_SLOW);
        } else if (currentInput.getSpeedPreset() == TransferPreset.Ejecting) {
          transferMotor.set(RobotConstants.TRANSFER.EJECTING_SPEED);
        } else if (currentInput.getSpeedPreset() == TransferPreset.Retracting) {
          transferMotor.set(RobotConstants.TRANSFER.RETRACTING_SPEED);
        } else if (currentInput.getSpeedPreset() == TransferPreset.Testing) {
          transferMotor.set(RobotConstants.TRANSFER.TESTING_SPEED);
        }
      } else {
        transferMotor.set(0.0);
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
  public TransferOutput toOutputs() {
    TransferOutput transferOutput = new TransferOutput();
    transferOutput.setActive(transferMotor.getEncoder().getVelocity() != 0);
    transferOutput.setBrakeModeEnabled(IdleMode.kBrake == transferMotor.getIdleMode());
    transferOutput.setCurrentSpeed(transferMotor.getEncoder().getVelocity());
    transferOutput.setCurrentMode(currentInput.getSpeedPreset());
    return transferOutput;
  }

  @Override
  public Command getTestCommand() {
    return new TestTransferCommand(this);
  }
}
