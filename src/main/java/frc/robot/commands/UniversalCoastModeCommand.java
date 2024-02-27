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

public class UniversalCoastModeCommand extends EntechCommand {

  private ClimbInput cInput = new ClimbInput();
  private IntakeInput iInput = new IntakeInput();
  private PivotInput pInput = new PivotInput();
  private ShooterInput sInput = new ShooterInput();
  private TransferInput tInput = new TransferInput();

  private ClimbSubsystem cSubsystem = new ClimbSubsystem();
  private IntakeSubsystem iSubsystem = new IntakeSubsystem();
  private PivotSubsystem pSubsystem = new PivotSubsystem();
  private ShooterSubsystem sSubsystem = new ShooterSubsystem();
  private TransferSubsystem tSubsystem = new TransferSubsystem();

  public UniversalCoastModeCommand() {}

  @Override
  public void initialize() {
    cInput.setBrakeModeEnabled(false);
    iInput.setBrakeModeEnabled(false);
    pInput.setBrakeModeEnabled(false);
    sInput.setBrakeModeEnabled(false);
    tInput.setBrakeModeEnabled(false);

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
