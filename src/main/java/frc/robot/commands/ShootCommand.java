package frc.robot.commands;

import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.transfer.TransferInput;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem.TransferPreset;

public class ShootCommand extends EntechCommand {

  private ShooterInput shooterInput = new ShooterInput();
  private TransferInput transferInput = new TransferInput();
  private ShooterSubsystem shooterSubsystem;
  private TransferSubsystem transferSubsystem;

  private RobotIO getInstance = RobotIO.getInstance();

  private StoppingCounter shotDelay =
      new StoppingCounter(getClass().getSimpleName(), RobotConstants.SHOOTER.SHOOT_DELAY);

  private boolean shooting = false;
  private boolean cancel = false;

  public ShootCommand(TransferSubsystem transferSubsystem, ShooterSubsystem shooterSubsystem) {
    super(transferSubsystem, shooterSubsystem);
    this.transferSubsystem = transferSubsystem;
    this.shooterSubsystem = shooterSubsystem;
  }

  @Override
  public void initialize() {
    shooting = false;
    cancel = false;
    shotDelay.reset();
    if (getInstance.getInternalNoteDetectorOutput().forwardSensorHasNote()
        || getInstance.getInternalNoteDetectorOutput().rearSensorHasNote()) {
      cancel = false;
    } else {
      cancel = true;
    }
  }

  @Override
  public void execute() {
    if (shooting) {

    } else {
      if (getInstance.getInternalNoteDetectorOutput().forwardSensorHasNote()
          || getInstance.getInternalNoteDetectorOutput().rearSensorHasNote()) {
        transferInput.setActivate(true);
        transferInput.setBrakeModeEnabled(false);
        transferInput.setSpeedPreset(TransferPreset.Shooting);
        transferSubsystem.updateInputs(transferInput);
        shooting = true;
      } else {
        cancel = true;
        shooting = false;
      }
    }
  }

  @Override
  public void end(boolean interupted) {
    transferInput.setActivate(false);
    transferInput.setBrakeModeEnabled(false);
    transferInput.setSpeedPreset(TransferPreset.Off);
    transferSubsystem.updateInputs(transferInput);

    shooterInput.setActivate(false);
    shooterInput.setBrakeModeEnabled(false);
    shooterInput.setSpeed(0);
    shooterSubsystem.updateInputs(shooterInput);
  }

  @Override
  public boolean isFinished() {
    if (cancel) {
      return true;
    } else if (shotDelay.isFinished(shooting)) {
      return true;
    } else {
      return false;
    }
  }

}
