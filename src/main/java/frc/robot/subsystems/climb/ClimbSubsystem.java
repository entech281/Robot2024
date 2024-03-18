package frc.robot.subsystems.climb;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.wpilibj2.command.Command;
import entech.subsystems.EntechSubsystem;
import frc.robot.RobotConstants;
import frc.robot.commands.test.TestClimbCommand;
import frc.robot.io.RobotIO;

public class ClimbSubsystem extends EntechSubsystem<ClimbInput, ClimbOutput> {
  private boolean ENABLED = false;

  private ClimbInput currentInput = new ClimbInput();

  private CANSparkMax climbMotorRight;
  private CANSparkMax climbMotorLeft;

  private IdleMode mode;

  @Override
  public void initialize() {
    if (ENABLED) {
      climbMotorRight = new CANSparkMax(RobotConstants.PORTS.CAN.CLIMB_A, MotorType.kBrushless);
      climbMotorLeft = new CANSparkMax(RobotConstants.PORTS.CAN.CLIMB_B, MotorType.kBrushless);

      climbMotorRight.setInverted(false);
      climbMotorLeft.setInverted(true);

      climbMotorRight.getEncoder()
          .setPositionConversionFactor(RobotConstants.CLIMB.CLIMB_CONVERSION_FACTOR);
      climbMotorRight.getEncoder()
          .setPositionConversionFactor(RobotConstants.CLIMB.CLIMB_CONVERSION_FACTOR);

      setUpPIDConstants(climbMotorRight.getPIDController());
      setUpPIDConstants(climbMotorLeft.getPIDController());

      climbMotorRight.setIdleMode(IdleMode.kCoast);
      climbMotorLeft.setIdleMode(IdleMode.kCoast);
      mode = IdleMode.kCoast;

      climbMotorRight.getEncoder().setPosition(0.0);
      climbMotorLeft.getEncoder().setPosition(0.0);
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
          climbMotorRight.set(0.0);
          climbMotorLeft.set(0.0);

        } else {
          climbMotorRight.set(currentInput.getSpeedRight());
          climbMotorLeft.set(currentInput.getSpeedLeft());
        }
      } else {
        climbMotorRight.set(0.0);
        climbMotorLeft.set(0.0);
      }
    }
  }

  @Override
  public boolean isEnabled() {
    return ENABLED;
  }

  @Override
  public void updateInputs(ClimbInput input) {
    RobotIO.processInput(input);
    this.currentInput = input;
  }

  @Override
  public ClimbOutput toOutputs() {
    ClimbOutput climbOutput = new ClimbOutput();
    climbOutput.setActive(climbMotorRight.getEncoder().getVelocity() != 0);
    climbOutput.setBrakeModeEnabled(IdleMode.kBrake == mode);
    climbOutput.setCurrentPosition(climbMotorRight.getEncoder().getPosition());
    climbOutput.setExtended(climbMotorRight.getEncoder().getPosition() > 0);
    return climbOutput;
  }

  @Override
  public Command getTestCommand() {
    return new TestClimbCommand(this);
  }

  public void setPosition(double position) {
    climbMotorRight.getEncoder().setPosition(position);
    climbMotorLeft.getEncoder().setPosition(position);
  }
}
