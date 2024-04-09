package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.intake.IntakeInput;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.led.LEDInput;
import frc.robot.subsystems.led.LEDSubsystem;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.transfer.TransferInput;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem.TransferPreset;

public class IntakeNoteCommand extends EntechCommand {

  private final IntakeSubsystem intSubsystem;
  private final TransferSubsystem transSubsystem;
  private final ShooterSubsystem shooterSubsystem;
  private final LEDSubsystem lSubsystem;

  private IntakeInput iInput = new IntakeInput();
  private ShooterInput sInput = new ShooterInput();
  private TransferInput tInput = new TransferInput();

  private StoppingCounter counter = new StoppingCounter(0.1);

  private boolean retracting;
  private boolean retracted;
  private boolean hasNote;

  public IntakeNoteCommand(IntakeSubsystem iSubsystem, TransferSubsystem tSubsystem,
      ShooterSubsystem sSubsystem, LEDSubsystem ledSubsystem) {
    super(iSubsystem, tSubsystem, ledSubsystem, sSubsystem);
    this.intSubsystem = iSubsystem;
    this.transSubsystem = tSubsystem;
    this.lSubsystem = ledSubsystem;
    this.shooterSubsystem = sSubsystem;
  }

  @Override
  public void initialize() {
    retracted = false;
    retracting = false;
    hasNote = false;
    sInput.setBrakeModeEnabled(true);
    iInput.setActivate(true);
    iInput.setSpeed(RobotConstants.INTAKE.INTAKE_SPEED);
    tInput.setActivate(true);
    tInput.setSpeedPreset(TransferPreset.INTAKING1);
    intSubsystem.updateInputs(iInput);
    transSubsystem.updateInputs(tInput);
    shooterSubsystem.updateInputs(sInput);

    LEDInput lIn = new LEDInput();
    lIn.setColor(Color.kPurple);
    lIn.setBlinking(true);
    if (RobotIO.getInstance().getNoteDetectorOutput() != null
        && RobotIO.getInstance().getNoteDetectorOutput().hasNotes()) {
      lIn.setSecondaryColor(Color.kOrange);
    } else {
      lIn.setSecondaryColor(Color.kBlack);
    }
    lSubsystem.updateInputs(lIn);
  }

  @Override
  public void execute() {
    if (retracting) {
      tInput.setSpeedPreset(TransferPreset.RETRACTING);
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
          tInput.setSpeedPreset(TransferPreset.INTAKING2);
          transSubsystem.updateInputs(tInput);
        }
      }
    }
  }

  @Override
  public void end(boolean interupted) {
    sInput.setBrakeModeEnabled(false);
    iInput.setActivate(false);
    tInput.setActivate(false);
    intSubsystem.updateInputs(iInput);
    transSubsystem.updateInputs(tInput);
    shooterSubsystem.updateInputs(sInput);
  }

  @Override
  public boolean isFinished() {
    if (retracted) {
      return true;
    } else {
      if (counter.isFinished(hasNote) && hasNote) {
        if (RobotIO.getInstance().getInternalNoteDetectorOutput().hasNote()) {
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
