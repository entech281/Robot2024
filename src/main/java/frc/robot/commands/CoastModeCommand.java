package frc.robot.commands;

import entech.commands.EntechCommand;
import frc.robot.subsystems.climb.ClimbInput;
import frc.robot.subsystems.intake.IntakeInput;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.transfer.TransferInput;

import frc.robot.subsystems.climb.ClimbSubsystem;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.pivot.PivotSubsystem;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem;

public class CoastModeCommand extends EntechCommand {

  private ClimbInput cInput;
  private IntakeInput iInput;
  private PivotInput pInput;
  private ShooterInput sInput;
  private TransferInput tInput;

  private ClimbSubsystem cSubsystem;
  private IntakeSubsystem iSubsystem;
  private PivotSubsystem pSubsystem;
  private ShooterSubsystem sSubsystem;
  private TransferSubsystem tSubsystem;

  public CoastModeCommand(ClimbSubsystem climbSubsystem, IntakeSubsystem intakeSubsystem,
      PivotSubsystem pivotSubsystem, ShooterSubsystem shooterSubsystem,
      TransferSubsystem transferSubsystem) {
    this.cSubsystem = climbSubsystem;
    this.iSubsystem = intakeSubsystem;
    this.pSubsystem = pivotSubsystem;
    this.sSubsystem = shooterSubsystem;
    this.tSubsystem = transferSubsystem;
  }

  @Override
  public void initialize() {
    cSubsystem.updateInputs(cInput);
    iSubsystem.updateInputs(iInput);
    pSubsystem.updateInputs(pInput);
    sSubsystem.updateInputs(sInput);
    tSubsystem.updateInputs(tInput);
  }

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
