package frc.robot.commands;

import entech.commands.EntechCommand;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class StartShooterCommand extends EntechCommand {

  private ShooterInput input = new ShooterInput();

  private ShooterSubsystem subsystem;

  public static final double SHOOTER_SPEED = 4000;

  public StartShooterCommand(ShooterSubsystem shooterSubsystem) {
    super(shooterSubsystem);
    this.subsystem = shooterSubsystem;
  }

  @Override
  public void initialize() {
    input.setActivate(true);
    input.setBrakeModeEnabled(false);
    input.setSpeed(SHOOTER_SPEED);
    subsystem.updateInputs(input);
  }

  @Override
  public boolean isFinished() {
    return RobotIO.getInstance().getShooterOutput().isAtSpeed();
  }
}
