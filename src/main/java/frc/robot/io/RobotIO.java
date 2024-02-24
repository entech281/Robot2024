package frc.robot.io;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.subsystems.drive.DriveOutput;
import frc.robot.subsystems.intake.IntakeOutput;
import frc.robot.subsystems.navx.NavXOutput;
import frc.robot.subsystems.note_detector.NoteDetectorOutput;
import frc.robot.subsystems.pivot.PivotOutput;
import frc.robot.subsystems.shooter.ShooterOutput;
import frc.robot.subsystems.transfer.TransferOutput;
import frc.robot.subsystems.vision.VisionOutput;
import frc.robot.subsystems.climb.ClimbOutput;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggableInputs;

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

<<<<<<< HEAD
  public NoteDetectorOutput getNoteDetectorOutput() {
    return latestNoteDetectorOutput;
=======
  public ClimbOutput getClimbOutput() {
    return latestClimbOutput;
>>>>>>> main
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

<<<<<<< HEAD
  public void updateNoteDetector(NoteDetectorOutput ndo) {
    latestNoteDetectorOutput = ndo;
    ndo.log();
=======
  public void updateClimb(ClimbOutput clo) {
    latestClimbOutput = clo;
    clo.log();
>>>>>>> main
  }

  public void updateOdometryPose(Pose2d pose) {
    latestOdometryPose = pose;
    Logger.recordOutput("OdometryPose", pose);
  }

  private VisionOutput latestVisionOutput;
  private NavXOutput latestNavXOutput;
  private DriveOutput latestDriveOutput;
  private IntakeOutput latestIntakeOutput;
  private TransferOutput latestTransferOutput;
  private ShooterOutput latestShooterOutput;
  private PivotOutput latestPivotOutput;
<<<<<<< HEAD
  private NoteDetectorOutput latestNoteDetectorOutput;
=======
  private ClimbOutput latestClimbOutput;
>>>>>>> main
  private Pose2d latestOdometryPose;
}
