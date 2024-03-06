package frc.robot.commands;

import java.util.Optional;
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

public class ShootSpeakerCommand extends EntechCommand {

  private ShooterInput sInput = new ShooterInput();
  private PivotInput pInput = new PivotInput();
  private TransferInput tInput = new TransferInput();

  private StoppingCounter cancelCounter =
      new StoppingCounter(getClass().getSimpleName(), RobotConstants.SHOOTER.RESET_DELAY);
  private StoppingCounter shootingCounter =
      new StoppingCounter(getClass().getSimpleName(), RobotConstants.SHOOTER.SHOOT_DELAY);

  private ShooterSubsystem sSubsystem;
  private PivotSubsystem pSubsystem;
  private TransferSubsystem tSubsystem;

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
    cancelCounter.reset();
    shootingCounter.reset();
    if (RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()
        || RobotIO.getInstance().getInternalNoteDetectorOutput().forwardSensorHasNote()) {
      noNote = false;
      sInput.setActivate(true);
      sInput.setBrakeModeEnabled(false);
      sInput.setSpeed(5000);
      sSubsystem.updateInputs(sInput);

      pInput.setActivate(true);
      pInput.setBrakeModeEnabled(true);
      // pInput.setRequestedPosition(calculatePivotAngle());
      pInput.setRequestedPosition(RobotConstants.PIVOT.SPEAKER_PODIUM_SCORING);
      pSubsystem.updateInputs(pInput);
    } else {
      noNote = true;
    }
  }

  private double calculatePivotAngle() {
    Optional<Double> distance = RobotIO.getInstance().getDistanceFromTarget();
    if (distance.isPresent()) {
      double a = Math.pow(distance.get(), 4) * RobotConstants.PIVOT.kA;
      double b = Math.pow(distance.get(), 3) * RobotConstants.PIVOT.kB;
      double c = Math.pow(distance.get(), 2) * RobotConstants.PIVOT.kC;
      double d = distance.get() * RobotConstants.PIVOT.kD;

      return a + b + c + d + RobotConstants.PIVOT.kE;
    }
    return RobotConstants.PIVOT.SPEAKER_BUMPER_SCORING;
  }

  @Override
  public void execute() {
    if (shootingCounter.isFinished(RobotIO.getInstance().getShooterOutput().isAtSpeed()
        && (RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()
            || RobotIO.getInstance().getInternalNoteDetectorOutput().forwardSensorHasNote()))) {
      tInput.setActivate(true);
      tInput.setBrakeModeEnabled(false);
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

    sInput.setBrakeModeEnabled(false);
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
