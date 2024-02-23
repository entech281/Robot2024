package frc.robot.commands;

import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.subsystems.drive.DriveSubsystem;

public class TestDriveCommand extends EntechCommand {
  private final DriveSubsystem driveSubsystem;
  private boolean end = false;
  private final StoppingCounter counter = new StoppingCounter(this.getClass().getSimpleName(), 150);
  private static final double DRIVE_POWER = 0.25;
  private int stage = 0;

  public TestDriveCommand(DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
  }

  @Override
  public void execute() {
    // TODO Auto-generated method stub
    super.execute();
  }

  @Override
  public void initialize() {
    // TODO Auto-generated method stub
    super.initialize();
  }

  @Override
  public boolean isFinished() {
    // TODO Auto-generated method stub
    return super.isFinished();
  }
}
