package frc.robot.commands;

import frc.entech.commands.EntechCommand;
import frc.robot.subsystems.drive.DriveSubsystem;

public class XDriveCommand extends EntechCommand {
  private final DriveSubsystem drive;

  public XDriveCommand(DriveSubsystem drive) {
    super(drive);
    this.drive = drive;
  }

  @Override
  public void initialize() {
    drive.setX();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
