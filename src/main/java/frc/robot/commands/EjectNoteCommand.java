package frc.robot.commands;

import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
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

  private StoppingCounter counter =
      new StoppingCounter(getClass().getSimpleName(), RobotConstants.INTAKE.EJECTING_TIME);

  public EjectNoteCommand(IntakeSubsystem iSubsystem, TransferSubsystem tSubsystem) {
    super(iSubsystem, tSubsystem);
    this.intSubsystem = iSubsystem;
    this.transSubsystem = tSubsystem;
  }

  @Override
  public void initialize() {
    iInput.setActivate(false);
    iInput.setSpeed(-1);
    iInput.setBrakeModeEnabled(false);
    tInput.setActivate(false);
    tInput.setSpeedPreset(TransferPreset.Ejecting);
    tInput.setBrakeModeEnabled(false);
    intSubsystem.updateInputs(iInput);
    transSubsystem.updateInputs(tInput);
  }

  @Override
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
    return counter.isFinished(true);
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }

}
