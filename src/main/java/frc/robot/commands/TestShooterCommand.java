package frc.robot.commands;

import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class TestShooterCommand extends EntechCommand {

  private ShooterInput input = new ShooterInput();
  private ShooterSubsystem sSubsystem = new ShooterSubsystem();
  private StoppingCounter counter = new StoppingCounter(getClass().getSimpleName(), 75);

  public TestShooterCommand(ShooterSubsystem subsystem) {
    super(subsystem);
    this.sSubsystem = subsystem;
  }

  public void initialize() {
    counter.reset();
    input.setActivate(true);
    input.setBrakeModeEnabled(false);
    input.setSpeed(1000);
    sSubsystem.updateInputs(input);
  }

  public void execute() {
    isFinished(counter.isFinished(sSubsystem.getOutputs().isActive()));
  }

  public boolean isFinished(boolean isFinished) {
    return isFinished;
  }

}
