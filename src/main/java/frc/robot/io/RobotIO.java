package frc.robot.io;

import java.util.Optional;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggableInputs;
import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.subsystems.LEDs.LEDOutput;
import frc.robot.subsystems.climb.ClimbOutput;
import frc.robot.subsystems.drive.DriveOutput;
import frc.robot.subsystems.intake.IntakeOutput;
import frc.robot.subsystems.internalNoteDetector.InternalNoteDetectorOutput;
import frc.robot.subsystems.navx.NavXOutput;
import frc.robot.subsystems.noteDetector.NoteDetectorOutput;
import frc.robot.subsystems.pivot.PivotOutput;
import frc.robot.subsystems.shooter.ShooterOutput;
import frc.robot.subsystems.transfer.TransferOutput;
import frc.robot.subsystems.vision.VisionOutput;

public class RobotIO {
  private static RobotIO instance = new RobotIO();

  public static RobotIO getInstance() {
    return instance;
  }

  public static void processInput(LoggableInputs in) {
    Logger.processInputs(in.getClass().getSimpleName(), in);
  }

  private RobotIO() {}

  public VisionOutput getVisionOutput() {
    return latestVisionOutput;
  }

  public DriveOutput getDriveOutput() {
    return latestDriveOutput;
  }

  public NavXOutput getNavXOutput() {
    return latestNavXOutput;
  }

  public IntakeOutput getIntakeOutput() {
    return latestIntakeOutput;
  }

  public ShooterOutput getShooterOutput() {
    return latestShooterOutput;
  }

  public TransferOutput getTransferOutput() {
    return latestTransferOutput;
  }

  public PivotOutput getPivotOutput() {
    return latestPivotOutput;
  }

  public NoteDetectorOutput getNoteDetectorOutput() {
    return latestNoteDetectorOutput;
  }

  public ClimbOutput getClimbOutput() {
    return latestClimbOutput;

  }

  public InternalNoteDetectorOutput getInternalNoteDetectorOutput() {
    return latestInternalNoteDetectorOutput;
  }

  public Pose2d getOdometryPose() {
    return latestOdometryPose;
  }

  public void updateVision(VisionOutput vo) {
    latestVisionOutput = vo;
    vo.log();
  }

  public void updateNavx(NavXOutput no) {
    latestNavXOutput = no;
    no.log();
  }

  public void updateDrive(DriveOutput dro) {
    latestDriveOutput = dro;
    dro.log();
  }

  public void updateTransfer(TransferOutput tro) {
    latestTransferOutput = tro;
    tro.log();
  }

  public void updateShooter(ShooterOutput sho) {
    latestShooterOutput = sho;
    sho.log();
  }

  public void updateIntake(IntakeOutput ito) {
    latestIntakeOutput = ito;
    ito.log();
  }

  public void updatePivot(PivotOutput pio) {
    latestPivotOutput = pio;
    pio.log();
  }

  public void updateClimb(ClimbOutput clo) {
    latestClimbOutput = clo;
    clo.log();
  }

  public void updateInternalNoteDetector(InternalNoteDetectorOutput hno) {
    latestInternalNoteDetectorOutput = hno;
    hno.log();
  }

  public void updateOdometryPose(Pose2d pose) {
    latestOdometryPose = pose;
    Logger.recordOutput("OdometryPose", pose);
  }

  public void updateNoteDetector(NoteDetectorOutput ndo) {
    latestNoteDetectorOutput = ndo;
    ndo.log();
  }

  public Optional<Double> getDistanceFromTarget() {
    return distanceFromTarget;
  }

  public LEDOutput getLEDOutput() {
    return latestLEDOutput;
  }

  public void updateLED(LEDOutput ledo) {
    latestLEDOutput = ledo;
  }

  public void setDistanceFromTarget(Optional<Double> distance) {
    distanceFromTarget = distance;
    if (distance.isPresent()) {
      Logger.recordOutput("DistanceFromTarget", distance.get());
    } else {
      Logger.recordOutput("DistanceFromTarget", -1);
    }
  }

  private VisionOutput latestVisionOutput;
  private NavXOutput latestNavXOutput;
  private DriveOutput latestDriveOutput;
  private IntakeOutput latestIntakeOutput;
  private TransferOutput latestTransferOutput;
  private ShooterOutput latestShooterOutput;
  private PivotOutput latestPivotOutput;
  private InternalNoteDetectorOutput latestInternalNoteDetectorOutput;
  private NoteDetectorOutput latestNoteDetectorOutput;
  private ClimbOutput latestClimbOutput;
  private Pose2d latestOdometryPose;
  private LEDOutput latestLEDOutput;
  private Optional<Double> distanceFromTarget = Optional.empty();
}
