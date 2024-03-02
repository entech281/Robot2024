package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.intake.IntakeInput;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.transfer.TransferInput;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem.TransferPreset;

public class IntakeNoteCommand extends EntechCommand {

  private IntakeSubsystem intSubsystem;
  private TransferSubsystem transSubsystem;

  private IntakeInput iInput = new IntakeInput();
  private TransferInput tInput = new TransferInput();

  private StoppingCounter counter = new StoppingCounter(getClass().getSimpleName(), 0.35);

  private boolean retracting;
  private boolean retracted;
  private boolean hasNote;

  public IntakeNoteCommand(IntakeSubsystem iSubsystem, TransferSubsystem tSubsystem) {
    super(iSubsystem, tSubsystem);
    this.intSubsystem = iSubsystem;
    this.transSubsystem = tSubsystem;
  }

  @Override
  public void initialize() {
    DriverStation.reportWarning("Command was called", false);
    retracted = false;
    retracting = false;
    hasNote = false;
    iInput.setActivate(true);
    iInput.setSpeed(RobotConstants.INTAKE.INTAKE_SPEED);
    iInput.setBrakeModeEnabled(false);
    tInput.setActivate(true);
    tInput.setSpeedPreset(TransferPreset.Intaking1);
    tInput.setBrakeModeEnabled(true);
    intSubsystem.updateInputs(iInput);
    transSubsystem.updateInputs(tInput);
  }

  @Override
  public void execute() {
    if (retracting) {
      tInput.setSpeedPreset(TransferPreset.Retracting);
      transSubsystem.updateInputs(tInput);
      if (RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()) {
        retracted = true;
      }
    } else {
      if (RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()) {
        hasNote = true;
        iInput.setActivate(false);
        intSubsystem.updateInputs(iInput);
      } else {
        if (RobotIO.getInstance().getInternalNoteDetectorOutput().forwardSensorHasNote()) {
          iInput.setActivate(false);
          intSubsystem.updateInputs(iInput);
          tInput.setSpeedPreset(TransferPreset.Intaking2);
          transSubsystem.updateInputs(tInput);
        }
      }
    }
  }

  @Override
  public void end(boolean interupted) {
    iInput.setActivate(false);
    tInput.setActivate(false);
    intSubsystem.updateInputs(iInput);
    transSubsystem.updateInputs(tInput);
  }

  @Override
  public boolean isFinished() {
    if (retracted) {
      return true;
    } else {
      if (counter.isFinished(hasNote) && hasNote) {
        if (RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()) {
          return true;
        } else {
          retracting = true;
          return false;
        }
      }
      return false;
    }
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }

}
