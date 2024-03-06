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

public class ShootAmpCommand extends EntechCommand {

  private ShooterInput sInput = new ShooterInput();
  private PivotInput pInput = new PivotInput();
  private TransferInput tInput = new TransferInput();

  private final ShooterSubsystem sSubsystem;
  private final PivotSubsystem pSubsystem;
  private final TransferSubsystem tSubsystem;

  private StoppingCounter counter =
      new StoppingCounter(getClass().getSimpleName(), RobotConstants.SHOOTER.RESET_DELAY);

  private boolean noNote;

  public ShootAmpCommand(ShooterSubsystem shooterSubsystem, PivotSubsystem pivotSubsystem,
      TransferSubsystem transferSubsystem) {
    super(shooterSubsystem, pivotSubsystem, transferSubsystem);
    this.sSubsystem = shooterSubsystem;
    this.pSubsystem = pivotSubsystem;
    this.tSubsystem = transferSubsystem;
  }

  @Override
  public void initialize() {
    if (RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()) {
      noNote = false;
      sInput.setActivate(true);
      sInput.setSpeed(RobotConstants.PID.SHOOTER.MAX_SPEED);
      sSubsystem.updateInputs(sInput);

      pInput.setActivate(true);
      pInput.setRequestedPosition(RobotConstants.PIVOT.SHOOT_AMP_POSITION_DEG);
      pSubsystem.updateInputs(pInput);
    } else {
      noNote = true;
    }
  }

  @Override
  public void execute() {
    if (RobotIO.getInstance().getPivotOutput().isAtRequestedPosition()
        && RobotIO.getInstance().getShooterOutput().isAtSpeed()
        && RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()) {
      tInput.setActivate(true);
      tInput.setSpeedPreset(TransferPreset.Shooting);
      tSubsystem.updateInputs(tInput);
    } else if (!RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()) {
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
    return counter.isFinished(noNote);
  }
}
