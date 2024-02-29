package frc.robot.subsystems.transfer;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import frc.robot.commands.TestTransferCommand;

public class TransferSubsystem extends EntechSubsystem<TransferInput, TransferOutput> {

  private static final boolean ENABLED = false;


  public enum TransferPreset {
    SHOOTING, TRANSFERING, INTAKING, TESTING, OFF
  }

  private TransferInput currentInput = new TransferInput();

  private CANSparkMax transferMotor;

  @Override
  public void initialize() {
    if (ENABLED) {
      transferMotor = new CANSparkMax(RobotConstants.Ports.CAN.TRANSFER, MotorType.kBrushless);
      transferMotor.setInverted(false);
    }
  }

  @Override
  public void periodic() {
    if (ENABLED) {
      if (currentInput.getActivate()) {
        if (currentInput.getSpeedPreset() == TransferPreset.SHOOTING) {
          transferMotor.set(RobotConstants.TRANSFER.SHOOTING_SPEED);
        } else if (currentInput.getSpeedPreset() == TransferPreset.TRANSFERING) {
          transferMotor.set(RobotConstants.TRANSFER.TRANSFERING_SPEED);
        } else if (currentInput.getSpeedPreset() == TransferPreset.INTAKING) {
          transferMotor.set(RobotConstants.TRANSFER.INTAKING_SPEED);
        } else if (currentInput.getSpeedPreset() == TransferPreset.TESTING) {
          transferMotor.set(RobotConstants.TRANSFER.TESTING_SPEED);
        }
      } else {
        transferMotor.set(0.0);
      }

      if (currentInput.getBrakeModeEnabled()) {
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
