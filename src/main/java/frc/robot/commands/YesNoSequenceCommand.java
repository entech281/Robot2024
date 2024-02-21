package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.util.List;


/*
   this command will execute commands one at a time.
   During the sequence, if the okButton is pressed, we will move along to the next
   command. if failButton is pressed, we will stop execution
 */
public class YesNoSequenceCommand extends Command {

  private Command[] commandsToRun;
  private int currentCommandIndex = 0;
  private boolean currentCommandInitialized = false;
  private Trigger okButton;
  private Trigger failButton;

  private boolean overallSuccess = true;
  public YesNoSequenceCommand(Trigger okButton, Trigger failButton,Command ...commandsToRun){
    this.commandsToRun = commandsToRun;
    this.okButton=okButton;
    this.failButton=failButton;

  }

  @Override
  public void initialize() {
    //do nothing
  }

  @Override
  public void execute() {
    Command currentCommand = commandsToRun[currentCommandIndex];
    if ( ! currentCommandInitialized ){
      currentCommand.initialize();
    }
    else if ( okButton.getAsBoolean() || failButton.getAsBoolean()){
      currentCommand.cancel();
      if (okButton.getAsBoolean()){
        reportResult(currentCommand,true);
        currentCommandIndex++;
      }
      else{
        reportResult(currentCommand,false);
        currentCommandIndex=commandsToRun.length;
        overallSuccess = false;
      }

    }
    else{
      currentCommand.execute();
    }

  }

  public void reportResult(Command c, boolean success){
    DriverStation.reportWarning(c.getName() + ": result =>" + success,false);
  }

  @Override
  public void end(boolean interrupted) {
    DriverStation.reportWarning("Tests Complete: Overall [" + overallSuccess + "]",false);
  }

  @Override
  public boolean isFinished() {
    return ( currentCommandIndex >= commandsToRun.length);
  }
}
