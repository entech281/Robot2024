package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;

public class ShooterSubsystem extends EntechSubsystem<ShooterInput, ShooterOutput> {

  private final boolean ENABLED = false;

  private CANSparkMax shooterTop;
  private CANSparkMax shooterBottom;

  SparkPIDController shooterTopPID = null;
  SparkPIDController shooterBottomPID = null;

  private ShooterInput currentInput = new ShooterInput();

  @Override
  public void initialize() {
    if (ENABLED) {
      shooterTop = new CANSparkMax(RobotConstants.Ports.CAN.SHOOTER_A, MotorType.kBrushless);
      shooterBottom = new CANSparkMax(RobotConstants.Ports.CAN.SHOOTER_B, MotorType.kBrushless);

      updateBrakeMode();

      shooterTop.setInverted(true);
      shooterBottom.setInverted(true);

      setUpPIDConstants(shooterTop.getPIDController());
      setUpPIDConstants(shooterBottom.getPIDController());
    }
  }

  private void setUpPIDConstants(SparkPIDController pIDController) {
    pIDController.setP(RobotConstants.PID.Pivot.KP);
    pIDController.setD(RobotConstants.PID.Pivot.KI);
    pIDController.setI(RobotConstants.PID.Pivot.KD);
    pIDController.setFF(RobotConstants.PID.Shooter.KFF);
  }

  private void updateBrakeMode() {
    if (currentInput.getBrakeModeEnabled()) {
      shooterTop.setIdleMode(IdleMode.kBrake);
      shooterBottom.setIdleMode(IdleMode.kBrake);
    } else {
      shooterTop.setIdleMode(IdleMode.kCoast);
      shooterBottom.setIdleMode(IdleMode.kCoast);
    }
  }

  public void periodic() {

    if (ENABLED) {
      if (currentInput.getActivate()) {
        shooterTopPID.setReference(currentInput.getSpeed(), CANSparkMax.ControlType.kVelocity);
        shooterBottomPID.setReference(currentInput.getSpeed(), CANSparkMax.ControlType.kVelocity);
      } else {
        shooterTopPID.setReference(0, CANSparkMax.ControlType.kVelocity);
        shooterBottomPID.setReference(0, CANSparkMax.ControlType.kVelocity);
      }

      if (currentInput.getBrakeModeEnabled()) {
        shooterTop.setIdleMode(IdleMode.kBrake);
        shooterBottom.setIdleMode(IdleMode.kBrake);
      } else {
        shooterTop.setIdleMode(IdleMode.kCoast);
        shooterBottom.setIdleMode(IdleMode.kCoast);
      }
    }
  }

  @Override
  public boolean isEnabled() {
    return ENABLED;
  }

  @Override
  public void updateInputs(ShooterInput input) {
    this.currentInput = input;
  }

  @Override
  public ShooterOutput getOutputs() {
    ShooterOutput shooterOutput = new ShooterOutput();
    shooterOutput.setCurrentSpeed( (shooterTop.getEncoder().getVelocity() + shooterBottom.getEncoder().getVelocity()) / 2);
    shooterOutput.setActive(shooterOutput.getCurrentSpeed() != 0);
    shooterOutput.setBrakeModeEnabled(IdleMode.kBrake == shooterTop.getIdleMode());
    return shooterOutput;
  }

}
