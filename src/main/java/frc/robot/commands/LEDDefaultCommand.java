package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.button.Trigger;
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

  private boolean hasError() {
    return RobotIO.getInstance().getNavXOutput().isFaultDetected();
  }

  private boolean readyToShoot() {
    return RobotIO.getInstance().getShooterOutput().isAtSpeed()
        && (RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()
        || RobotIO.getInstance().getInternalNoteDetectorOutput().forwardSensorHasNote());
  }

  private boolean shooterHasNote() {
    return RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()
        || RobotIO.getInstance().getInternalNoteDetectorOutput().forwardSensorHasNote();
  }

  @Override
  public void execute() {
    if (hasError()) {
      input.setBlinking(true);
      input.setColor(Color.kRed);
      input.setSecondaryColor(Color.kBlack);
    } else if (readyToShoot()) {
      //rumble code
    } else if (RobotIO.getInstance().getIntakeOutput().isActive()) {
      input.setBlinking(true);
      input.setColor(Color.kPurple);
      if (RobotIO.getInstance().getNoteDetectorOutput() != null && RobotIO.getInstance().getNoteDetectorOutput().hasNotes()) {
        input.setSecondaryColor(Color.kOrange);
      } else {
        input.setSecondaryColor(Color.kBlack);
      }
    } else if (shooterHasNote()) {
      input.setBlinking(false);
      input.setColor(Color.kPurple);
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
