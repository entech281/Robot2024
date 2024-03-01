package frc.robot.commands;

import org.photonvision.targeting.PhotonTrackedTarget;
import entech.commands.EntechCommand;
import frc.robot.io.DriveInputSupplier;
import frc.robot.processors.DriveInputProcessor;
import frc.robot.subsystems.drive.DriveInput;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.note_detector.NoteDetectorSubsystem;

public class NoteAlignCommand extends EntechCommand {
  private final DriveSubsystem drive;
  private final DriveInputProcessor inputProcessor;
  private final DriveInputSupplier driveInputSource;
  private final NoteDetectorSubsystem nds;

  private PhotonTrackedTarget note;

  public NoteAlignCommand(DriveSubsystem drive, DriveInputSupplier driveInputSource,
      NoteDetectorSubsystem nds) {
    super(drive);
    this.drive = drive;
    this.inputProcessor = new DriveInputProcessor();
    this.driveInputSource = driveInputSource;
    this.nds = nds;
  }

  @Override
  public void initialize() {
    DriveInput stop = new DriveInput(driveInputSource.getDriveInput());

    stop.setRotation(0.0);
    stop.setXSpeed(0.0);
    stop.setXSpeed(0.0);

    drive.updateInputs(stop);

    // note = nds.getOutputs().selectedNote;
  }

  @Override
  public void execute() {
    // double distance = nds.getCenterOfClosestNote(note).x;
  }

}
