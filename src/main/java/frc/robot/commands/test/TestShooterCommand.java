package frc.robot.commands.test;

import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class TestShooterCommand extends EntechCommand {

  private ShooterInput input = new ShooterInput();
  private final ShooterSubsystem sSubsystem;
  private StoppingCounter counter = new StoppingCounter(getClass().getSimpleName(),
      RobotConstants.TEST_CONSTANTS.STANDARD_TEST_LENGTH);

  public TestShooterCommand(ShooterSubsystem subsystem) {
    super(subsystem);
    this.sSubsystem = subsystem;
  }

  @Override
  public void initialize() {
    counter.reset();
    input.setActivate(true);
    input.setSpeed(RobotConstants.TEST_CONSTANTS.SHOOTER.TESTING_SPEED);
    sSubsystem.updateInputs(input);
  }

  @Override
  public void end(boolean interupted) {
    input.setActivate(false);
    input.setSpeed(0);
    sSubsystem.updateInputs(input);
  }

  @Override
  public boolean isFinished() {
    return counter.isFinished(sSubsystem.getOutputs().isAtSpeed());
  }

}
