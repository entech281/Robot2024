package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.operation.UserPolicy;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.pivot.PivotSubsystem;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class PrepareToShoot extends EntechCommand {
  private StoppingCounter cancelCounter = new StoppingCounter(RobotConstants.SHOOTER.RESET_DELAY);
  private StoppingCounter stableCounter = new StoppingCounter(RobotConstants.SHOOTER.SHOOT_DELAY);
  private final PivotSubsystem pivot;
  private final ShooterSubsystem shooter;

  private final ShooterInput sInput = new ShooterInput();
  private final PivotInput pInput = new PivotInput();

  private final Trigger ampSwitch;
  private final Trigger speakerSwitch;

  private boolean noNote;

  public PrepareToShoot(ShooterSubsystem shooter, PivotSubsystem pivot, Trigger ampSwitch,
      Trigger speakerSwitch) {
    super(shooter, pivot);
    this.pivot = pivot;
    this.shooter = shooter;
    this.ampSwitch = ampSwitch;
    this.speakerSwitch = speakerSwitch;
  }

  @Override
  public void end(boolean interrupted) {
    pInput.setRequestedPosition(0);
    pivot.updateInputs(pInput);

    sInput.setActivate(false);
    shooter.updateInputs(sInput);
    UserPolicy.getInstance().setReadyToShoot(false);
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
      pInput.setActivate(true);

      if (ampSwitch.getAsBoolean()) {
        pInput.setRequestedPosition(RobotConstants.PIVOT.SHOOT_AMP_POSITION_DEG);
        sInput.setSpeed(RobotConstants.PID.SHOOTER.AMP_SPEED);
      } else if (speakerSwitch.getAsBoolean()) {
        pInput.setRequestedPosition(RobotConstants.PIVOT.SPEAKER_PODIUM_SCORING);
        sInput.setSpeed(RobotConstants.PID.SHOOTER.PODIUM_SPEED);
      } else {
        pInput.setRequestedPosition(RobotConstants.PIVOT.SPEAKER_SUBWOOFER_SCORING);
        sInput.setSpeed(RobotConstants.PID.SHOOTER.SUBWOOFER_SPEED);
      }
      shooter.updateInputs(sInput);
      pivot.updateInputs(pInput);

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
  public boolean isFinished() {
    return cancelCounter.isFinished(noNote);
  }
}
