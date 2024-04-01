package frc.robot.commands;

import java.util.Optional;
import entech.commands.EntechCommand;
import entech.util.AimCalculator;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.intake.IntakeInput;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.pivot.PivotSubsystem;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class AutoPrepareToShootCommand extends EntechCommand {

  private StoppingCounter cancelCounter = new StoppingCounter(RobotConstants.SHOOTER.RESET_DELAY);
  private StoppingCounter stableCounter = new StoppingCounter(RobotConstants.SHOOTER.SHOOT_DELAY);
  private final PivotSubsystem pivot;
  private final ShooterSubsystem shooter;
  private final IntakeSubsystem intake;

  private final ShooterInput sInput = new ShooterInput();
  private final PivotInput pInput = new PivotInput();
  private final IntakeInput iInput = new IntakeInput();

  private boolean noNote;

  private final double speed;

  public AutoPrepareToShootCommand(ShooterSubsystem shooter, PivotSubsystem pivot,
      IntakeSubsystem intake, double speed) {
    super(shooter, pivot);
    this.pivot = pivot;
    this.shooter = shooter;
    this.intake = intake;
    this.speed = speed;
  }

  @Override
  public void end(boolean interrupted) {
    pInput.setRequestedPosition(0);
    pivot.updateInputs(pInput);

    sInput.setActivate(false);
    shooter.updateInputs(sInput);

    iInput.setActivate(false);
    iInput.setSpeed(0.0);
    intake.updateInputs(iInput);
  }

  @Override
  public void initialize() {
    cancelCounter.reset();
    stableCounter.reset();
  }

  @Override
  public void execute() {
    if (RobotIO.getInstance().getInternalNoteDetectorOutput().hasNote()) {
      noNote = false;
      sInput.setActivate(true);
      sInput.setSpeed(speed);
      sInput.setBrakeModeEnabled(false);

      pInput.setActivate(true);
      Optional<Double> distance = RobotIO.getInstance().getDistanceFromTarget();
      pInput.setRequestedPosition(
          AimCalculator.getPivotAngleFromDistance(distance.isPresent() ? distance.get() : 1));

      iInput.setActivate(true);
      iInput.setSpeed(0.25);

      shooter.updateInputs(sInput);
      pivot.updateInputs(pInput);
      intake.updateInputs(iInput);
    } else {
      noNote = true;
    }
  }

  @Override
  public boolean isFinished() {
    return cancelCounter.isFinished(noNote);
  }
}
