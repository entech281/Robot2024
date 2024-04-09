package frc.robot.commands;

import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.subsystems.intake.IntakeInput;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.transfer.TransferInput;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem.TransferPreset;

public class EjectNoteCommand extends EntechCommand {

  private IntakeSubsystem intSubsystem;
  private TransferSubsystem transSubsystem;
  private ShooterSubsystem shootSubsystem;

  private IntakeInput iInput = new IntakeInput();
  private TransferInput tInput = new TransferInput();
  private ShooterInput sInput = new ShooterInput();

  private StoppingCounter counter = new StoppingCounter(RobotConstants.INTAKE.EJECTING_TIME);

  public EjectNoteCommand(IntakeSubsystem iSubsystem, TransferSubsystem tSubsystem,
      ShooterSubsystem sSubsystem) {
    super(iSubsystem, tSubsystem);
    this.intSubsystem = iSubsystem;
    this.transSubsystem = tSubsystem;
    this.shootSubsystem = sSubsystem;
  }

  @Override
  public void initialize() {
    iInput.setActivate(true);
    iInput.setSpeed(RobotConstants.INTAKE.EJECTING_SPEED);
    tInput.setActivate(true);
    tInput.setSpeedPreset(TransferPreset.EJECTING);
    intSubsystem.updateInputs(iInput);
    transSubsystem.updateInputs(tInput);
    counter.reset();
    sInput.setActivate(true);
    sInput.setSpeed(-1500);
    shootSubsystem.updateInputs(sInput);
  }

  @Override
  public void end(boolean interupted) {
    iInput.setActivate(false);
    iInput.setSpeed(0);
    tInput.setActivate(false);
    tInput.setSpeedPreset(TransferPreset.OFF);
    intSubsystem.updateInputs(iInput);
    transSubsystem.updateInputs(tInput);
    sInput.setActivate(false);
    sInput.setSpeed(0);
    shootSubsystem.updateInputs(sInput);
  }

  @Override
  public boolean isFinished() {
    return counter.isFinished(true);
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
