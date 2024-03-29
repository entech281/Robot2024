package frc.robot.commands;

import entech.commands.EntechCommand;
import frc.robot.io.RobotIO;
import frc.robot.operation.UserPolicy;
import frc.robot.subsystems.transfer.TransferInput;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem.TransferPreset;

public class FeedShooterCommand extends EntechCommand {
  private final TransferSubsystem transfer;
  private final TransferInput input = new TransferInput();


  public FeedShooterCommand(TransferSubsystem transfer) {
    this.transfer = transfer;
  }


  @Override
  public void end(boolean interrupted) {
    input.setActivate(false);
    input.setSpeedPreset(TransferPreset.Off);
    transfer.updateInputs(input);
  }


  @Override
  public void execute() {
    if (UserPolicy.getInstance().isReadyToShoot()
        && RobotIO.getInstance().getInternalNoteDetectorOutput().hasNote()) {
      input.setActivate(true);
      input.setSpeedPreset(TransferPreset.Shooting);
      transfer.updateInputs(input);
    }
  }


  @Override
  public void initialize() {
    input.setActivate(false);
    input.setSpeedPreset(TransferPreset.Off);
    transfer.updateInputs(input);
  }


  @Override
  public boolean isFinished() {
    return !RobotIO.getInstance().getInternalNoteDetectorOutput().hasNote()
        || !UserPolicy.getInstance().isReadyToShoot();
  }

}
