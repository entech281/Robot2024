package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import entech.util.EntechJoystick;

public class YesNoConfirmationCommand extends Command {

  private Command commandToRun;
  private Trigger okButton;
  private Trigger failButton;

  private boolean success = false;
  protected String name;

  public YesNoConfirmationCommand(String name, Command commandToRun, Trigger okButton, Trigger failButton){
    this.commandToRun = commandToRun;
    this.okButton=okButton;
    this.failButton=failButton;
    this.name=name;
  }

  @Override
  public void initialize() {
    commandToRun.initialize();
  }

  @Override
  public void execute() {

    commandToRun.execute();
  }

  public void operatorMessage(boolean success){
    DriverStation.reportWarning(this.name + ": result =>" + success,false);
  }

  @Override
  public boolean isFinished() {

    if ( okButton.getAsBoolean() ){
       success = true;
       return true;
    }
    if (failButton.getAsBoolean()){
       success = false;
       return false;
    }
    return commandToRun.isFinished();
  }
}
