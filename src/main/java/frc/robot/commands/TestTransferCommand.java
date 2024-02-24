package frc.robot.commands;

import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.subsystems.transfer.TransferInput;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem.TransferStatus;

public class TestTransferCommand extends EntechCommand {

  private TransferInput input = new TransferInput();
  private TransferSubsystem tSubsystem = new TransferSubsystem();
  private private StoppingCounter counter = new StoppingCounter(getClass().getSimpleName(), 75);

  private int stage = 0;

  public TestTransferCommand(TransferSubsystem subsystem) {
    super(subsystem);
    this.tSubsystem = subsystem;
  }

  public void initialize() {
    counter.reset();
    input.setActivate(true);
    input.setBrakeModeEnabled(false);
    input.setCurrentMode(TransferStatus.Testing);
    tSubsystem.updateInputs(input);
  }

  public void execute() {
    switch (stage) {
      case 1:
        if()
        break;
      case 2:
        break;
      default:
        break;
    }
    counter.isFinished(tSubsystem.getOutputs().isActive());
  }

  public void end() {
    input.setActivate(false);
    input.setBrakeModeEnabled(false);
    input.setCurrentMode(TransferStatus.Off);
    tSubsystem.updateInputs(input);
  }

  public boolean isFinished(boolean isFinished) {
    return stage >= 3;
  }
}
