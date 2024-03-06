package frc.robot.commands;

import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.pivot.PivotSubsystem;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.transfer.TransferInput;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem.TransferPreset;

public class ShootAngleCommand extends EntechCommand {

  private ShooterInput sInput = new ShooterInput();
  private PivotInput pInput = new PivotInput();
  private TransferInput tInput = new TransferInput();

  private StoppingCounter cancelCounter =
      new StoppingCounter(getClass().getSimpleName(), RobotConstants.SHOOTER.RESET_DELAY);
  private StoppingCounter shootingCounter =
      new StoppingCounter(getClass().getSimpleName(), RobotConstants.SHOOTER.SHOOT_DELAY);

  private final ShooterSubsystem sSubsystem;
  private final PivotSubsystem pSubsystem;
  private final TransferSubsystem tSubsystem;
  private final double angle;

  private boolean noNote;

  public ShootAngleCommand(ShooterSubsystem shooterSubsystem, PivotSubsystem pivotSubsystem,
      TransferSubsystem transferSubsystem, double angle) {
    super(shooterSubsystem, pivotSubsystem, transferSubsystem);
    this.sSubsystem = shooterSubsystem;
    this.pSubsystem = pivotSubsystem;
    this.tSubsystem = transferSubsystem;
    this.angle = angle;
  }

  @Override
  public void initialize() {
    cancelCounter.reset();
    shootingCounter.reset();
    if (RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()
        || RobotIO.getInstance().getInternalNoteDetectorOutput().forwardSensorHasNote()) {
      noNote = false;
      sInput.setActivate(true);
      sInput.setSpeed(5000);
      sSubsystem.updateInputs(sInput);

      pInput.setActivate(true);
      pInput.setRequestedPosition(angle);
      pSubsystem.updateInputs(pInput);
    } else {
      noNote = true;
    }
  }

  @Override
  public void execute() {
    if (shootingCounter.isFinished(RobotIO.getInstance().getShooterOutput().isAtSpeed()
        && (RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()
            || RobotIO.getInstance().getInternalNoteDetectorOutput().forwardSensorHasNote()))) {
      tInput.setActivate(true);
      tInput.setSpeedPreset(TransferPreset.Shooting);
      tSubsystem.updateInputs(tInput);
    } else if (!(RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()
        || RobotIO.getInstance().getInternalNoteDetectorOutput().forwardSensorHasNote())) {
      noNote = true;
    }
  }

  @Override
  public void end(boolean interupted) {

    pInput.setRequestedPosition(0);
    pSubsystem.updateInputs(pInput);

    sInput.setActivate(false);
    sSubsystem.updateInputs(sInput);

    if (RobotIO.getInstance().getTransferOutput().isActive()) {
      tInput.setActivate(false);
      tInput.setSpeedPreset(TransferPreset.Off);
      tSubsystem.updateInputs(tInput);
    }
  }

  @Override
  public boolean isFinished() {
    return cancelCounter.isFinished(noNote);
  }
}
