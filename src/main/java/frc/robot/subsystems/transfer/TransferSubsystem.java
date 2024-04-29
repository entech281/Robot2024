package frc.robot.subsystems.transfer;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import frc.robot.commands.test.TestTransferCommand;
import frc.robot.io.RobotIO;

public class TransferSubsystem extends EntechSubsystem<TransferInput, TransferOutput> {

  private static final boolean ENABLED = true;


  public enum TransferPreset {
    SHOOTING, TRANSFERRING, INTAKING1, INTAKING2, RETRACTING, EJECTING, TESTING, OFF
  }

  private TransferInput currentInput = new TransferInput();

  private CANSparkMax transferMotor;

  private IdleMode mode;

  @Override
  public void initialize() {
    if (ENABLED) {
      transferMotor = new CANSparkMax(RobotConstants.PORTS.CAN.TRANSFER, MotorType.kBrushless);
      transferMotor.setInverted(false);
      transferMotor.setIdleMode(IdleMode.kCoast);
      mode = IdleMode.kCoast;
    }
  }

  @Override
  public void periodic() {
    if (ENABLED) {
      if (currentInput.getActivate()) {
        if (currentInput.getSpeedPreset() == TransferPreset.SHOOTING) {
          transferMotor.set(RobotConstants.TRANSFER.SHOOTING_SPEED);
        } else if (currentInput.getSpeedPreset() == TransferPreset.TRANSFERRING) {
          transferMotor.set(RobotConstants.TRANSFER.TRANSFERRING_SPEED);
        } else if (currentInput.getSpeedPreset() == TransferPreset.INTAKING1) {
          transferMotor.set(RobotConstants.TRANSFER.INTAKING_SPEED_FAST);
        } else if (currentInput.getSpeedPreset() == TransferPreset.INTAKING2) {
          transferMotor.set(RobotConstants.TRANSFER.INTAKING_SPEED_SLOW);
        } else if (currentInput.getSpeedPreset() == TransferPreset.EJECTING) {
          transferMotor.set(RobotConstants.TRANSFER.EJECTING_SPEED);
        } else if (currentInput.getSpeedPreset() == TransferPreset.RETRACTING) {
          transferMotor.set(RobotConstants.TRANSFER.RETRACTING_SPEED);
        } else if (currentInput.getSpeedPreset() == TransferPreset.TESTING) {
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
    RobotIO.processInput(input);
    this.currentInput = input;
  }

  @Override
  public TransferOutput toOutputs() {
    TransferOutput transferOutput = new TransferOutput();
    transferOutput.setActive(transferMotor.getEncoder().getVelocity() != 0);
    transferOutput.setBrakeModeEnabled(IdleMode.kBrake == mode);
    transferOutput.setCurrentSpeed(transferMotor.getEncoder().getVelocity());
    transferOutput.setCurrentMode(currentInput.getSpeedPreset());
    return transferOutput;
  }

  @Override
  public Command getTestCommand() {
    return new TestTransferCommand(this);
  }
}
