package frc.robot.commands;

import entech.commands.EntechCommand;
import entech.util.SpeakerPivotSolution;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.pivot.PivotSubsystem;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem.TransferPreset;
import frc.robot.subsystems.transfer.TransferInput;

public class ShootSpeakerCommand extends EntechCommand {

  private ShooterInput sInput = new ShooterInput();
  private PivotInput pInput = new PivotInput();
  private TransferInput tInput = new TransferInput();

  private StoppingCounter counter = new StoppingCounter(getClass().getSimpleName(), RESET_DELAY);

  private ShooterSubsystem sSubsystem;
  private PivotSubsystem pSubsystem;
  private TransferSubsystem tSubsystem;

  public static final double RESET_DELAY = 0.5;

  private boolean noNote;

  public ShootSpeakerCommand(ShooterSubsystem shooterSubsystem, PivotSubsystem pivotSubsystem,
      TransferSubsystem transferSubsystem) {
    super(shooterSubsystem, pivotSubsystem, transferSubsystem);
    this.sSubsystem = shooterSubsystem;
    this.pSubsystem = pivotSubsystem;
    this.tSubsystem = transferSubsystem;
  }

  @Override
  public void initialize() {
    if (RobotIO.getInstance().getHasNoteOutput().hasNote()) {
      noNote = false;
      sInput.setActivate(true);
      sInput.setBrakeModeEnabled(false);
      sInput.setSpeed(RobotConstants.PID.SHOOTER.MAX_SPEED);
      sSubsystem.updateInputs(sInput);

      pInput.setActivate(true);
      pInput.setBrakeModeEnabled(true);
      pInput.setRequestedPosition(SpeakerPivotSolution.getShooterSolutionDeg());
      pSubsystem.updateInputs(pInput);
    } else {
      noNote = true;
    }
  }

  @Override
  public void execute() {
    if (RobotIO.getInstance().getPivotOutput().isAtRequestedPosition()
        && RobotIO.getInstance().getShooterOutput().isAtSpeed()
        && RobotIO.getInstance().getHasNoteOutput().hasNote()) {
      tInput.setActivate(true);
      tInput.setBrakeModeEnabled(false);
      tInput.setSpeedPreset(TransferPreset.Shooting);
      tSubsystem.updateInputs(tInput);
    } else if (RobotIO.getInstance().getHasNoteOutput().hasNote() == false) {
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

    if (RobotIO.getInstance().getTransferOutput().isActive()) {
      tInput.setActivate(false);
      tInput.setSpeedPreset(TransferPreset.Off);
      tSubsystem.updateInputs(tInput);
    }
  }

  @Override
  public boolean isFinished() {
    if (counter.isFinished(noNote)) {
      return true;
    } else {
      return false;
    }
  }
}
