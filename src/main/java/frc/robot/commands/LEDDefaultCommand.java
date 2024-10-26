package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import frc.entech.commands.EntechCommand;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.led.LEDInput;
import frc.robot.subsystems.led.LEDSubsystem;

public class LEDDefaultCommand extends EntechCommand {
  private final LEDSubsystem ledSubsystem;
  private LEDInput input = new LEDInput();

  public LEDDefaultCommand(LEDSubsystem ledSubsystem) {
    super(ledSubsystem);
    this.ledSubsystem = ledSubsystem;
  }

  private boolean hasError() {
    return RobotIO.getInstance().getNavXOutput().isFaultDetected()
        || RobotIO.getInstance().getVisionOutput().isDriverMode()
        || RobotIO.getInstance().getNoteDetectorOutput().isDriverMode();
  }

  @Override
  public void execute() {
    if (hasError()) {
      input.setBlinking(true);
      input.setColor(Color.kRed);
      input.setSecondaryColor(Color.kBlack);
    } else if (RobotIO.getInstance().getInternalNoteDetectorOutput().hasNote()) {
      input.setBlinking(false);
      input.setColor(Color.kPurple);
    } else if (RobotIO.getInstance().getNoteDetectorOutput() != null
        && RobotIO.getInstance().getNoteDetectorOutput().hasNotes()) {
      input.setBlinking(false);
      input.setColor(Color.kOrange);
    } else {
      input.setBlinking(false);
      input.setColor(Color.kGreen);
    }

    ledSubsystem.updateInputs(input);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
