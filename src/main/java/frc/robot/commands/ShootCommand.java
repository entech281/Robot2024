package frc.robot.commands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import entech.commands.EntechCommand;
import entech.util.StoppingCounter;
import frc.robot.RobotConstants;
import frc.robot.io.RobotIO;
import frc.robot.subsystems.pivot.PivotInput;
import frc.robot.subsystems.pivot.PivotSubsystem;
import frc.robot.subsystems.shooter.ShooterInput;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import frc.robot.subsystems.transfer.TransferInput;
import frc.robot.subsystems.transfer.TransferSubsystem;
import frc.robot.subsystems.transfer.TransferSubsystem.TransferPreset;

public class ShootCommand extends EntechCommand {

  private ShooterInput sInput = new ShooterInput();
  private PivotInput pInput = new PivotInput();
  private TransferInput tInput = new TransferInput();

  private StoppingCounter cancelCounter = new StoppingCounter(RobotConstants.SHOOTER.RESET_DELAY);
  private StoppingCounter shootingCounter = new StoppingCounter(RobotConstants.SHOOTER.SHOOT_DELAY);

  private final ShooterSubsystem sSubsystem;
  private final PivotSubsystem pSubsystem;
  private final TransferSubsystem tSubsystem;
  private final Trigger ampSwitch;
  private final Trigger speakerSwitch;

  private boolean noNote;

  public ShootCommand(ShooterSubsystem shooterSubsystem, PivotSubsystem pivotSubsystem,
      TransferSubsystem transferSubsystem, Trigger ampSwitch, Trigger speakerSwitch) {
    super(shooterSubsystem, pivotSubsystem, transferSubsystem);
    this.sSubsystem = shooterSubsystem;
    this.pSubsystem = pivotSubsystem;
    this.tSubsystem = transferSubsystem;
    this.ampSwitch = ampSwitch;
    this.speakerSwitch = speakerSwitch;
  }

  // angleA is the angle that the angle of the arm when it is flat
  // sideB is the length from the pivot point to the shooter
  // targetPos is the virtual position that the arm should be aiming at
  // originPos is the location of the pivot in the field
  private static final double aimToPoint(double angleA, double sideB, double targetPos[],
      double originPos[]) {
    double sideA = Math.sqrt((Math.pow((targetPos[1] - originPos[1]), 2)
        + (Math.pow((targetPos[0] - originPos[0]), 2))));

    double angleB = Math.toDegrees(Math.sin(sideB * Math.sin(Math.toRadians(angleA)) / sideA));

    double angleC = 180 - angleB - angleA;

    double sideD = Math.sqrt(
        Math.pow((originPos[0] - targetPos[0]), 2) + Math.pow((originPos[2] - targetPos[2]), 2));

    double sideE = originPos[1] - targetPos[1];

    double angleE = Math.toDegrees(Math.acos(
        (Math.pow(sideA, 2) + Math.pow(sideD, 2) - Math.pow(sideE, 2)) / (2 * sideA * sideD)));

    double angleTheta = 180 - (angleC + angleE);
    return angleTheta;
  }

  @Override
  public void initialize() {
    cancelCounter.reset();
    shootingCounter.reset();
    if (RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()
        || RobotIO.getInstance().getInternalNoteDetectorOutput().forwardSensorHasNote()) {
      noNote = false;
      sInput.setActivate(true);
      pInput.setActivate(true);
    } else {
      noNote = true;
    }

    pInput.setRequestedPosition(aimToPoint(57.0, Units.inchesToMeters(15),
        new double[] {0.0, Units.inchesToMeters(80), 0.0},
        new double[] {RobotIO.getInstance().getOdometryPose().getX(), Units.inchesToMeters(18),
            RobotIO.getInstance().getOdometryPose().getY()}));

    if (ampSwitch.getAsBoolean()) {
      // pInput.setRequestedPosition(RobotConstants.PIVOT.SHOOT_AMP_POSITION_DEG);
      sInput.setSpeed(RobotConstants.PID.SHOOTER.AMP_SPEED);
    } else if (speakerSwitch.getAsBoolean()) {
      // pInput.setRequestedPosition(RobotConstants.PIVOT.SPEAKER_PODIUM_SCORING);
      sInput.setSpeed(RobotConstants.PID.SHOOTER.PODIUM_SPEED);
    } else {
      // pInput.setRequestedPosition(RobotConstants.PIVOT.SPEAKER_SUBWOOFER_SCORING);
      sInput.setSpeed(RobotConstants.PID.SHOOTER.SUBWOOFER_SPEED);
    }
    sSubsystem.updateInputs(sInput);
    pSubsystem.updateInputs(pInput);
  }

  @Override
  public void execute() {
    if (shootingCounter.isFinished(RobotIO.getInstance().getShooterOutput().isAtSpeed()
        && (RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()
            || RobotIO.getInstance().getInternalNoteDetectorOutput().forwardSensorHasNote()))) {
      tInput.setActivate(true);
      tInput.setSpeedPreset(TransferPreset.Shooting);
      tSubsystem.updateInputs(tInput);
    } else if (!(RobotIO.getInstance().getInternalNoteDetectorOutput().rearSensorHasNote()
        || RobotIO.getInstance().getInternalNoteDetectorOutput().forwardSensorHasNote())) {
      noNote = true;
    }
  }

  @Override
  public void end(boolean interupted) {

    pInput.setRequestedPosition(0);
    pSubsystem.updateInputs(pInput);

    sInput.setActivate(false);
    sSubsystem.updateInputs(sInput);

    if (RobotIO.getInstance().getTransferOutput().isActive()) {
      tInput.setActivate(false);
      tInput.setSpeedPreset(TransferPreset.Off);
      tSubsystem.updateInputs(tInput);
    }
  }

  @Override
  public boolean isFinished() {
    return cancelCounter.isFinished(noNote);
  }
}
