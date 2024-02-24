package frc.robot.subsystems.pivot;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;

public class PivotSubsystem extends EntechSubsystem<PivotInput, PivotOutput> {

  private boolean ENABLED = false;

  private PivotInput currentInput = new PivotInput();

  private CANSparkMax pivotLeft;
  private CANSparkMax pivotRight;

  @Override
  public void initialize() {
    if (ENABLED) {
      pivotLeft = new CANSparkMax(RobotConstants.Ports.CAN.PIVOT_A, MotorType.kBrushless);
      pivotRight = new CANSparkMax(RobotConstants.Ports.CAN.PIVOT_B, MotorType.kBrushless);

      pivotLeft.getEncoder()
          .setPositionConversionFactor(RobotConstants.PIVOT.PIVOT_CONVERSION_FACTOR);
      pivotRight.getEncoder()
          .setPositionConversionFactor(RobotConstants.PIVOT.PIVOT_CONVERSION_FACTOR);

      updateBrakeMode();

      pivotLeft.setInverted(true);
      pivotRight.setInverted(true);

      setUpPIDConstants(pivotLeft.getPIDController());
      setUpPIDConstants(pivotRight.getPIDController());
    }
  }

  private void setUpPIDConstants(SparkPIDController pIDController) {
    pIDController.setP(RobotConstants.PID.PIVOT.KP);
    pIDController.setD(RobotConstants.PID.PIVOT.KI);
    pIDController.setI(RobotConstants.PID.PIVOT.KD);
  }

  private void updateBrakeMode() {
    if (currentInput.getBrakeModeEnabled()) {
      pivotLeft.setIdleMode(IdleMode.kBrake);
      pivotRight.setIdleMode(IdleMode.kBrake);
    } else {
      pivotLeft.setIdleMode(IdleMode.kCoast);
      pivotRight.setIdleMode(IdleMode.kCoast);
    }
  }

  private double clampRequestedPosition(double position) {
    if (position < 0) {
      DriverStation.reportWarning(
          "Pivot tried to go to " + currentInput.getRequestedPosition() + " value was changed to 0",
          null);
      return 0;
    } else if (position > RobotConstants.PIVOT.UPPER_SOFT_LIMIT_DEG) {
      DriverStation.reportWarning("Pivot tried to go to " + currentInput.getRequestedPosition()
          + " value was changed to " + RobotConstants.PIVOT.PIVOT_CONVERSION_FACTOR, null);
      return RobotConstants.PIVOT.UPPER_SOFT_LIMIT_DEG;
    } else {
      return position;
    }
  }

  private void setPosition(double position) {
    pivotLeft.getPIDController().setReference(position, CANSparkMax.ControlType.kPosition);
    pivotRight.getPIDController().setReference(position, CANSparkMax.ControlType.kPosition);
  }

  public void periodic() {

    double clampedPosition = clampRequestedPosition(currentInput.getRequestedPosition());

    if (ENABLED) {
      setPosition(clampedPosition);

      updateBrakeMode();
    }
  }

  @Override
  public boolean isEnabled() {
    return ENABLED;
  }

  @Override
  public void updateInputs(PivotInput input) {
    this.currentInput = input;
  }

  @Override
  public PivotOutput getOutputs() {
    PivotOutput pivotOutput = new PivotOutput();
    pivotOutput.setMoving(pivotLeft.getEncoder().getVelocity() != 0);
    pivotOutput.setLeftBrakeModeEnabled(IdleMode.kBrake == pivotLeft.getIdleMode());
    pivotOutput.setRightBrakeModeEnabled(IdleMode.kBrake == pivotRight.getIdleMode());
    pivotOutput.setCurrentPosition(pivotLeft.getEncoder().getPosition());
    return pivotOutput;
  }
  @Override
  public Command getTestCommand() {
    return Commands.none();
  }
}
