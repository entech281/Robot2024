package frc.robot.commands;

import entech.commands.EntechCommand;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class StopShooterCommand extends EntechCommand {

  private ShooterInput input = new ShooterInput();

  private ShooterSubsystem subsystem;

  public static final double SHOOTER_SPEED = 4000;

  public StopShooterCommand(ShooterSubsystem shooterSubsystem) {
    super(shooterSubsystem);
    this.subsystem = shooterSubsystem;
  }

  @Override
  public void initialize() {
    input.setActivate(false);
    input.setBrakeModeEnabled(false);
    input.setSpeed(0);
    subsystem.updateInputs(input);
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
