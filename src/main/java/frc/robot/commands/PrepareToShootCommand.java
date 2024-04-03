package frc.robot.commands;

import java.util.Optional;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import entech.commands.EntechCommand;
import entech.util.AimCalculator;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.operation.UserPolicy;
import frc.robot.subsystems.intake.IntakeInput;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.pivot.PivotSubsystem;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.shooter.ShooterSubsystem;

public class PrepareToShootCommand extends EntechCommand {

  private StoppingCounter cancelCounter =
      new StoppingCounter(RobotConstants.SHOOTER.RESET_DELAY + 1);
  private StoppingCounter stableCounter = new StoppingCounter(RobotConstants.SHOOTER.SHOOT_DELAY);
  private final PivotSubsystem pivot;
  private final ShooterSubsystem shooter;
  private final IntakeSubsystem intake;

  private final ShooterInput sInput = new ShooterInput();
  private final PivotInput pInput = new PivotInput();
  private final IntakeInput iInput = new IntakeInput();

  private final Trigger ampSwitch;
  private final Trigger speakerSwitch;
  private final Trigger autoSwitch;
  private final XboxController controller;

  private boolean noNote;

  public PrepareToShootCommand(ShooterSubsystem shooter, PivotSubsystem pivot,
      IntakeSubsystem intake, Trigger ampSwitch, Trigger speakerSwitch, Trigger autoSwitch,
      XboxController controller) {
    super(shooter, pivot);
    this.pivot = pivot;
    this.shooter = shooter;
    this.ampSwitch = ampSwitch;
    this.speakerSwitch = speakerSwitch;
    this.controller = controller;
    this.intake = intake;
    this.autoSwitch = autoSwitch;
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
      iInput.setActivate(true);
      iInput.setSpeed(0.25);

      if (ampSwitch.getAsBoolean()) {
        pInput.setRequestedPosition(RobotConstants.PIVOT.SHOOT_AMP_POSITION_DEG);
        sInput.setSpeed(RobotConstants.PID.SHOOTER.AMP_SPEED);
      } else if (autoSwitch.getAsBoolean()) {
        sInput.setSpeed(RobotConstants.PID.SHOOTER.PODIUM_SPEED);
        Optional<Double> distance = RobotIO.getInstance().getDistanceFromTarget();

        pInput.setRequestedPosition(
            AimCalculator.getPivotAngleFromDistance(distance.isPresent() ? distance.get() : 1));
      } else if (speakerSwitch.getAsBoolean()) {
        pInput.setRequestedPosition(RobotConstants.PIVOT.SPEAKER_PODIUM_SCORING);
        sInput.setSpeed(RobotConstants.PID.SHOOTER.PODIUM_SPEED);
      } else {
        pInput.setRequestedPosition(RobotConstants.PIVOT.SPEAKER_SUBWOOFER_SCORING);
        sInput.setSpeed(RobotConstants.PID.SHOOTER.SUBWOOFER_SPEED);
      }
      shooter.updateInputs(sInput);
      pivot.updateInputs(pInput);
      intake.updateInputs(iInput);

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
