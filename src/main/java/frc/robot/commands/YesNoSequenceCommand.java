package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.util.List;
import java.util.Optional;


/*
   this command will execute commands one at a time.
   During the sequence, if the okButton is pressed, we will move along to the next
   command. if failButton is pressed, we will stop execution
 */
public class YesNoSequenceCommand extends Command {

  private final Command[] commandsToRun;
  private int currentCommandIndex = 0;

  private final Trigger okButton;
  private final Trigger failButton;

  private Command currentCommand;
  private Optional<Boolean> result;
  private final boolean finished = false;

  public YesNoSequenceCommand(Trigger okButton, Trigger failButton,Command ...commandsToRun){
    this.commandsToRun = commandsToRun;
    this.okButton=okButton;
    this.failButton=failButton;
    result = Optional.empty();
    if ( commandsToRun.length < 1){
      throw new RuntimeException("No Commands Provided to run!");
    }
  }

  @Override
  public void initialize() {
    moveToNextCommand();
  }

  @Override
  public void execute() {

    processCurrentCommand();

    if ( okButton.getAsBoolean() || failButton.getAsBoolean()){
      //user reported success or failure. move to next command if there is one
      currentCommand.cancel();
      boolean testFailed  = failButton.getAsBoolean();
      reportResult(currentCommand.getName(),testFailed);

      if ( testFailed){
        //do not run any more tests
        result = Optional.of(false);
      }
      else{
        moveToNextCommand();
      }
    }
    else{

      if ( currentCommand.isFinished()) {
        moveToNextCommand();
      }
    }

  }

  private void processCurrentCommand(){
    currentCommand.execute();
    if ( currentCommand.isFinished()){
      currentCommand.end(false);
    }
  }
  private void moveToNextCommand(){

    if (currentCommandIndex < commandsToRun.length ){
       currentCommand = commandsToRun[currentCommandIndex];
       currentCommand.initialize();
      currentCommandIndex++;
    }
    else{
      //if there are no more commands, we are finished!
      result = Optional.of(true);
    }

  }

  public void reportResult(String name, boolean success){
    DriverStation.reportWarning(name + ": result =>" + success,false);
  }

  @Override
  public void end(boolean interrupted) {
    DriverStation.reportWarning("Tests Complete: Overall [" + result.orElse(false) + "]",false);
  }

  @Override
  public boolean isFinished() {
    return result.orElse(false);
  }
}
