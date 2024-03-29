package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import entech.commands.EntechCommand;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.LEDs.LEDInput;
import frc.robot.subsystems.LEDs.LEDSubsystem;

public class LEDDefaultCommand extends EntechCommand {
  private final LEDSubsystem ledSubsystem;
  private final LEDInput input = new LEDInput();
  private Trigger ampSwitch;
  private Trigger speakerSwitch;

  public LEDDefaultCommand(LEDSubsystem ledSubsystem, Trigger ampSwitch, Trigger speakerSwitch) {
    super(ledSubsystem);
    this.ledSubsystem = ledSubsystem;
    this.ampSwitch = ampSwitch;
    this.speakerSwitch = speakerSwitch;
  }

  public boolean hasError() {
    return RobotIO.getInstance().getNavXOutput().isFaultDetected();
  }

  public boolean readyToShoot() {
    return RobotIO.getInstance().getShooterOutput().isAtSpeed()
        && (RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()
        || RobotIO.getInstance().getInternalNoteDetectorOutput().forwardSensorHasNote());
  }

  @Override
  public void execute() {
    if (hasError()) {
      input.setBlinking(true);
      input.setColor(Color.kRed);
    } else if (readyToShoot()) {
      input.setBlinking(false);
      input.setColor(Color.kGreen); // or rumble the controller
    } else if (RobotIO.getInstance().getIntakeOutput().isActive()) {
      input.setBlinking(true);
      if (RobotIO.getInstance().getDriveOutput().getModuleStates()[0].speedMetersPerSecond != 0) {
        input.setColor(Color.kOrange);
      } else {
        input.setColor(Color.kPurple);
      }
    } else if (!(RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()
        || RobotIO.getInstance().getInternalNoteDetectorOutput().forwardSensorHasNote())
        && RobotIO.getInstance().getNoteDetectorOutput() != null) {
      if (RobotIO.getInstance().getNoteDetectorOutput().hasNotes()) {
        input.setBlinking(false);
        input.setColor(Color.kOrange);
      }
    } else {
      input.setBlinking(false);
      if (ampSwitch.getAsBoolean()) {
        input.setColor(Color.kDarkGreen);
      } else if (speakerSwitch.getAsBoolean()) {
        input.setColor(Color.kPink);
      } else {
        input.setColor(Color.kMaroon);
      }
      //needs code to read oi for the wanted shooting position
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
