package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import entech.subsystems.EntechSubsystem;
import entech.util.EntechUtils;
import frc.robot.RobotConstants;
import frc.robot.commands.test.TestShooterCommand;
import frc.robot.io.RobotIO;

public class ShooterSubsystem extends EntechSubsystem<ShooterInput, ShooterOutput> {

  private static final boolean ENABLED = true;
  private static final double TOLERANCE = 75;

  private CANSparkMax shooterTop;
  private CANSparkMax shooterBottom;

  private SparkPIDController shooterTopPID = null;
  private SparkPIDController shooterBottomPID = null;

  private ShooterInput currentInput = new ShooterInput();

  private IdleMode mode;

  @Override
  public void initialize() {
    if (ENABLED) {
      shooterTop = new CANSparkMax(RobotConstants.PORTS.CAN.SHOOTER_A, MotorType.kBrushless);
      shooterBottom = new CANSparkMax(RobotConstants.PORTS.CAN.SHOOTER_B, MotorType.kBrushless);

      shooterTop.setIdleMode(IdleMode.kCoast);
      shooterBottom.setIdleMode(IdleMode.kCoast);
      mode = IdleMode.kCoast;

      shooterTop.getEncoder().setVelocityConversionFactor(1);
      shooterBottom.getEncoder().setVelocityConversionFactor(1);

      shooterTop.setInverted(false);
      shooterBottom.setInverted(false);

      shooterTopPID = shooterTop.getPIDController();
      shooterBottomPID = shooterBottom.getPIDController();

      setUpPIDConstants(shooterBottomPID);
      setUpPIDConstants(shooterTopPID);
    }
  }

  private void setUpPIDConstants(SparkPIDController pIDController) {
    pIDController.setP(RobotConstants.PID.SHOOTER.KP);
    pIDController.setD(RobotConstants.PID.SHOOTER.KI);
    pIDController.setI(RobotConstants.PID.SHOOTER.KD);
    pIDController.setFF(RobotConstants.PID.SHOOTER.KFF);
  }

  @Override
  public void periodic() {
    if (ENABLED) {
      if (currentInput.getActivate()) {
        shooterTopPID.setReference(currentInput.getSpeed(), CANSparkMax.ControlType.kVelocity);
        shooterBottomPID.setReference(currentInput.getSpeed(), CANSparkMax.ControlType.kVelocity);
      } else {
        shooterBottom.set(0.0);
        shooterTop.set(0.0);
      }
    }
    if (currentInput.isBrakeModeEnabled() && mode != IdleMode.kBrake) {
      shooterTop.setIdleMode(IdleMode.kBrake);
      shooterBottom.setIdleMode(IdleMode.kBrake);
      mode = IdleMode.kBrake;
    } else if (mode != IdleMode.kCoast) {
      shooterTop.setIdleMode(IdleMode.kCoast);
      shooterBottom.setIdleMode(IdleMode.kCoast);
      mode = IdleMode.kCoast;
    }
  }

  private double getCurrentSpeed() {
    double currentSpeed =
        (shooterTop.getEncoder().getVelocity() + shooterBottom.getEncoder().getVelocity()) / 2;
    return currentSpeed;
  }

  @Override
  public boolean isEnabled() {
    return ENABLED;
  }

  @Override
  public void updateInputs(ShooterInput input) {
    RobotIO.processInput(input);
    this.currentInput = input;
  }

  @Override
  public ShooterOutput toOutputs() {
    ShooterOutput shooterOutput = new ShooterOutput();
    shooterOutput.setCurrentSpeed(getCurrentSpeed());
    shooterOutput.setActive(shooterOutput.getCurrentSpeed() != 0);
    shooterOutput.setBrakeModeEnabled(IdleMode.kBrake == mode);
    shooterOutput.setIsAtSpeed(EntechUtils.isWithinTolerance(TOLERANCE,
        shooterOutput.getCurrentSpeed(), currentInput.getSpeed()));
    shooterOutput.setSpeedA(shooterTop.getEncoder().getVelocity());
    shooterOutput.setSpeedB(shooterBottom.getEncoder().getVelocity());
    return shooterOutput;
  }

  @Override
  public Command getTestCommand() {
    SequentialCommandGroup testCommands = new SequentialCommandGroup();
    testCommands.addCommands(new TestShooterCommand(this, RobotConstants.PID.SHOOTER.AMP_SPEED));
    testCommands.addCommands(new WaitCommand(1.0));
    testCommands.addCommands(new TestShooterCommand(this, RobotConstants.PID.SHOOTER.PODIUM_SPEED));
    return testCommands;
  }
}
