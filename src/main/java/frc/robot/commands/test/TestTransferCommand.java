package frc.robot.commands.test;

import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.subsystems.transfer.TransferInput;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem.TransferPreset;

public class TestTransferCommand extends EntechCommand {

  private TransferInput input = new TransferInput();
  private TransferSubsystem tSubsystem = new TransferSubsystem();
  private StoppingCounter counter = new StoppingCounter(getClass().getSimpleName(),
      RobotConstants.TEST_CONSTANTS.STANDARD_TEST_LENGTH);

  public TestTransferCommand(TransferSubsystem subsystem) {
    super(subsystem);
    this.tSubsystem = subsystem;
  }

  @Override
  public void initialize() {
    counter.reset();
    input.setActivate(true);
    input.setBrakeModeEnabled(false);
    input.setSpeedPreset(TransferPreset.Testing);
    tSubsystem.updateInputs(input);
  }

  @Override
  public void end(boolean interupted) {
    input.setActivate(false);
    input.setBrakeModeEnabled(false);
    input.setSpeedPreset(TransferPreset.Off);
    tSubsystem.updateInputs(input);
  }

  @Override
  public boolean isFinished() {
    return counter.isFinished(tSubsystem.getOutputs().isActive());
  }
}
