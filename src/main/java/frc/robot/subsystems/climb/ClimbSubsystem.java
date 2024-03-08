package frc.robot.subsystems.climb;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.wpilibj2.command.Command;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import frc.robot.commands.test.TestClimbCommand;

public class ClimbSubsystem extends EntechSubsystem<ClimbInput, ClimbOutput> {
  private boolean ENABLED = true;

  private ClimbInput currentInput = new ClimbInput();

  private CANSparkMax climbMotorLeft;
  private CANSparkMax climbMotorRight;

  @Override
  public void initialize() {
    if (ENABLED) {
      climbMotorLeft = new CANSparkMax(RobotConstants.PORTS.CAN.CLIMB_A, MotorType.kBrushless);
      climbMotorRight = new CANSparkMax(RobotConstants.PORTS.CAN.CLIMB_B, MotorType.kBrushless);

      climbMotorLeft.setInverted(false);
      climbMotorRight.setInverted(true);

      climbMotorLeft.getEncoder()
          .setPositionConversionFactor(RobotConstants.CLIMB.CLIMB_CONVERSION_FACTOR);
      climbMotorLeft.getEncoder()
          .setPositionConversionFactor(RobotConstants.CLIMB.CLIMB_CONVERSION_FACTOR);

      setUpPIDConstants(climbMotorLeft.getPIDController());
      setUpPIDConstants(climbMotorRight.getPIDController());

      climbMotorLeft.setIdleMode(IdleMode.kCoast);
      climbMotorRight.setIdleMode(IdleMode.kCoast);

      climbMotorLeft.getEncoder().setPosition(0.0);
      climbMotorRight.getEncoder().setPosition(0.0);
    }
  }

  private void setUpPIDConstants(SparkPIDController pIDController) {
    pIDController.setP(RobotConstants.PID.CLIMB.KP);
    pIDController.setD(RobotConstants.PID.CLIMB.KI);
    pIDController.setI(RobotConstants.PID.CLIMB.KD);
  }

  public void periodic() {
    if (ENABLED) {
      if (currentInput.getActivate()) {
        if (currentInput.getFeeze()) {
          climbMotorLeft.set(0.0);
          climbMotorRight.set(0.0);

        } else {
          climbMotorLeft.set(currentInput.getSpeed());
          climbMotorRight.set(currentInput.getSpeed());
        }
      } else {
        climbMotorLeft.set(0.0);
        climbMotorRight.set(0.0);
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
  public ClimbOutput toOutputs() {
    ClimbOutput climbOutput = new ClimbOutput();
    climbOutput.setActive(climbMotorLeft.getEncoder().getVelocity() != 0);
    climbOutput.setBrakeModeEnabled(IdleMode.kBrake == climbMotorLeft.getIdleMode());
    climbOutput.setCurrentPosition(climbMotorLeft.getEncoder().getPosition());
    climbOutput.setExtended(climbMotorLeft.getEncoder().getPosition() > 0);
    return climbOutput;
  }

  @Override
  public Command getTestCommand() {
    return new TestClimbCommand(this);
  }

}
