package frc.robot.commands;

import entech.commands.EntechCommand;
import frc.robot.subsystems.has_note.HasNoteSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.intake.IntakeInput;
import frc.robot.subsystems.transfer.TransferInput;

public class IntakeCommand extends EntechCommand {

  private IntakeSubsystem intSubsystem;
  private TransferSubsystem transSubsystem;

  private IntakeInput iInput = new IntakeInput();
  private TransferInput tInput = new TransferInput();

  private HasNoteSubsystem hNOutput = new HasNoteSubsystem();

  public IntakeCommand(IntakeSubsystem iSubsystem, TransferSubsystem tSubsystem) {
    super(iSubsystem, tSubsystem);
    this.intSubsystem = iSubsystem;
    this.transSubsystem = tSubsystem;
  }

  @Override
  public void initialize() {
    iInput.setActivate(true);
    tInput.setActivate(true);
    intSubsystem.updateInputs(iInput);
    transSubsystem.updateInputs(tInput);
  }

  public void end(boolean interupted) {
    iInput.setActivate(false);
    tInput.setActivate(false);
    intSubsystem.updateInputs(iInput);
    transSubsystem.updateInputs(tInput);
  }

  @Override
  public boolean isFinished() {
    return hNOutput.getOutputs().hasNote();
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }

}
