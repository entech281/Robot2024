package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.wpilibj2.command.Command;
import entech.subsystems.EntechSubsystem;
import entech.util.EntechUtils;
import frc.robot.RobotConstants;
import frc.robot.commands.test.TestShooterCommand;

public class ShooterSubsystem extends EntechSubsystem<ShooterInput, ShooterOutput> {

  private static final boolean ENABLED = true;
  private static final double TOLERANCE = 75;

  private CANSparkMax shooterTop;
  private CANSparkMax shooterBottom;

  private SparkPIDController shooterTopPID = null;
  private SparkPIDController shooterBottomPID = null;

  private ShooterInput currentInput = new ShooterInput();

  @Override
  public void initialize() {
    if (ENABLED) {
      shooterTop = new CANSparkMax(RobotConstants.PORTS.CAN.SHOOTER_A, MotorType.kBrushless);
      shooterBottom = new CANSparkMax(RobotConstants.PORTS.CAN.SHOOTER_B, MotorType.kBrushless);

      shooterTop.getEncoder().setVelocityConversionFactor(1);
      shooterBottom.getEncoder().setVelocityConversionFactor(1);

      updateBrakeMode();

      shooterTop.setInverted(true);
      shooterBottom.setInverted(true);

      shooterTopPID = shooterTop.getPIDController();
      shooterBottomPID = shooterBottom.getPIDController();

      setUpPIDConstants(shooterTopPID);
      setUpPIDConstants(shooterBottomPID);
    }
  }

  private void setUpPIDConstants(SparkPIDController pIDController) {
    pIDController.setP(RobotConstants.PID.SHOOTER.KP);
    pIDController.setD(RobotConstants.PID.SHOOTER.KI);
    pIDController.setI(RobotConstants.PID.SHOOTER.KD);
    pIDController.setFF(RobotConstants.PID.SHOOTER.KFF);
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

  @Override
  public void periodic() {
    if (ENABLED) {
      if (currentInput.getActivate()) {
        shooterTopPID.setReference(-currentInput.getSpeed(), CANSparkMax.ControlType.kVelocity);
        shooterBottomPID.setReference(-currentInput.getSpeed(), CANSparkMax.ControlType.kVelocity);
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

  private double getCurrentSpeed() {
    double currentSpeed =
        -(shooterTop.getEncoder().getVelocity() + shooterBottom.getEncoder().getVelocity()) / 2;
    return currentSpeed;
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
  public ShooterOutput toOutputs() {
    ShooterOutput shooterOutput = new ShooterOutput();
    shooterOutput.setCurrentSpeed(getCurrentSpeed());
    shooterOutput.setActive(shooterOutput.getCurrentSpeed() != 0);
    shooterOutput.setBrakeModeEnabled(IdleMode.kBrake == shooterTop.getIdleMode());
    shooterOutput.setIsAtSpeed(EntechUtils.isWithinTolerance(TOLERANCE,
        shooterOutput.getCurrentSpeed(), currentInput.getSpeed()));
    return shooterOutput;
  }

  @Override
  public Command getTestCommand() {
    return new TestShooterCommand(this);
  }
}
