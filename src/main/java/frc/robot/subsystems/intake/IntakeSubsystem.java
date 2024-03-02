package frc.robot.subsystems.intake;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import frc.robot.commands.test.TestIntakeCommand;

public class IntakeSubsystem extends EntechSubsystem<IntakeInput, IntakeOutput> {

  private final static boolean ENABLED = true;

  private IntakeInput currentInput = new IntakeInput();

  private CANSparkMax intakeMotor;

  @Override
  public void initialize() {
    if (ENABLED) {
      this.intakeMotor = new CANSparkMax(RobotConstants.PORTS.CAN.INTAKE, MotorType.kBrushless);
      this.intakeMotor.setInverted(false);
    }
  }

  @Override
  public void periodic() {
    if (ENABLED) {
      if (currentInput.getActivate()) {
        this.intakeMotor.set(currentInput.getSpeed());
      } else {
        this.intakeMotor.set(0);
      }

      if (currentInput.getBrakeModeEnabled()) {
        this.intakeMotor.setIdleMode(IdleMode.kBrake);
      } else {
        this.intakeMotor.setIdleMode(IdleMode.kCoast);
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
    intakeOutput.setActive(this.intakeMotor.getEncoder().getVelocity() != 0);
    intakeOutput.setCurrentSpeed(this.intakeMotor.getEncoder().getVelocity());
    intakeOutput.setBrakeModeEnabled(IdleMode.kBrake == this.intakeMotor.getIdleMode());
    return intakeOutput;
  }

  @Override
  public Command getTestCommand() {
    return new TestIntakeCommand(this);
  }

}
