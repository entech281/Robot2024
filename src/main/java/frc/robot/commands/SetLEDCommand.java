package frc.robot.commands;

import entech.commands.EntechCommand;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.LEDs.LEDInput;
import frc.robot.subsystems.LEDs.LEDSubsystem;
import edu.wpi.first.wpilibj.util.Color;

public class SetLEDCommand extends EntechCommand {
  
  private LEDInput ledInput = new LEDInput();

  private final LEDSubsystem ledSubsystem;
  private final RobotIO ri = RobotIO.getInstance();

  public SetLEDCommand(LEDSubsystem ledSubsystem) {
    this.ledSubsystem = ledSubsystem;
  }

  public void execute() {
    updateIntakeLights();

  }

  public void updateIntakeLights() {
      boolean intakeHasNote = ri.getInternalNoteDetectorOutput().forwardSensorHasNote() || ri.getInternalNoteDetectorOutput().rearSensorHasNote();

    if (ri.getIntakeOutput().isActive()) {
      ledInput.setColor(Color.kOrange);
      if (!intakeHasNote) {
        ledInput.setBlinking(true);
      } else {
        ledInput.setBlinking(false);
      }
    } else {
      ledInput.setColor(Color.kBlack);
    }
    
    ledSubsystem.updateInputs(ledInput);
  }

  public void updateTargetLights() {

  }
}
