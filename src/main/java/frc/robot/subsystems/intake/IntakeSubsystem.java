package frc.robot.subsystems.intake;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import frc.robot.commands.testCommands.TestIntakeCommand;

public class IntakeSubsystem extends EntechSubsystem<IntakeInput, IntakeOutput> {

  private final static boolean ENABLED = false;

  IntakeInput currentInput = new IntakeInput();

  CANSparkMax intakeMotor;

  @Override
  public void initialize() {
    if (ENABLED) {
      intakeMotor = new CANSparkMax(RobotConstants.Ports.CAN.INTAKE, MotorType.kBrushless);
      intakeMotor.setInverted(false);
    }
  }

  public void periodic() {
    if (ENABLED) {
      if (currentInput.getActivate()) {
        intakeMotor.set(currentInput.getSpeed());
      } else {
        intakeMotor.set(0);
      }

      if (currentInput.getBrakeModeEnabled()) {
        intakeMotor.setIdleMode(IdleMode.kBrake);
      } else {
        intakeMotor.setIdleMode(IdleMode.kCoast);
      }
    }
  }

  @Override
  public boolean isEnabled() {
    return ENABLED;
  }

  @Override
  public void updateInputs(IntakeInput input) {
    this.currentInput = input;
  }

  @Override
  public IntakeOutput toOutputs() {
    IntakeOutput intakeOutput = new IntakeOutput();
    intakeOutput.setActive(intakeMotor.getEncoder().getVelocity() != 0);
    intakeOutput.setCurrentSpeed(intakeMotor.getEncoder().getVelocity());
    intakeOutput.setBrakeModeEnabled(IdleMode.kBrake == intakeMotor.getIdleMode());
    return intakeOutput;
  }

  @Override
  public Command getTestCommand() {
    return new TestIntakeCommand(this);
  }

}
