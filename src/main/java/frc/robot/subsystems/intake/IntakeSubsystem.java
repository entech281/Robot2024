package frc.robot.subsystems.intake;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;

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
      if (currentInput.activate) {
        intakeMotor.set(RobotConstants.INTAKE.INTAKE_SPEED);
      } else {
        intakeMotor.set(0);
      }

      if (currentInput.brakeModeEnabled) {
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
  public IntakeOutput getOutputs() {
    IntakeOutput intakeOutput = new IntakeOutput();
    intakeOutput.active = intakeMotor.getEncoder().getVelocity()!=0;
    intakeOutput.currentSpeed = intakeMotor.getEncoder().getVelocity();
    intakeOutput.brakeModeEnabled = IdleMode.kBrake==intakeMotor.getIdleMode();
    intakeOutput.currentSpeed = intakeMotor.getEncoder().getVelocity();
    return intakeOutput;
  }

}
