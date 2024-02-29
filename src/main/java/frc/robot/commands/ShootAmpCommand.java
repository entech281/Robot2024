package frc.robot.commands;

import entech.commands.EntechCommand;
import frc.robot.RobotConstants;
import frc.robot.subsystems.has_note.HasNoteOutput;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.pivot.PivotOutput;
import frc.robot.subsystems.pivot.PivotSubsystem;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.shooter.ShooterOutput;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem.TransferPreset;
import frc.robot.subsystems.transfer.TransferOutput;
import frc.robot.subsystems.transfer.TransferInput;

public class ShootAmpCommand extends EntechCommand {

  private ShooterInput sInput = new ShooterInput();
  private PivotInput pInput = new PivotInput();
  private TransferInput tInput = new TransferInput();

  private ShooterOutput sOutput;
  private PivotOutput pOutput;
  private TransferOutput tOutput;

  private ShooterSubsystem sSubsystem;
  private PivotSubsystem pSubsystem;
  private TransferSubsystem tSubsystem;

  private HasNoteOutput hNOutput;

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
    if (hNOutput.hasNote()) {
      noNote = false;
      sInput.setActivate(true);
      sInput.setBrakeModeEnabled(false);
      sInput.setSpeed(RobotConstants.PID.SHOOTER.MAX_SPEED);
      sSubsystem.updateInputs(sInput);

      pInput.setActivate(true);
      pInput.setBrakeModeEnabled(true);
      pInput.setRequestedPosition(RobotConstants.PIVOT.SHOOT_AMP_POSITION_DEG);
      pSubsystem.updateInputs(pInput);
    } else {
      noNote = true;
    }
  }

  @Override
  public void execute() {
    if (pOutput.isAtRequestedPosition() && sOutput.isAtSpeed() && hNOutput.hasNote()) {
      tInput.setActivate(true);
      tInput.setBrakeModeEnabled(false);
      tInput.setSpeedPreset(TransferPreset.Shooting);
      tSubsystem.updateInputs(tInput);
    } else if (hNOutput.hasNote() == false) {
      noNote = true;
    }
  }

  @Override
  public void end(boolean interupted) {

    pInput.setRequestedPosition(0);
    pSubsystem.updateInputs(pInput);

    sInput.setBrakeModeEnabled(true);
    sInput.setActivate(false);
    sSubsystem.updateInputs(sInput);

    if (tOutput.isActive()) {
      tInput.setActivate(false);
      tInput.setSpeedPreset(TransferPreset.Off);
      tSubsystem.updateInputs(tInput);
    }
  }

  @Override
  public boolean isFinished() {
    if (noNote) {
      return true;
    } else {
      return false;
    }
  }
}
