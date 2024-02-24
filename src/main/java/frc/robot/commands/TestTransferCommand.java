package frc.robot.commands;

import entech.commands.EntechCommand;
import entech.util.PeriodicLoopsPerSecond;
import entech.util.StoppingCounter;
import frc.robot.subsystems.transfer.TransferInput;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem.TransferStatus;

public class TestTransferCommand extends EntechCommand {

  private TransferInput input = new TransferInput();
  private TransferSubsystem tSubsystem = new TransferSubsystem();
  private StoppingCounter counter =
      new StoppingCounter(getClass().getSimpleName(), PeriodicLoopsPerSecond.getLoopsPerSecond(1));

  public TestTransferCommand(TransferSubsystem subsystem) {
    super(subsystem);
    this.tSubsystem = subsystem;
  }

  @Override
  public void initialize() {
    counter.reset();
    input.setActivate(true);
    input.setBrakeModeEnabled(false);
    input.setCurrentMode(TransferStatus.Testing);
    tSubsystem.updateInputs(input);
  }

  @Override
  public void end(boolean interupted) {
    input.setActivate(false);
    input.setBrakeModeEnabled(false);
    input.setCurrentMode(TransferStatus.Off);
    tSubsystem.updateInputs(input);
  }

  @Override
  public boolean isFinished() {
    return counter.isFinished(tSubsystem.getOutputs().isActive());
  }
}
