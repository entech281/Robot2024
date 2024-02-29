package frc.robot.commands;

import entech.commands.EntechCommand;
import entech.util.PeriodicLoopsPerSecond;
import entech.util.StoppingCounter;
import frc.robot.subsystems.intake.IntakeInput;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.transfer.TransferInput;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem.TransferPreset;

public class EjectNoteCommand extends EntechCommand {

  private IntakeSubsystem intSubsystem;
  private TransferSubsystem transSubsystem;

  private IntakeInput iInput = new IntakeInput();
  private TransferInput tInput = new TransferInput();

  private StoppingCounter counter = new StoppingCounter(getClass().getSimpleName(), EJECTING_TIME);

  public static final int EJECTING_TIME = 2;
  public boolean ejecting = false;

  public EjectNoteCommand(IntakeSubsystem iSubsystem, TransferSubsystem tSubsystem) {
    super(iSubsystem, tSubsystem);
    this.intSubsystem = iSubsystem;
    this.transSubsystem = tSubsystem;
  }

  @Override
  public void initialize() {
    ejecting = true;
    iInput.setActivate(false);
    iInput.setSpeed(-1);
    iInput.setBrakeModeEnabled(false);
    tInput.setActivate(false);
    tInput.setSpeedPreset(TransferPreset.Ejecting);
    tInput.setBrakeModeEnabled(false);
    intSubsystem.updateInputs(iInput);
    transSubsystem.updateInputs(tInput);
  }

  public void end(boolean interupted) {
    iInput.setActivate(false);
    iInput.setSpeed(0);
    iInput.setBrakeModeEnabled(false);
    tInput.setActivate(false);
    tInput.setSpeedPreset(TransferPreset.Off);
    tInput.setBrakeModeEnabled(false);
    intSubsystem.updateInputs(iInput);
    transSubsystem.updateInputs(tInput);
  }

  @Override
  public boolean isFinished() {
    return counter.isFinished(ejecting);
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }

}
