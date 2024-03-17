package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import entech.commands.EntechCommand;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.LEDs.LEDInput;
import frc.robot.subsystems.LEDs.LEDSubsystem;

public class LEDDefaultCommand extends EntechCommand {
  private final LEDSubsystem ledSubsystem;
  private final LEDInput input = new LEDInput();

  public LEDDefaultCommand(LEDSubsystem ledSubsystem) {
    super(ledSubsystem);
    this.ledSubsystem = ledSubsystem;
  }

  @Override
  public void execute() {
    if (RobotIO.getInstance().getNavXOutput().isFaultDetected()) {
      input.setBlinking(true);
      input.setColor(Color.kRed);
    } else if (RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()
        || RobotIO.getInstance().getInternalNoteDetectorOutput().forwardSensorHasNote()) {
      input.setColor(Color.kPurple);
    } else if (RobotIO.getInstance().getNoteDetectorOutput() != null) {
      if (RobotIO.getInstance().getNoteDetectorOutput().hasNotes()) {
        input.setColor(Color.kOrange);
      } else {
        input.setColor(Color.kGreen);
      }
    } else {
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
