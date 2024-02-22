package frc.robot.subsystems.climb;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.wpilibj.DriverStation;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;

public class ClimbSubsystem extends EntechSubsystem<ClimbInput, ClimbOutput> {

  private boolean ENABLED = false;

  private ClimbInput currentInput = new ClimbInput();

  private CANSparkMax climbMotorLeft;
  private CANSparkMax climbMotorRight;

  @Override
  public void initialize() {
    if (ENABLED) {
      climbMotorLeft = new CANSparkMax(RobotConstants.Ports.CAN.CLIMB_A, MotorType.kBrushless);
      climbMotorRight = new CANSparkMax(RobotConstants.Ports.CAN.CLIMB_B, MotorType.kBrushless);

      climbMotorLeft.setInverted(false);
      climbMotorRight.setInverted(false);

      climbMotorLeft.getEncoder()
          .setPositionConversionFactor(RobotConstants.CLIMB.CLIMB_CONVERSION_FACTOR);
      climbMotorLeft.getEncoder()
          .setPositionConversionFactor(RobotConstants.CLIMB.CLIMB_CONVERSION_FACTOR);

      setUpPIDConstants(climbMotorLeft.getPIDController());
      setUpPIDConstants(climbMotorRight.getPIDController());

      updateBrakeMode();
    }
  }

  private void setUpPIDConstants(SparkPIDController pIDController) {
    pIDController.setP(RobotConstants.PID.CLIMB.KP);
    pIDController.setD(RobotConstants.PID.CLIMB.KI);
    pIDController.setI(RobotConstants.PID.CLIMB.KD);
  }

  private void updateBrakeMode() {
    if (currentInput.getBrakeModeEnabled()) {
      climbMotorLeft.setIdleMode(IdleMode.kBrake);
      climbMotorRight.setIdleMode(IdleMode.kBrake);
    } else {
      climbMotorLeft.setIdleMode(IdleMode.kCoast);
      climbMotorRight.setIdleMode(IdleMode.kCoast);
    }
  }

  private double clampRequestedPosition(double position) {
    if (position < 0) {
      DriverStation.reportWarning(
          "Climb tried to go to " + currentInput.getRequestedPosition() + " value was changed to 0",
          null);
      return 0;
    } else if (position > RobotConstants.PIVOT.UPPER_SOFT_LIMIT_DEG) {
      DriverStation.reportWarning("Climb tried to go to " + currentInput.getRequestedPosition()
          + " value was changed to " + RobotConstants.PIVOT.PIVOT_CONVERSION_FACTOR, null);
      return RobotConstants.PIVOT.UPPER_SOFT_LIMIT_DEG;
    } else {
      return position;
    }
  }

  private void setPosition(double position) {
    climbMotorLeft.getPIDController().setReference(position, CANSparkMax.ControlType.kPosition);
    climbMotorRight.getPIDController().setReference(position, CANSparkMax.ControlType.kPosition);
  }

  public void periodic() {
    if (ENABLED) {

      double clampedPosition = clampRequestedPosition(currentInput.getRequestedPosition());

      if (ENABLED) {
        setPosition(clampedPosition);

        updateBrakeMode();
      }
    }
  }

  @Override
  public boolean isEnabled() {
    return ENABLED;
  }

  @Override
  public void updateInputs(ClimbInput input) {
    this.currentInput = input;
  }

  @Override
  public ClimbOutput getOutputs() {
    ClimbOutput climbOutput = new ClimbOutput();
    climbOutput.setActive(climbMotorLeft.getEncoder().getVelocity() != 0);
    climbOutput.setBrakeModeEnabled(IdleMode.kBrake == climbMotorLeft.getIdleMode());
    climbOutput.setCurrentPosition(climbMotorLeft.getEncoder().getPosition());
    climbOutput.setExtended(climbMotorLeft.getEncoder().getPosition() > 0);
    return climbOutput;
  }

}
