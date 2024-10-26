package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.entech.commands.EntechCommand;

public class RunTestCommand extends EntechCommand {
  private final SendableChooser<Command> chooser;

  public RunTestCommand(SendableChooser<Command> chooser) {
    this.chooser = chooser;
  }

  @Override
  public void initialize() {
    chooser.getSelected().schedule();
  }

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
