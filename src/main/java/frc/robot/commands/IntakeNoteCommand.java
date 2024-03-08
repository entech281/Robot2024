package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.LEDs.LEDInput;
import frc.robot.subsystems.LEDs.LEDSubsystem;
import frc.robot.subsystems.intake.IntakeInput;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.transfer.TransferInput;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem.TransferPreset;

public class IntakeNoteCommand extends EntechCommand {

  private final IntakeSubsystem intSubsystem;
  private final TransferSubsystem transSubsystem;
  private final LEDSubsystem lSubsystem;

  private IntakeInput iInput = new IntakeInput();
  private TransferInput tInput = new TransferInput();

  private StoppingCounter counter = new StoppingCounter(getClass().getSimpleName(), 0.1);

  private boolean retracting;
  private boolean retracted;
  private boolean hasNote;

  public IntakeNoteCommand(IntakeSubsystem iSubsystem, TransferSubsystem tSubsystem,
      LEDSubsystem ledSubsystem) {
    super(iSubsystem, tSubsystem, ledSubsystem);
    this.intSubsystem = iSubsystem;
    this.transSubsystem = tSubsystem;
    this.lSubsystem = ledSubsystem;
  }

  @Override
  public void initialize() {
    DriverStation.reportWarning("Command was called", false);
    retracted = false;
    retracting = false;
    hasNote = false;
    iInput.setActivate(true);
    iInput.setSpeed(RobotConstants.INTAKE.INTAKE_SPEED);
    tInput.setActivate(true);
    tInput.setSpeedPreset(TransferPreset.Intaking1);
    intSubsystem.updateInputs(iInput);
    transSubsystem.updateInputs(tInput);

    LEDInput lIn = new LEDInput();
    lIn.setColor(Color.kPurple);
    lIn.setBlinking(true);
    lSubsystem.updateInputs(lIn);
  }

  @Override
  public void execute() {
    if (retracting) {
      tInput.setSpeedPreset(TransferPreset.Retracting);
      transSubsystem.updateInputs(tInput);
      if (RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()) {
        retracted = true;
      }
    } else {
      if (RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()) {
        hasNote = true;
        iInput.setActivate(false);
        intSubsystem.updateInputs(iInput);
      } else {
        if (RobotIO.getInstance().getInternalNoteDetectorOutput().forwardSensorHasNote()) {
          iInput.setActivate(false);
          intSubsystem.updateInputs(iInput);
          tInput.setSpeedPreset(TransferPreset.Intaking2);
          transSubsystem.updateInputs(tInput);
        }
      }
    }
  }

  @Override
  public void end(boolean interupted) {
    iInput.setActivate(false);
    tInput.setActivate(false);
    intSubsystem.updateInputs(iInput);
    transSubsystem.updateInputs(tInput);
  }

  @Override
  public boolean isFinished() {
    if (retracted) {
      return true;
    } else {
      if (counter.isFinished(hasNote) && hasNote) {
        if (RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()) {
          return true;
        } else {
          retracting = true;
          return false;
        }
      }
      return false;
    }
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }

}
