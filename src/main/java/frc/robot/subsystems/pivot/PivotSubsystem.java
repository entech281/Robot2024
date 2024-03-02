package frc.robot.subsystems.pivot;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
// import com.revrobotics.SparkPIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import entech.subsystems.EntechSubsystem;
import entech.util.EntechUtils;
import frc.robot.RobotConstants;
import frc.robot.commands.test.TestPivotCommand;

public class PivotSubsystem extends EntechSubsystem<PivotInput, PivotOutput> {

  private static final boolean ENABLED = true;
  private static final boolean IS_INVERTED = false;

  private PivotInput currentInput = new PivotInput();

  private CANSparkMax pivotLeft;
  private CANSparkMax pivotRight;

  public static double calculateMotorPositionFromDegrees(double degrees) {
    return degrees / RobotConstants.PIVOT.PIVOT_CONVERSION_FACTOR;
  }

  @Override
  public void initialize() {
    if (ENABLED) {
      // IMPORTANT! DO NOT BURN FLASH OR SET SETTINGS FOR THIS SUBSYSTEM in code!
      // we want to avoid accidently disabling the controller soft limits
      pivotLeft = new CANSparkMax(RobotConstants.PORTS.CAN.PIVOT_A, MotorType.kBrushless);
      pivotRight = new CANSparkMax(RobotConstants.PORTS.CAN.PIVOT_B, MotorType.kBrushless);
      pivotRight.follow(pivotLeft);

      updateBrakeMode();

      pivotLeft.setInverted(IS_INVERTED);
      pivotRight.setInverted(IS_INVERTED);
    }
  }

  // private void setUpPIDConstants(SparkPIDController pIDController) {
  // pIDController.setP(RobotConstants.PID.PIVOT.KP);
  // pIDController.setD(RobotConstants.PID.PIVOT.KI);
  // pIDController.setI(RobotConstants.PID.PIVOT.KD);
  // }

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
      DriverStation.reportWarning("Pivot tried to go to " + currentInput.getRequestedPosition()
          + " value was changed to " + RobotConstants.PIVOT.LOWER_SOFT_LIMIT_DEG, null);
      return RobotConstants.PIVOT.LOWER_SOFT_LIMIT_DEG;
    } else if (position > RobotConstants.PIVOT.UPPER_SOFT_LIMIT_DEG) {
      DriverStation.reportWarning("Pivot tried to go to " + currentInput.getRequestedPosition()
          + " value was changed to " + RobotConstants.PIVOT.UPPER_SOFT_LIMIT_DEG, null);
      return RobotConstants.PIVOT.UPPER_SOFT_LIMIT_DEG;
    } else {
      return position;
    }
  }

  @Override
  public void periodic() {
    double clampedPosition = clampRequestedPosition(currentInput.getRequestedPosition());
    if (ENABLED) {
      if (currentInput.getActivate()) {
        pivotLeft.getPIDController().setReference(
            calculateMotorPositionFromDegrees(clampedPosition), ControlType.kSmartMotion);
        updateBrakeMode();
      }
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
  public PivotOutput toOutputs() {
    PivotOutput pivotOutput = new PivotOutput();
    pivotOutput.setMoving(pivotLeft.getEncoder().getVelocity() != 0);
    pivotOutput.setLeftBrakeModeEnabled(IdleMode.kBrake == pivotLeft.getIdleMode());
    pivotOutput.setRightBrakeModeEnabled(IdleMode.kBrake == pivotRight.getIdleMode());
    pivotOutput.setCurrentPosition(pivotLeft.getEncoder().getPosition());
    pivotOutput.setAtRequestedPosition(EntechUtils.isWithinTolerance(1,
        pivotLeft.getEncoder().getPosition(), currentInput.getRequestedPosition()));
    pivotOutput.setAtLowerLimit(
        pivotLeft.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen).isPressed());
    return pivotOutput;
  }

  @Override
  public Command getTestCommand() {
    return new TestPivotCommand(this);
  }
}
