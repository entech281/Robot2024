package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;
import frc.entech.commands.EntechCommand;
import frc.entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.livetuning.LiveTuningHandler;
import frc.robot.operation.UserPolicy;
// import frc.robot.subsystems.intake.IntakeInput;
// import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.pivot.PivotSubsystem;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class PrepareToShootCommand extends EntechCommand {
  // private static final double OFFSET = -2.0;
  private StoppingCounter cancelCounter =
      new StoppingCounter(RobotConstants.SHOOTER.RESET_DELAY + 1);
  private StoppingCounter stableCounter = new StoppingCounter(RobotConstants.SHOOTER.SHOOT_DELAY);
  private final PivotSubsystem pivot;
  private final ShooterSubsystem shooter;

  private final ShooterInput sInput = new ShooterInput();
  private final PivotInput pInput = new PivotInput();

  private final XboxController controller;

  private boolean noNote;

  public PrepareToShootCommand(ShooterSubsystem shooter, PivotSubsystem pivot,
      XboxController controller) {
    super(shooter, pivot);
    this.pivot = pivot;
    this.shooter = shooter;
    this.controller = controller;
  }

  @Override
  public void end(boolean interrupted) {
    pInput.setRequestedPosition(0);
    pivot.updateInputs(pInput);

    sInput.setActivate(false);
    shooter.updateInputs(sInput);

    UserPolicy.getInstance().setReadyToShoot(false);
    controller.setRumble(RumbleType.kBothRumble, 0.0);
    UserPolicy.getInstance().setPreparingToShoot(false);
  }

  @Override
  public void initialize() {
    cancelCounter.reset();
    stableCounter.reset();
    controller.setRumble(RumbleType.kBothRumble, 0.0);
    UserPolicy.getInstance().setPreparingToShoot(true);
  }

  @Override
  public void execute() {
    if (RobotIO.getInstance().getInternalNoteDetectorOutput().hasNote()) {
      noNote = false;
      sInput.setActivate(true);
      pInput.setActivate(true);

      pInput.setRequestedPosition(
          LiveTuningHandler.getInstance().getValue("PivotSubsystem/selectedAngle"));
      sInput.setSpeed(LiveTuningHandler.getInstance().getValue("ShooterSubsystem/selectedSpeed"));

      shooter.updateInputs(sInput);
      pivot.updateInputs(pInput);

      if (stableCounter.isFinished(shooter.getOutputs().isAtSpeed()
          && shooter.getOutputs().getCurrentSpeed() > RobotConstants.PID.SHOOTER.AMP_SPEED / 2
          && pivot.getOutputs().isAtRequestedPosition() && pivot.getOutputs()
              .getCurrentPosition() > RobotConstants.PIVOT.SPEAKER_SUBWOOFER_SCORING / 2)) {
        UserPolicy.getInstance().setReadyToShoot(true);
        controller.setRumble(RumbleType.kBothRumble, 1.0);
      } else {
        UserPolicy.getInstance().setReadyToShoot(false);
        controller.setRumble(RumbleType.kBothRumble, 0.0);
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
