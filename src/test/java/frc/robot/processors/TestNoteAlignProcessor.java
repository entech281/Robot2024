package frc.robot.processors;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;
import frc.robot.subsystems.drive.DriveInput;
import frc.robot.subsystems.note_detector.NoteDetectorOutput;
import frc.robot.processors.NoteAlignProcessor;
import java.lang.Math;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import frc.robot.RobotConstants;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import org.opencv.core.Point;

public class TestNoteAlignProcessor {

  @Test
  void noNote() {
    DriveInput di = new DriveInput();
    di.setXSpeed(0.2);
    NoteDetectorOutput no = new NoteDetectorOutput();
    NoteAlignProcessor ni = new NoteAlignProcessor();
    //assertEquals(0.2, ni.keepAligned(di, no).getXSpeed());
  }

  @Test
  void centered() {
    DriveInput di = new DriveInput();
    Pose2d lop = new Pose2d(new Translation2d(0, 0), new Rotation2d(60));
    di.setLatestOdometryPose(lop);
    NoteDetectorOutput no = new NoteDetectorOutput();
    no.setMidpoint(Optional.of(new Point(RobotConstants.Vision.Cameras.COLOR_RESOLUTION[0]/2, RobotConstants.Vision.Cameras.COLOR_RESOLUTION[1]/2)));
    no.setYaw(0);
    NoteAlignProcessor ni = new NoteAlignProcessor();
    //assertEquals(0.5, ni.keepAligned(di, no).getXSpeed(), 0.01);
  }

  @Test
  void offsetLeft() {
    DriveInput di = new DriveInput();
    NoteDetectorOutput no = new NoteDetectorOutput();
    NoteAlignProcessor ni = new NoteAlignProcessor();
    //assertEquals(true, ni.keepAligned(di, no).getRotation()<0);
  }  

  @Test
  void offsetRight() {
    DriveInput di = new DriveInput();
    NoteDetectorOutput no = new NoteDetectorOutput();
    NoteAlignProcessor ni = new NoteAlignProcessor();
    //assertEquals(true, ni.keepAligned(di, no).getRotation()>0);
  }

}
