package frc.robot.processors;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.opencv.core.Point;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.RobotConstants;
import frc.robot.subsystems.drive.DriveInput;
import frc.robot.subsystems.note_detector.NoteDetectorOutput;

public class TestNoteAlignProcessor {

  @Test
  void noNote() {
    DriveInput di = new DriveInput();
    double initialSpeed = 0.2;
    di.setXSpeed(initialSpeed);
    NoteDetectorOutput no = new NoteDetectorOutput();
    no.setMidpoint(Optional.empty());
    NoteAlignProcessor ni = new NoteAlignProcessor();
    assertEquals(initialSpeed, ni.keepAligned(di, no).getXSpeed());
  }

  @Test
  void centered() {
    DriveInput di = new DriveInput();
    double angle = Math.PI / 3;
    di.setXSpeed(Math.cos(angle));
    di.setYSpeed(-Math.sin(angle));
    Pose2d lop = new Pose2d(new Translation2d(0, 0), new Rotation2d(angle));
    di.setLatestOdometryPose(lop);
    NoteDetectorOutput no = new NoteDetectorOutput();
    no.setMidpoint(Optional.of(new Point(RobotConstants.Vision.Resolution.COLOR_RESOLUTION[0] / 2,
        RobotConstants.Vision.Resolution.COLOR_RESOLUTION[1] / 2)));
    NoteAlignProcessor ni = new NoteAlignProcessor();
    assertEquals(Math.cos(angle), ni.keepAligned(di, no).getXSpeed(), 0.01);
  }

  // @Test
  // void offsetLeft() {
  // DriveInput di = new DriveInput();
  // double angle = Math.PI / 2;
  // di.setXSpeed(1);
  // di.setYSpeed(0);
  // Pose2d lop = new Pose2d(new Translation2d(0, 0), new Rotation2d(angle));
  // di.setLatestOdometryPose(lop);
  // NoteDetectorOutput no = new NoteDetectorOutput();
  // double pixelsOffset30Degrees = 56.1467;
  // no.setMidpoint(Optional.of(new Point(pixelsOffset30Degrees,
  // RobotConstants.Vision.Resolution.COLOR_RESOLUTION[1] / 2)));
  // NoteAlignProcessor ni = new NoteAlignProcessor();
  // assertEquals(-Math.sin(angle), ni.keepAligned(di, no).getYSpeed(), 0.01);
  // }

  // @Test
  // void offsetRight() {
  // DriveInput di = new DriveInput();
  // double angle = Math.PI / 3;
  // di.setXSpeed(1);
  // di.setYSpeed(0);
  // Pose2d lop = new Pose2d(new Translation2d(0, 0), new Rotation2d(angle));
  // di.setLatestOdometryPose(lop);
  // NoteDetectorOutput no = new NoteDetectorOutput();
  // no.setMidpoint(Optional.of(new Point(RobotConstants.Vision.Resolution.COLOR_RESOLUTION[0] / 2,
  // RobotConstants.Vision.Resolution.COLOR_RESOLUTION[1] / 2)));
  // NoteAlignProcessor ni = new NoteAlignProcessor();
  // assertEquals(-Math.sin(angle), ni.keepAligned(di, no).getYSpeed(), 0.01);
  // }

}
