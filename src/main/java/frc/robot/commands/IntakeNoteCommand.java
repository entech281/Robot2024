package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import entech.commands.EntechCommand;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem.TransferPreset;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.intake.IntakeInput;
import frc.robot.subsystems.transfer.TransferInput;

public class IntakeNoteCommand extends EntechCommand {

  private IntakeSubsystem intSubsystem;
  private TransferSubsystem transSubsystem;

  private IntakeInput iInput = new IntakeInput();
  private TransferInput tInput = new TransferInput();

  public IntakeNoteCommand(IntakeSubsystem iSubsystem, TransferSubsystem tSubsystem) {
    super(iSubsystem, tSubsystem);
    this.intSubsystem = iSubsystem;
    this.transSubsystem = tSubsystem;
  }

  @Override
  public void initialize() {
    DriverStation.reportWarning("Command was called", false);
    iInput.setActivate(true);
    iInput.setSpeed(RobotConstants.INTAKE.INTAKE_SPEED);
    iInput.setBrakeModeEnabled(false);
    tInput.setActivate(true);
    tInput.setSpeedPreset(TransferPreset.Intaking1);
    tInput.setBrakeModeEnabled(false);
    intSubsystem.updateInputs(iInput);
    transSubsystem.updateInputs(tInput);
  }

  @Override
  public void execute() {
    if (RobotIO.getInstance().getInternalNoteDetectorOutput().forwardSensorHasNote()) {
      iInput.setSpeed(RobotConstants.INTAKE.INTAKE_SPEED / 2);
      intSubsystem.updateInputs(iInput);

      tInput.setSpeedPreset(TransferPreset.Intaking2);
      transSubsystem.updateInputs(tInput);
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
    return RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote();
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }

}
