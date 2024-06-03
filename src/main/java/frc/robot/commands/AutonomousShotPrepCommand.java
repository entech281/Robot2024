package frc.robot.commands;

import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.PathPlannerTrajectory;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.CommandFactory;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.operation.UserPolicy;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.internalnotedetector.InternalNoteDetectorOutput;
import frc.robot.subsystems.pivot.PivotSubsystem;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.transfer.TransferInput;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem.TransferPreset;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.intake.IntakeInput;

public class AutonomousShotPrepCommand extends EntechCommand {

  private final ShooterSubsystem shooter;
  private final PivotSubsystem pivot;
  private final IntakeSubsystem intake;

  private final ShooterInput sInput = new ShooterInput();
  private final PivotInput pInput = new PivotInput();
  private final IntakeInput iInput = new IntakeInput();

  private StoppingCounter cancelCounter = new StoppingCounter(RobotConstants.SHOOTER.RESET_DELAY);
  private StoppingCounter stableCounter = new StoppingCounter(RobotConstants.SHOOTER.SHOOT_DELAY);

  double pviotAngle;
  boolean noNote = false;

  public AutonomousShotPrepCommand(ShooterSubsystem shooterSubsystem, PivotSubsystem pivotSubsystem,
      IntakeSubsystem intakeSubsystem, double pviotAngle) {
    super(shooterSubsystem, pivotSubsystem);
    this.shooter = shooterSubsystem;
    this.pivot = pivotSubsystem;
    this.intake = intakeSubsystem;
  }

  @Override
  public void initialize() {
    cancelCounter.reset();
    stableCounter.reset();
    noNote = false;
    UserPolicy.getInstance().setPreparingToShoot(true);

    sInput.setActivate(true);
    sInput.setSpeed(RobotConstants.PID.SHOOTER.PODIUM_SPEED);
    shooter.updateInputs(sInput);

    pInput.setActivate(true);
    pInput.setRequestedPosition(pviotAngle);
    pivot.updateInputs(pInput);

    iInput.setActivate(true);
    iInput.setSpeed(RobotConstants.INTAKE.SHOOTING_SPEED);
    intake.updateInputs(iInput);
  }

  @Override
  public void execute() {
    if (RobotIO.getInstance().getInstance().getInternalNoteDetectorOutput().hasNote()) {
      UserPolicy.getInstance().setPreparingToShoot(true);

      if (stableCounter.isFinished(shooter.getOutputs().isAtSpeed()
          && shooter.getOutputs().getCurrentSpeed() > RobotConstants.PID.SHOOTER.AMP_SPEED / 2
          && pivot.getOutputs().isAtRequestedPosition() && pivot.getOutputs()
              .getCurrentPosition() > RobotConstants.PIVOT.SPEAKER_SUBWOOFER_SCORING / 2)) {
        UserPolicy.getInstance().setReadyToShoot(true);
      } else {
        UserPolicy.getInstance().setReadyToShoot(false);
      }
    } else {
      noNote = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
    pInput.setRequestedPosition(0);
    pivot.updateInputs(pInput);

    sInput.setActivate(false);
    shooter.updateInputs(sInput);

    iInput.setActivate(false);
    iInput.setSpeed(0);
    intake.updateInputs(iInput);
  }

  @Override
  public boolean isFinished() {
    return cancelCounter.isFinished(noNote);
  }

  @Override
  public boolean runsWhenDisabled() {
    return false;
  }
}
